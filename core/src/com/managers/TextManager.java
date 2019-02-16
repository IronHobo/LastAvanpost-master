package com.managers;
import com.badlogic.gdx.Gdx;
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
        static StringBuffer playerInfo;
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
            font = new BitmapFont(Gdx.files.internal("appettitenew2.fnt"));
            //font.setColor(Color.MAROON);
            //масштабируем размер шрифта в соответствии с шириной экрана
            font.getData().setScale(width/800f);

            playerInfo = new StringBuffer( (String) "Move Player ");
            moveInfo = new StringBuffer( (String) "Move № ");
        }

        public static void displayMessage(SpriteBatch batch){

            GlyphLayout glyphLayout = new GlyphLayout(); //используем класс GlyphLayout

                    glyphLayout.setText(font, playerInfo);
                    font.draw(batch, glyphLayout, (float)(width*0.8 - glyphLayout.width/2), (float) (height*0.8 -glyphLayout.height/2));

            glyphLayout.setText(font, moveInfo);
            font.draw(batch, glyphLayout, (float)(width*0.8 - glyphLayout.width/2), (float) (height*0.7 -glyphLayout.height/2));

            }


        public static void whoIsMove(){
            //вставляем номер выбранной пользователем двери в текст строки confirm
            playerInfo.insert(playerInfo.indexOf("Move Player")+ "Move Player".length(), " "+(activePlayer.numberOfPlayer));
            moveInfo.insert(moveInfo.indexOf("Move № ")+ "Move № ".length(), " "+activePlayer.countStepsInMove);
        }

        public static void clear() {
            playerInfo.delete(11,14);
                    moveInfo.delete(7,9);
        }

}

