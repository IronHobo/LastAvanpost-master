package com.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.managers.GameManager;
import com.managers.Player;

public class Cell {
    public Texture cellTexture;  //текстура клетки
    public Sprite actualSprite;
    public Texture crossBlue = new Texture(Gdx.files.internal("CrossImgBlue.png"));
    public Sprite blueCrossSprite = new Sprite(crossBlue);
    public Texture crossRed= new Texture(Gdx.files.internal("CrossImgRed.png"));
    public Sprite redCrossSprite= new Sprite(crossRed);
    public Texture quadrateBlue = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));
    public Sprite blueQuadtratSprite = new Sprite(quadrateBlue);
    public Texture quadrateRed =new Texture(Gdx.files.internal("QuadrateImgRed.png"));
    public Sprite redQueadratSprite = new Sprite(quadrateRed);
    public Vector2 position = new Vector2();
    enum Condition {Empty, FrendlyCross, EnemyCross, FrendyQuadtrat, EnemyQueadrat};
    Condition condition = Condition.Empty;
    static final float SIZE = GameManager.height/11;
    public float height; //высота
    public float width;
    SpriteBatch batch = new SpriteBatch();

    public Cell(float x, float y) {
        cellTexture = new Texture(Gdx.files.internal("CellEmpty.png"));
        position.set(x, y);
        width = SIZE;
        height = SIZE;
        actualSprite = new Sprite(cellTexture);
        actualSprite.setSize(this.width, this.height);
        actualSprite.setPosition(this.position.x,this.position.y);
    }
    public void render(SpriteBatch batch) {
        actualSprite.draw(batch);
//        switch (condition){
//            case Empty: emptySprite.draw(batch); break;
//            case FrendlyCross: frendlyCrossSprite.draw(batch); break;
//            case EnemyCross: enemyCrossSprite.draw(batch); break;
//            case FrendyQuadtrat: frendyQuadtratSprite.draw(batch); break;
//            case EnemyQueadrat: enemyQueadratSprite.draw(batch); break;
//        }
    }

    public void isClicked(int player){
        System.out.println("я в методе");
        switch (player){
            case 1 : if (condition==Condition.Empty){
                condition=Condition.FrendlyCross;
                cellTexture = new Texture(Gdx.files.internal("CrossImgBlue.png"));
                actualSprite = new Sprite(cellTexture);
                actualSprite.setSize(width, height);
                actualSprite.setPosition(position.x,position.y);
            }

                break;
            case 2 :
                if (condition==Condition.Empty){
                    condition=Condition.FrendlyCross;
                    cellTexture = new Texture(Gdx.files.internal("CrossImgRed.png"));
                    actualSprite = new Sprite(cellTexture);
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x,position.y);
                break;
        }


    }
}


}
