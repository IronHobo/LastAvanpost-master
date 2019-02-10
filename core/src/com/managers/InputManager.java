package com.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.gameobjects.Cell;

import static com.managers.GameManager.cells;
import static com.managers.GameManager.temp;

public class InputManager {
    public static Player activePlayer;

    public static void handleInput(OrthographicCamera camera) {

        // Было ли касание экрана?
        if (Gdx.input.justTouched()) {// Получаем координаты касания и устанавливаем значения координат в вектор temp

            GameManager.temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);// получаем координаты касания относительно области просмотра нашей "камеры"


            camera.unproject(GameManager.temp);
            float touchX = GameManager.temp.x;
            float touchY = GameManager.temp.y;
            System.out.println("Было касание экрана, координаты " + " x: " + GameManager.temp.x + " y: " + GameManager.temp.y);
            if (GameLogic.moveIsValide(temp)) {  //проверка что такой ход допустим
                // Выполняем итерацию массива doors и проверяем
                // Было ли выполнено касание по какой-нибудь двери?
                for (int y = 0; y < 10; y++) {
                    for (int x = 0; x < 10; x++) {
                        handleCell(cells[x][y], touchX, touchY);
                    }

                }

            }
        }

    }


    public static boolean handleCell(Cell cell, float touchX, float touchY) {

        // Проверяем, находятся ли координаты касания экрана
        // в границах позиции клетки
        if ((touchX >= cell.position.x) && touchX <= (cell.position.x + cell.width) && (touchY >= cell.position.y) && touchY <= (cell.position.y + cell.height)) {
            // Открываем дверь, если касание было произведено по ней

            Player.totalMoves++; //счетчик всех ходов
            Player.playerClicked(); //счетчик ходов игрока, при необходимости переключает активного игрока
            System.out.println(" Сейчас активен- " + activePlayer.numberOfPlayer + " шагов сделано " + activePlayer.countStepsInMove);
            cell.isClicked(activePlayer.numberOfPlayer);
            return true;
        }
        return false;
    }

}
