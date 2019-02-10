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

        static Cell[][] cells ;
        // массив из 100 клеток
//        static Texture cellTexture; // текстура для изображения двери
//        private static final float DOOR_RESIZE_FACTOR = 2500f;
//        private static final float DOOR_VERT_POSITION_FACTOR = 3f;
//        private static final float DOOR1_HORIZ_POSITION_FACTOR = 7.77f;
//        private static final float DOOR2_HORIZ_POSITION_FACTOR = 2.57f;
//        private static final float DOOR3_HORIZ_POSITION_FACTOR = 1.52f;
        static final float SIZE = 1f;
        public static float width,height;
        static Vector3 temp = new Vector3(); // временный вектор для хранения входных координат

        public static void initialize(float width,float height){  //Инициализация каждой клетки
            GameManager.width = width;
            GameManager.height = height;
            playerCreator(2);
            initCells();
            addStartAvailableMoves();
        }

        public static void renderGame(SpriteBatch batch){ //Отобразить(нарисовать) каждую клетку

            for (int y = 0; y <10; y++) {
                for (int x = 0; x < 10; x++) {
                    cells[x][y].render(batch);
                }
            }
        }

        public static void dispose() {
         /// чтобы исключить перегрузку памяти устройства

        }

        public static void initCells()
            {
                float coordX = height/3;
                float coordY = height/20;
                cells = new Cell[10][10];
                for (int y = 0; y <10; y++) {     //создать экземляр каждой клетки и добавить их в массив cells
                    for (int x = 0; x < 10; x++) {
                        cells[x][y] = new Cell(coordX, coordY);

                        coordX += height/11;
                    }
                    coordY += height/11;
                    coordX = height/3; //пошла следующая строка клеток на экране
                }

                }


            }








