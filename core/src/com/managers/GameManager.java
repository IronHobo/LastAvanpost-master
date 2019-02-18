package com.managers;

import com.badlogic.gdx.math.Vector3;
import com.gameobjects.Cell;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.list.gwozdev.LastAvanpostGame;

import static com.managers.Player.playerCreator;

public class GameManager {
    static Sprite surrenderSprite;
    static Texture surrenderTexture;
    static final float surrender_RESIZE_FACTOR = 1200f;
    static Cell[][] cells;   // массив из 100 клеток
    static final float SIZE = 1f;
    public static float width, height;
    static Vector3 temp = new Vector3(); // временный вектор для хранения входных координат
    static Texture backtexture;
    static Sprite backSprite;

    public static void initialize(float width, float height) {  //Инициализация каждой клетки
        backtexture = new Texture(Gdx.files.internal("background.jpg"));
        backSprite = new Sprite(backtexture);
        backSprite.setSize(width, height);
        backSprite.setPosition(0, 0f);
        GameManager.width = width;
        GameManager.height = height;
        playerCreator(2); //временно, пока нет менюшки 2 игрока
        initCells();
        TextManager.initialize(width, height);


        surrenderTexture = new Texture(Gdx.files.internal("SurrenderBlue.png"));
        surrenderSprite = new Sprite(surrenderTexture);
        surrenderSprite.setSize(surrenderSprite.getWidth() * width / surrender_RESIZE_FACTOR, surrenderSprite.getHeight() * width / surrender_RESIZE_FACTOR);
        surrenderSprite.setPosition((float) (width * 0.8), (float) (height * 0.2));

    }

    public static void renderGame(SpriteBatch batch) { //Отобразить(нарисовать) каждую клетку
        backSprite.draw(batch);
        surrenderSprite.draw(batch);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cells[x][y].render(batch);
            }
        }
        TextManager.displayMessage(batch);
    }


    public static void dispose() {
        backtexture.dispose();
        surrenderTexture.dispose();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                cells[x][y].cellTexture.dispose();
            }
            /// чтобы исключить перегрузку памяти устройства
        }
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

    public static void endOfGame() {

        dispose();
        TextManager.winInfo();
    }

}










