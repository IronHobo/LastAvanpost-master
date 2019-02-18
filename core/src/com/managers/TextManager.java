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
        static BitmapFont winFont;
        //Текст, относящийся к разным состояниям
        static String start = "Select a door";
        static StringBuffer playerInfo;
        static StringBuffer moveInfo;
        static StringBuffer surrenderConfirmInfo;
        static StringBuffer surrenderConfirmInfo2;
        static StringBuffer winInfo;
        static String win = "You Win!";
        static String lose = "You Lose!";
        //высоты и ширина области просмотра
        static float width,height;

        public static void initialize(float width,float height){

            TextManager.width = width;
            TextManager.height= height;
            //установим цвет шрифта - cyan
            font = new BitmapFont();
            font = new BitmapFont(Gdx.files.internal("appetitenew2.fnt"));
            winFont = new BitmapFont();
            winFont = new BitmapFont(Gdx.files.internal("appetitenew2.fnt"));
            //масштабируем размер шрифта в соответствии с шириной экрана
            font.getData().setScale(width/1300f);
            winFont.getData().setScale(width/600f);
            playerInfo = new StringBuffer( (String) "Player ");
            moveInfo = new StringBuffer( (String) "Move № ");
            surrenderConfirmInfo = new StringBuffer( (String) " ");
            surrenderConfirmInfo2 = new StringBuffer( (String) " ");
            winInfo = new StringBuffer( (String) " ");
        }

        public static void displayMessage(SpriteBatch batch){

            GlyphLayout glyphLayout = new GlyphLayout(); //используем класс GlyphLayout

                    glyphLayout.setText(font, playerInfo);
                    font.draw(batch, glyphLayout, (float)(width*0.8 - glyphLayout.width/2), (float) (height*0.8 -glyphLayout.height/2));

            glyphLayout.setText(font, moveInfo);
            font.draw(batch, glyphLayout, (float)(width*0.8 - glyphLayout.width/2), (float) (height*0.7 -glyphLayout.height/2));

            glyphLayout.setText(font, surrenderConfirmInfo);
            font.draw(batch, glyphLayout, (float)(width*0.82 - glyphLayout.width/2), (float) (height*0.6 -glyphLayout.height/2));

            glyphLayout.setText(font, surrenderConfirmInfo2);
            font.draw(batch, glyphLayout, (float)(width*0.82 - glyphLayout.width/2), (float) (height*0.5 -glyphLayout.height/2));

            glyphLayout.setText(winFont, winInfo);
            winFont.draw(batch, glyphLayout, (float)(width*0.5 - glyphLayout.width/2), (float) (height*0.5 -glyphLayout.height/2));
            }

        public static void whoIsMove(){
            //вставляем номер выбранной пользователем двери в текст строки confirm
            playerInfo.insert(playerInfo.indexOf("Player")+ "Player".length(), " "+(activePlayer.numberOfPlayer));
            moveInfo.insert(moveInfo.indexOf("Move № ")+ "Move № ".length(), " "+activePlayer.countStepsInMove);
        }

        public static void clear() {
            playerInfo.delete(6,8);
                    moveInfo.delete(7,9);
        }
            public static void surrenderConfirm() {
                surrenderConfirmInfo.insert(0,"Player " +(activePlayer.numberOfPlayer)+" are u sure?");
                surrenderConfirmInfo2.insert(0,"Click again, to lose");
            }
            public static void surrenderClear(){
              surrenderConfirmInfo.delete(0,21);
              surrenderConfirmInfo2.delete(0,21);
           }

          public static void winInfo() {
              playerInfo.delete(0,8);
              moveInfo.delete(0,9);
              surrenderConfirmInfo.delete(0,21);
              surrenderConfirmInfo2.delete(0,21);
              winInfo.insert(0,"PLAYER " +(activePlayer.numberOfPlayer)+" WIN!");

    }

}


