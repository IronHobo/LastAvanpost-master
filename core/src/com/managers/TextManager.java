package com.managers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.managers.InputManager.activePlayer;

public class TextManager {


        static BitmapFont font; //мы будем отображать текст на экране
        //используя эту переменную

        //Текст, относящийся к разным состояниям
        static String start = "Select a door";
        static StringBuffer moveInfo;
        static String win = "You Win!";
        static String lose = "You Lose!";
        //высоты и ширина области просмотра
        static float width,height;

        public static void initialize(float width,float height){

            TextManager.width = width;
            TextManager.height= height;
            //установим цвет шрифта - cyan
            font = new BitmapFont();
            font.setColor(Color.CYAN);
            //масштабируем размер шрифта в соответствии с шириной экрана
            font.getData().setScale(width/800f);

            moveInfo = new StringBuffer( (String) "Player no moving. Move count");
        }

        public static void displayMessage(SpriteBatch batch){

            GlyphLayout glyphLayout = new GlyphLayout(); //используем класс GlyphLayout

                    glyphLayout.setText(font, moveInfo);
                    font.draw(batch, glyphLayout, (float)(width*0.8 - glyphLayout.width/2), (float) (height*0.8 -glyphLayout.height/2));

            }


        public static void whoIsMove(){
            //вставляем номер выбранной пользователем двери в текст строки confirm
            moveInfo.insert(moveInfo.indexOf("Player no")+ "Player no".length(), " "+(activePlayer.numberOfPlayer)+" "+(activePlayer.countStepsInMove));
        }
    }


