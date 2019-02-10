package com.managers;

import com.badlogic.gdx.math.Vector3;
import com.gameobjects.Cell;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static com.managers.Player.addStartAvailableMoves;
import static com.managers.Player.playerCreator;

public class GameManager {
    static Cell[][] cells;   // массив из 100 клеток
    static final float SIZE = 1f;
    public static float width, height;
    static Vector3 temp = new Vector3(); // временный вектор для хранения входных координат

    public static void initialize(float width, float height) {  //Инициализация каждой клетки
        GameManager.width = width;
        GameManager.height = height;
        playerCreator(2); //временно, пока нет менюшки 2 игрока
        initCells();
        addStartAvailableMoves();
    }

    public static void renderGame(SpriteBatch batch) { //Отобразить(нарисовать) каждую клетку

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cells[x][y].render(batch);
            }
        }
    }

    public static void dispose() {
        System.out.println("Метод чтобы исключить перегрузку памяти устройства еще не реализован");  /// чтобы исключить перегрузку памяти устройства
    }

    public static void initCells() {
        float coordX = height / 3;
        float coordY = height / 20;
        cells = new Cell[10][10];
        for (int y = 0; y < 10; y++) {     //создать экземляр каждой клетки и добавить их в массив cells
            for (int x = 0; x < 10; x++) {
                cells[x][y] = new Cell(coordX, coordY);
                cells[x][y].numCell = "Клетка x" + x + " + y" + y;
                coordX += height / 11;
            }
            coordY += height / 11;
            coordX = height / 3; //пошла следующая строка клеток на экране
        }
        for (int y = 0; y < 10; y++) {            //каждой клетке даем ссылку на соседей
            for (int x = 0; x < 10; x++) {
                if (x == 0) {
                    if (y == 9) {
                        cells[x][y].nearCells.add(cells[x][y - 1], cells[x + 1][y], cells[x + 1][y - 1]);
                    }
                    if (y != 9 && y != 0) {
                        cells[x][y].nearCells.add(cells[x][y - 1], cells[x + 1][y]);
                        cells[x][y].nearCells.add(cells[x][y + 1], cells[x + 1][y + 1], cells[x + 1][y - 1]);
                    }
                    if (y == 0) {
                        cells[x][y].nearCells.add(cells[x + 1][y], cells[x][y + 1], cells[x + 1][y + 1]);
                    }
                } else if (x == 9) {
                    if (y == 9) {
                        cells[x][y].nearCells.add(cells[x - 1][y], cells[x][y - 1], cells[x - 1][y - 1]);

                    }
                    if (y != 9 && y != 0) {
                        cells[x][y].nearCells.add(cells[x - 1][y], cells[x][y - 1], cells[x - 1][y - 1]);
                        cells[x][y].nearCells.add(cells[x][y + 1], cells[x - 1][y + 1]);
                    }
                    if (y == 0) {
                        cells[x][y].nearCells.add(cells[x][y + 1], cells[x - 1][y], cells[x - 1][y + 1]);

                    }
                } else {
                    if (y != 9 && y != 0) {
                        cells[x][y].nearCells.add(cells[x - 1][y], cells[x][y - 1], cells[x - 1][y - 1], cells[x + 1][y]);
                        cells[x][y].nearCells.add(cells[x][y + 1], cells[x + 1][y + 1], cells[x - 1][y + 1], cells[x + 1][y - 1]);
                    }
                    if (y == 9) {
                        cells[x][y].nearCells.add(cells[x - 1][y], cells[x][y - 1], cells[x - 1][y - 1], cells[x + 1][y]);
                        cells[x][y].nearCells.add(cells[x + 1][y - 1]);
                    }
                    if (y == 0) {
                        cells[x][y].nearCells.add(cells[x - 1][y], cells[x + 1][y]);
                        cells[x][y].nearCells.add(cells[x][y + 1], cells[x + 1][y + 1], cells[x - 1][y + 1]);
                    }
                }
            }
        }

    }
}








