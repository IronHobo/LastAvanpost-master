package com.managers;

import com.badlogic.gdx.utils.Array;
import com.gameobjects.Cell;

import java.util.Iterator;

import static com.managers.GameManager.cells;
import static com.managers.InputManager.activePlayer;

public class Player {
    static int playerCount; //количество ироков
    static int totalMoves = 0;  //общее количество ходов всех игроков
    public int totalPlayerMoves = 0;
    public int countStepsInMove;     // количество шагов, сделанных игроком в текущем ходе
    String nameOfPlayer; // имя игрока
    public int numberOfPlayer;  //порядковый номер игрока
    boolean isActive = false;
    static int count = 0;   // кол-во сгенерированных игроков ,используется при генерации игроков
    static Player playerArr[];   //массив в котором хранятся объекты игрок
    private Array<Cell> availableMoves; //допустимые дя хода клетки у каждого игрока

    Player() {
        numberOfPlayer = ++count;
        nameOfPlayer = "Игрок " + numberOfPlayer;
        countStepsInMove = 0;
        availableMoves = new Array<Cell>();
    }

    public static void playerCreator(int playerCount) {
        Player.playerCount = playerCount;
        playerArr = new Player[playerCount];
        for (int x = 0; x < playerCount; x++) {
            playerArr[x] = new Player();
        }
        playerArr[0].isActive = true;
        activePlayer = playerArr[0];
    }


    public static void switchPlayer() {
        System.out.println("в методе switchPlayer");

        for (int x = 0; x < playerArr.length; x++) {
            int y = playerArr.length;
            if (playerArr[x].isActive && x == (y - 1)) {
                playerArr[x].isActive = false;
                playerArr[0].isActive = true;
                InputManager.activePlayer = playerArr[0];
                System.out.println("сменился игрок активный, активен теперь " + activePlayer.numberOfPlayer);
                activePlayer.countStepsInMove = 0;
                break;
            }
            else if (playerArr[x].isActive && x < (y - 1)) {
                int z = x + 1;
                playerArr[x].isActive = false;
                playerArr[z].isActive = true;
                activePlayer = playerArr[z];
                System.out.println("сменился игрок активный, активен теперь " + activePlayer.numberOfPlayer);
                activePlayer.countStepsInMove = 0;
                break;
            }
        }
    }

    public Array<Cell> getAvailableMoves() {

        return availableMoves;

    }
    public void removeElementOfAvailableMoves(Cell cell) {
        availableMoves.removeValue(cell,true);
        System.out.println(cell.numCell+" более не доступна для нажатия игроком "+activePlayer.numberOfPlayer);
    }

    public void addAvailableMoves(Cell cell) {
        if (availableMoves.size == 0) {
            availableMoves.add(cell);
            System.out.println(cell.numCell + " добавлена в доступные ходы игроку " + numberOfPlayer);
        }
        else {
            Iterator<Cell> iterator = availableMoves.iterator();
            while (iterator.hasNext()) {
                Cell value = iterator.next();
                if (cell.numCell.equals(value.numCell)) {
                    System.out.println(cell.numCell + " уже есть в доступных ходах игрока" + numberOfPlayer);
                    break;
                }
                else
                    availableMoves.add(cell);
                System.out.println(cell.numCell + " добавлена в доступные ходы игроку " + numberOfPlayer);
                break;
            }
        }
    }

    public static void addStartAvailableMoves() {
        for (Player player : playerArr) {
            switch (player.numberOfPlayer) {
                case 1: {
                    player.addAvailableMoves(cells[9][0]);
                    break;
                }
                case 2: {
                    player.addAvailableMoves(cells[0][9]);
                    break;
                }
                case 3: {
                    player.addAvailableMoves(cells[9][9]);
                    break;
                }
                case 4: {
                    player.addAvailableMoves(cells[0][0]);
                    break;
                }

            }
        }
    }
}