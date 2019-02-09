package com.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gameobjects.Cell;

import static com.managers.InputManager.activePlayer;
import static com.managers.GameManager.cells;

public class GameLogic {
    public static boolean moveIsValide(Vector3 touch, Player pl) {
        if (pl.numberOfPlayer == 1) {
            if (pl.totalPlayerMoves == 0) {
                if ((touch.x >= cells[9][0].position.x) && touch.x <= (cells[9][0].position.x + cells[9][0].width) && (touch.y >= cells[9][0].position.y) && touch.y <= (cells[9][0].position.y + cells[9][0].height)) {
                    System.out.println("можно сюда нажимать");
                    return true;
                } else {
                    System.out.println("нельзя сюда нажимать");
                    return false;
                }
            }
            else return true;
        }

        else if((pl.numberOfPlayer != 1))/*&&(pl.totalPlayerMoves == 0))  */ {
            switch (pl.numberOfPlayer) {
                case 2: {
                    if ((touch.x >= cells[0][9].position.x) && touch.x <= (cells[0][9].position.x + cells[0][9].width) && (touch.y >= cells[0][9].position.y) && touch.y <= (cells[0][9].position.y + cells[0][9].height)) {
                        System.out.println("можно сюда нажимать");
                        return true;
                    } else {
                        System.out.println("нельзя сюда нажимать");
                        return false;
                    }
                }
            }
        }
        return true;

    }
}


