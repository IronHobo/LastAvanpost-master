package com.managers;

import com.managers.Player;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.gameobjects.Cell;

import static com.managers.InputManager.activePlayer;
import static com.managers.GameManager.cells;

public class GameLogic {
    public static boolean moveIsValide(Vector3 touch) {

        for (Cell cell : activePlayer.availableMoves) {
            if ((touch.x >= cell.position.x) && touch.x <= (cell.position.x + cell.width) && (touch.y >= cell.position.y) && touch.y <= (cell.position.y + cell.height)) {
                System.out.println("можно сюда нажимать, точка касания совпадает с одним из доступны ходов");
                return true;

            } else
                System.out.println("нельзя сюда нажимать");
            return false;
        }
        return false;
    }
}





