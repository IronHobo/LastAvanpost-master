package com.managers;

import com.MainGame;
import com.MenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.gameobjects.Cell;

import java.io.IOException;

import ru.list.gwozdev.LastAvanpostGame;

import static com.managers.GameManager.cells;
import static com.managers.GameManager.temp;
import static com.managers.GameManager.tokOffSound;
import static com.managers.GameManager.wrongStep;

public class InputManager extends InputAdapter {
    public static Player activePlayer;
    private static boolean surrender = false;
    @Override
    public boolean keyUp(int keycode) {
        if(keycode== Input.Keys.BACK){
            LastAvanpostGame.game.setScreen(new MenuScreen(LastAvanpostGame.game)); // Переход к экрану меню
        }
        return false;
    }

    public static void handleInput(OrthographicCamera camera) throws IOException {
        // Было ли касание экрана?
            if (Gdx.input.justTouched()) {  // Получаем координаты касания и устанавливаем значения координат в вектор temp
            GameManager.temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(GameManager.temp);// получаем координаты касания относительно области просмотра нашей "камеры"

                if (LastAvanpostGame.onlineNumberOfPlayer==0){     //Игра на одном устройстве
                    handleSurrender(temp);
                    doingMove(temp);// проверяет попадание по любой клетке и при необходимости переключает активного игрока
                }
                else if(LastAvanpostGame.onlineNumberOfPlayer==1&&activePlayer.numberOfPlayer==1){

                    Server.serverSpeak(temp.toString());
                    handleSurrender(temp);
                    doingMove(temp);
                }
//                else if (LastAvanpostGame.onlineNumberOfPlayer==2&&activePlayer.numberOfPlayer==1) {
//                    String coordsMove = Client.clientListen();
//                    Vector3 someoneMove = new Vector3();
//                    someoneMove.fromString(coordsMove); //преобразую строку с координатами касания от клиента в vector3
//                    handleSurrender(someoneMove);
//                    doingMove(someoneMove);
//
//                }
                else if (LastAvanpostGame.onlineNumberOfPlayer==2&&activePlayer.numberOfPlayer==2){
                    Client.clientSpeak(temp.toString());
                    handleSurrender(temp);
                    doingMove(temp);
                }

//
        }
    }
    public static void doingMove(Vector3 touch) {
        System.out.println("проверяю попадание по любой клетке и при необходимости переключаю активного игрока");
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                //есть ли попадание в клетку
                if ((touch.x >= cells[x][y].position.x) && touch.x <= (cells[x][y].position.x + cells[x][y].width) && (touch.y >= cells[x][y].position.y) && touch.y <= (cells[x][y].position.y + cells[x][y].height)) {
                    if (activePlayer.totalPlayerMoves == 0)
                        validateFirstMove(cells[x][y], activePlayer.numberOfPlayer);
                    else
                        validateMove(cells[x][y]);
                    break;
                }
            }
        }
    }
    private static void validateFirstMove(Cell cell, int numberOfPlayer) {
        switch (numberOfPlayer) {
            case 1: {
                if (cell.position == cells[9][0].position){
                    handleCell(cell);
                    break;
                }
                else{
                    wrongStep.play();
                    break;
                }
            }
            case 2: {
                if (cell.position == cells[0][9].position) {
                    handleCell(cell);
                break;
                }
                else{
                wrongStep.play();
                break;
            }
            }
            case 3: {
                if (cell.position == cells[9][9].position) {
                    handleCell(cell);
                break;
                }
                else{
                wrongStep.play();
                break;
            }
            }
            case 4: {
                if (cell.position == cells[0][0].position){
                    handleCell(cell);
                break;
                }
                else{
                wrongStep.play();
                break;
            }
            }
        }
    }
    private static void validateMove(Cell cel) {
        for (Cell near :
                cel.nearCells) {
            if (((near.condition == Cell.Condition.Quadtrat && near.numberMasterOfTheCell == activePlayer.numberOfPlayer) && (cel.condition == Cell.Condition.Empty || (cel.condition == Cell.Condition.Cross && !(cel.numberMasterOfTheCell == (activePlayer.numberOfPlayer)))))) {
                handleCell(cel);
                break;
            } else if
            (
                    (near.condition == Cell.Condition.Cross && near.numberMasterOfTheCell == activePlayer.numberOfPlayer) &&
                            (cel.condition == Cell.Condition.Empty ||
                                    (cel.condition == Cell.Condition.Cross && (!(cel.numberMasterOfTheCell == activePlayer.numberOfPlayer))))
            ) {
                handleCell(cel);
                break;
            } else System.out.println("касание не совпадает ни с одним из доступных игроку ходов ");


        }
//        wrongStep.play();


        }
    public static void handleCell(Cell cell) {  //счетчики
        System.out.println("Я в методе InputManager.handleCell тут отрабатывают счетчики");
        Player.totalMoves++; //счетчик всех ходов
        activePlayer.countStepsInMove++;
        activePlayer.totalPlayerMoves++;//счетчик всех ходов конкретного игрока
        System.out.println("Ход валиден,сейчас будет клик. Сейчас активен- " + activePlayer.numberOfPlayer + " шагов сделано " + activePlayer.countStepsInMove);
        cell.isClicked(activePlayer);
    }
    public static void handleSurrender(Vector3 touch) {
        // определяем, было ли касание кнопки surrender, используя границы спрайта
        if ((touch.x >= GameManager.surrenderSprite.getX()) && touch.x <= (GameManager.surrenderSprite.getX() + GameManager.surrenderSprite.getWidth()) && (touch.y >= GameManager.surrenderSprite.getY()) && touch.y <= (GameManager.surrenderSprite.getY() + GameManager.surrenderSprite.getHeight())) {
            if (!surrender) {
                TextManager.surrenderConfirm();
                surrender = true;
            } else GameManager.endOfGame();
        } else {
            surrender = false;
            TextManager.surrenderClear();
        }
    }
}



