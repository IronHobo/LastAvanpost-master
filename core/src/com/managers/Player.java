package com.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

}