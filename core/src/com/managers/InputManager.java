package com.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.gameobjects.Cell;
import com.gameobjects.GroupCells;

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
            doingMove(temp);// проверяет попадание по любой клетке и при необходимости переключает активного игрока
            }
    }

    private static void doingMove(Vector3 touch) {
        System.out.println("проверяю попадание по любой клетке и при необходимости переключаю активного игрока");
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                //есть ли попадание в клетку
                if ((touch.x >= cells[x][y].position.x) && touch.x <= (cells[x][y].position.x + cells[x][y].width) && (touch.y >= cells[x][y].position.y) && touch.y <= (cells[x][y].position.y + cells[x][y].height)) {
                    if(activePlayer.totalPlayerMoves==0)validateFirstMove(cells[x][y],activePlayer.numberOfPlayer);
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
                if(cell.position ==cells[9][0].position)handleCell(cell);
                break;
            }
            case 2: {
                if(cell.position ==cells[0][9].position)handleCell(cell);
                break;
            }
            case 3: {
                if(cell.position ==cells[9][9].position)handleCell(cell);
                break;
            }
            case 4: {
                if(cell.position ==cells[0][0].position)handleCell(cell);
                break;
            }



        }

    }


    private static void validateMove(Cell cel){
          for (Cell near:
                  cel.nearCells) {
            if(((near.condition == Cell.Condition.Quadtrat&&near.numberMasterOfTheCell==activePlayer.numberOfPlayer)&&(cel.condition == Cell.Condition.Empty||(cel.condition == Cell.Condition.Cross&&!(cel.numberMasterOfTheCell==(activePlayer.numberOfPlayer))))))

            {
                handleCell(cel);
                break;
            }
            else if
              (
                      (near.condition == Cell.Condition.Cross&&near.numberMasterOfTheCell==activePlayer.numberOfPlayer)&&
                      (cel.condition == Cell.Condition.Empty||
                              (cel.condition == Cell.Condition.Cross&&(!(cel.numberMasterOfTheCell==activePlayer.numberOfPlayer))))
              )
            {
                handleCell(cel);
                break;
            }

            else System.out.println("касание не совпадает ни с одним из доступных игроку ходов ");

        }
        }



    public static void handleCell(Cell cell) {  //счетчики
        System.out.println("Я в методе InputManager.handleCell тут отрабатывают счетчики");
        Player.totalMoves++; //счетчик всех ходов
        activePlayer.countStepsInMove++;
        activePlayer.totalPlayerMoves++;//счетчик всех ходов конкретного игрока
        TextManager.clear();
        TextManager.whoIsMove();
        System.out.println("Ход валиден,сейчас будет клик. Сейчас активен- " + activePlayer.numberOfPlayer + " шагов сделано " + activePlayer.countStepsInMove);
        cell.isClicked(activePlayer);
//        turnOnAllQuadratsWithElectro();
//        turnOffAllQuadratsWithNoElectro();
        checkElectricityOfAllGroups();
        uncheckAllQuadrats();
        if (activePlayer.countStepsInMove >= 3)
            switchPlayer();
    }




//    private static void turnOffAllQuadratsWithNoElectro() {
//        System.out.println("Прозваниваю все клетки с квадратами, в случае, если он отключается от питания меняю его вид и функции");
//        for (int y = 0; y < 10; y++) {
//            for (int x = 0; x < 10; x++) {
//
//                if (cells[x][y].condition == Cell.Condition.Quadtrat) {
//                    System.out.println("Квадрат нашел");
//                    if (!(cells[x][y].haveElectricity())) {
//                        System.out.println("Выключаю энергию квадрату" + cells[x][y].numCell);
//                        electricity(cells[x][y], 0);//Выключаем у квадрата энергию, квадрат меняет статус
//                    } else System.out.println("Квадратов какие надо отключить пока не нашел");
//                }
//            }
//        }
//    }
//
//    private static void turnOnAllQuadratsWithElectro() {
//        for (int y = 0; y < 10; y++) {
//            for (int x = 0; x < 10; x++) {
//                if (cells[x][y].condition == Cell.Condition.DeadQuadtrat) {
//                    System.out.println("Тухлый kвадрат нашел");
//                    if (cells[x][y].haveElectricity()) {
//                        System.out.println("Включаю энергию квадрату" + cells[x][y].numCell);
//                        electricity(cells[x][y], 1);//Включаем у квадрата энергию, квадрат меняет статус
//                    } else System.out.println("Квадратов какие надо включить пока не нашел");
//                }
//            }
//
//        }
//    }
    public static void uncheckAllQuadrats() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (cells[x][y].condition == Cell.Condition.DeadQuadtrat || cells[x][y].condition == Cell.Condition.Quadtrat) {
                    cells[x][y].uncheck();
                }
            }
        }
    }
    public static void checkElectricityOfAllGroups(){
        if(!(GroupCells.allGroups == null)) {
            for (GroupCells group :
                    GroupCells.allGroups) {
                group.checkElectricity();

            }
        }
        else
            System.out.println("Пока нет групп квадратов");

    }

}



