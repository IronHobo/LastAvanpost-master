package com.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gameobjects.Cell;

import java.util.Iterator;
import java.util.concurrent.locks.Condition;

import static com.gameobjects.Cell.electricity;
import static com.managers.GameManager.cells;
import static com.managers.GameManager.temp;
import static com.managers.Player.switchPlayer;

public class InputManager {
    public static Player activePlayer;

    public static void handleInput(OrthographicCamera camera) {
        // Было ли касание экрана?
        if (Gdx.input.justTouched()) {  // Получаем координаты касания и устанавливаем значения координат в вектор temp
            GameManager.temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);// получаем координаты касания относительно области просмотра нашей "камеры"
            camera.unproject(GameManager.temp);
            float touchX = GameManager.temp.x;
            float touchY = GameManager.temp.y;
            System.out.println("Было касание экрана");
            System.out.println(" Произошло касание, координаты " + " x: " + GameManager.temp.x + " y: " + GameManager.temp.y + ". Проверки попадания еще нет. Сейчас активен- " + activePlayer.numberOfPlayer + " шагов сделано " + activePlayer.countStepsInMove);
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

                    } else {
                        System.out.println("Есть попадание по одной из клеток, переключаю активного игрока");
                        switchPlayer();
                    }
                }
            }
        }
    }

    public static void moveIsValide(Vector3 touch) {
        System.out.println("Я в методе InputManager.moveIsValide - проверяю доступность хода на эту клетки");

        for (Cell cell : activePlayer.getAvailableMoves()) {
            if ((touch.x >= cell.position.x) && touch.x <= (cell.position.x + cell.width) && (touch.y >= cell.position.y) && touch.y <= (cell.position.y + cell.height)) {
                System.out.println("касание совпадает с одним из доступны ходов " + "- " + cell.numCell);
                handleCell(cell);
                break;
            }
            System.out.println("касание не совпадает ни с одним из доступны игроку ходов ");
        }
    }

    public static void handleCell(Cell cell) {  //счетчики
        System.out.println("Я в методе InputManager.handleCell тут отрабатывают счетчики");
        Player.totalMoves++; //счетчик всех ходов
        activePlayer.countStepsInMove++;
        activePlayer.totalPlayerMoves++;//счетчик всех ходов конкретного игрока
        System.out.println("Ход валиден,сейчас будет клик. Сейчас активен- " + activePlayer.numberOfPlayer + " шагов сделано " + activePlayer.countStepsInMove);
        cell.isClicked(activePlayer);
        callAllQuadrats();
    }

    private static void callAllQuadrats() {
        System.out.println("Прозваниваю все клетки с квадратами, в случае, если он отключается от питания  или включается меняю его вид и функции");
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (cells[x][y].condition == Cell.Condition.Quadtrat) {
                    System.out.println("Квадрат нашел");
                    int masterOfQuadtrat = cells[x][y].numberMasterOfTheCell;
                    int countOfGenerator = 0;
                    Iterator<Cell> iterator = cells[x][y].nearCells.iterator();
                    while (iterator.hasNext()) {
                        Cell nearCell = iterator.next();
                        if (nearCell.numberMasterOfTheCell == masterOfQuadtrat && (nearCell.condition == Cell.Condition.Cross || nearCell.condition == Cell.Condition.Quadtrat)) {
                            countOfGenerator++;
                            break;
                        }
                    }
                    if (countOfGenerator == 0) {
                        electricity(cells[x][y], cells[x][y].numberMasterOfTheCell, 0);//Выключаем у квадрата энергию, квадрат меняет статус
                    }


                } else if (cells[x][y].condition == Cell.Condition.DeadQuadtrat) {
                    System.out.println("Тухлый kвадрат нашел");
                    int masterOfQuadtrat = cells[x][y].numberMasterOfTheCell;
                    int countOfGenerator = 0;
                    Iterator<Cell> iterator = cells[x][y].nearCells.iterator();
                    while (iterator.hasNext()) {
                        Cell nearCell = iterator.next();
                        if (nearCell.numberMasterOfTheCell == masterOfQuadtrat && (nearCell.condition == Cell.Condition.Cross || nearCell.condition == Cell.Condition.Quadtrat)) {
                            countOfGenerator++;
                            break;
                        }
                    }
                    if (countOfGenerator == 1) {
                        electricity(cells[x][y], cells[x][y].numberMasterOfTheCell, 1);//Включаем у квадрата энергию, квадрат меняет статус
                    } else System.out.println("Квадратов пока не нашел");

                }
            }
        }
    }
}


