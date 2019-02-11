package com.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.managers.GameManager;
import com.managers.Player;

import java.util.Iterator;


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
    public enum Condition {Empty, Cross, Quadtrat,DeadQuadtrat}
    public Condition condition;
    static final float SIZE = GameManager.height/11;
    public float height; //высота
    public float width;
    SpriteBatch batch = new SpriteBatch();
    public int numberMasterOfTheCell;
    public String numCell;
    public Array<Cell> nearCells;   //список соседних клеток
    public Array<Cell> brothers;    //список соседних квадратов
    public Array<String> temp;   //массив в котором храняться уже проверенные клетки



    public Cell(float x, float y) {
        condition = Condition.Empty;
        cellTexture = new Texture(Gdx.files.internal("CellEmpty.png"));
        position.set(x, y);
        width = SIZE;
        height = SIZE;
        actualSprite = new Sprite(cellTexture);
        actualSprite.setSize(this.width, this.height);
        actualSprite.setPosition(this.position.x,this.position.y);
        nearCells = new Array<Cell>();

    }
    public void render(SpriteBatch batch) {
        actualSprite.draw(batch);
    }

    public void isClicked(Player player){
        System.out.println("я в методе Cell.isClicked");

        for (Cell cell:nearCells ) {    //проверяем соседние клетки и добавляем доступные для хода игрока клетки в массив
            if ((cell.condition==Condition.Empty)||(cell.condition==Condition.Cross&&cell.numberMasterOfTheCell!=player.numberOfPlayer)){
                player.addAvailableMoves(cell);
            }
            else
                System.out.println("на следующую клетку не походить " + cell.numCell);

        }
        switch (player.numberOfPlayer){
            case 1 :
                if (condition==Condition.Empty){
                condition=Condition.Cross;
                cellTexture = new Texture(Gdx.files.internal("CrossImgBlue.png"));
                actualSprite = new Sprite(cellTexture);
                actualSprite.setSize(width, height);
                actualSprite.setPosition(position.x,position.y);
                break;
            }
                else if ((condition==Condition.Cross)&&numberMasterOfTheCell!=player.numberOfPlayer){
                    condition=Condition.Quadtrat;
                    brothers =new Array<Cell>();
                    findBuildings();
                    cellTexture = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));
                    actualSprite = new Sprite(cellTexture);
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x,position.y);
                    break;

            }

            case 2 :
                if (condition==Condition.Empty){
                    condition=Condition.Cross;
                    cellTexture = new Texture(Gdx.files.internal("CrossImgRed.png"));
                    actualSprite = new Sprite(cellTexture);
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x,position.y);
                    break;

        }
                else if ((condition==Condition.Cross)&&numberMasterOfTheCell!=player.numberOfPlayer){
                    condition=Condition.Quadtrat;
                    brothers =new Array<Cell>();
                    findBuildings();
                    cellTexture = new Texture(Gdx.files.internal("QuadrateImgRed.png"));
                    actualSprite = new Sprite(cellTexture);
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x,position.y);
                    break;
                }

    }
        numberMasterOfTheCell=player.numberOfPlayer; //присваивается новый хозяин текущей клетке
        player.removeElementOfAvailableMoves(this); // текущая клетка пропадает из доступных для хода

}

    private void findBuildings() {
        //проверка всех соседей квадрата
        Iterator<Cell> iterator = nearCells.iterator();
        while (iterator.hasNext()) {
            Cell nearCell = iterator.next();
            if (nearCell.numberMasterOfTheCell == numberMasterOfTheCell && nearCell.condition == Cell.Condition.Quadtrat) {
                brothers.add(nearCell);
                System.out.println("в список соседних квадратов добавлен квадрат "+nearCell.numCell);
                break;
            }
            else
                System.out.println("по соседству пока квадратов нет");
        }
    }

    public static void electricity(Cell cell,int numberMasterOfTheCell, int switcher){

        if(switcher==0){
            switch (numberMasterOfTheCell){
                case 1:
            System.out.println("в методе, квадрат меняет статус");
            cell.condition=Condition.DeadQuadtrat;
            cell.cellTexture = new Texture(Gdx.files.internal("QuadrateBlueDead.png"));
                    cell.actualSprite = new Sprite(cell.cellTexture);
                    cell.actualSprite.setSize(cell.width, cell.height);
                    cell.actualSprite.setPosition(cell.position.x,cell.position.y);
                    break;
                case 2:
                    System.out.println("в методе, квадрат меняет статус");
                    cell.condition=Condition.DeadQuadtrat;
                    cell.cellTexture = new Texture(Gdx.files.internal("QuadrateRedDead.png"));
                    cell.actualSprite = new Sprite(cell.cellTexture);
                    cell.actualSprite.setSize(cell.width, cell.height);
                    cell.actualSprite.setPosition(cell.position.x,cell.position.y);
                    break;

        }
        if (switcher==1){
            switch (numberMasterOfTheCell){
                case 1:
                    System.out.println("в методе, квадрат меняет статус");
                    cell.condition=Condition.Quadtrat;
                    cell.cellTexture = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));
                    cell.actualSprite = new Sprite(cell.cellTexture);
                    cell.actualSprite.setSize(cell.width, cell.height);
                    cell.actualSprite.setPosition(cell.position.x,cell.position.y);
                    break;
                case 2:
                    System.out.println("в методе, квадрат меняет статус");
                    cell.condition=Condition.Quadtrat;
                    cell.cellTexture = new Texture(Gdx.files.internal("QuadrateImgRed.png"));
                    cell.actualSprite = new Sprite(cell.cellTexture);
                    cell.actualSprite.setSize(cell.width, cell.height);
                    cell.actualSprite.setPosition(cell.position.x,cell.position.y);
                    break;

            }
        }

        }

        {

        }


    }
    public boolean hasACrossNear(){  //  Выясняет есть ли крест своего цвета рядом.

        for (Cell nearCell:nearCells
             ) {
            if (nearCell.condition==Condition.Cross&&nearCell.numberMasterOfTheCell==numberMasterOfTheCell)   return true; }
            return false;
        }
    public boolean haveElectricity(){
        //temp.add(this.numCell);
        if(this.hasACrossNear()) return true;
        else
            for (Cell bro:brothers){
                boolean result=bro.haveElectricity();
                if(result) return true;break;
            }
            return false;

    }
    }


