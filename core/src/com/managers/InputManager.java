package com.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gameobjects.Cell;

import static com.managers.GameManager.cells;
import static com.managers.GameManager.temp;
import static com.managers.Player.switchPlayer;

public class InputManager {
    public static Player activePlayer;

    public static void handleInput(OrthographicCamera camera) {
        // Было ли касание экрана?
        if (Gdx.input.justTouched()) {// Получаем координаты касания и устанавливаем значения координат в вектор temp
            GameManager.temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);// получаем координаты касания относительно области просмотра нашей "камеры"
            camera.unproject(GameManager.temp);
            float touchX = GameManager.temp.x;
            float touchY = GameManager.temp.y;
            System.out.println("Было касание экрана");
            System.out.println(" Произошло касание, координаты " + " x: " + GameManager.temp.x + " y: " + GameManager.temp.y +". Проверки попадания еще нет. Сейчас активен- " + activePlayer.numberOfPlayer + " шагов сделано " + activePlayer.countStepsInMove);
            isItTouch(temp);// проверяет попадание по любой клетке и при необходимости переключает активного игрока
            moveIsValide(temp);
    }
    }

    private static void isItTouch(Vector3 touch) {
        System.out.println("проверяю попадание по любой клетке и при необходимости переключаю активного игрока");
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if ((touch.x >= cells[x][y].position.x) && touch.x <= (cells[x][y].position.x + cells[x][y].width) && (touch.y >= cells[x][y].position.y) && touch.y <= (cells[x][y].position.y + cells[x][y].height)) {
                    if (activePlayer.countStepsInMove < 3) {
                        System.out.println("Есть попадание по одной из клеток, переключение активного игрока не требуется");

                    }
                    else {
                        System.out.println("Есть попадание по одной из клеток, переключаю активного игрока");
                        switchPlayer();
                    }
                }
            }
        }
    }
    public static void moveIsValide(Vector3 touch) {
        System.out.println("Я в методе InputManager.moveIsValide - проверяю доступность для хода этой клетки");

        for (Cell cell : activePlayer.getAvailableMoves()) {
            if ((touch.x >= cell.position.x) && touch.x <= (cell.position.x + cell.width) && (touch.y >= cell.position.y) && touch.y <= (cell.position.y + cell.height)) {
                System.out.println("касание совпадает с одним из доступны ходов "+"- "+cell.numCell);
                handleCell(cell, touch.x, touch.y);
                break;
            }

        }

    }

    public static void handleCell(Cell cell, float touchX, float touchY) {  //счетчики
        System.out.println("Я в методе InputManager.handleCell");
            Player.totalMoves++; //счетчик всех ходов
            activePlayer.countStepsInMove++;
            activePlayer.totalPlayerMoves++;//счетчик всех ходов конкретного игрока
            System.out.println("Ход валиден,сейчас будет клик. Сейчас активен- " + activePlayer.numberOfPlayer + " шагов сделано " + activePlayer.countStepsInMove);
            cell.isClicked(activePlayer);
        }



}
