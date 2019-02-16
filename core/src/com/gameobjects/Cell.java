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

import static com.gameobjects.GroupCells.allGroups;
import static com.managers.InputManager.activePlayer;
import static com.managers.InputManager.uncheckAllQuadrats;


public class Cell {
    public Texture cellTexture;  //текстура клетки
    public Sprite actualSprite;
    public Texture crossBlue = new Texture(Gdx.files.internal("CrossImgBlue.png"));

    public Texture crossRed = new Texture(Gdx.files.internal("CrossImgRed.png"));

    public Texture quadrateBlue = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));

    public Texture quadrateRed = new Texture(Gdx.files.internal("QuadrateImgRed.png"));

    public Vector2 position = new Vector2();

    public enum Condition {Empty, Cross, Quadtrat, DeadQuadtrat}

    public Condition condition;
    static final float SIZE = GameManager.height / 11;
    public float height; //высота
    public float width;
    SpriteBatch batch = new SpriteBatch();
    public int numberMasterOfTheCell;
    public String numCell;
    public Array<Cell> nearCells;   //список соседних клеток
    public Array<Cell> brothers;    //список соседних квадратов
    boolean checked;
    boolean electro;
    GroupCells group;


    public Cell(float x, float y) {
        condition = Condition.Empty;
        cellTexture = new Texture(Gdx.files.internal("CellEmpty.png"));
        position.set(x, y);
        width = SIZE;
        height = SIZE;
        actualSprite = new Sprite(cellTexture);
        actualSprite.setSize(this.width, this.height);
        actualSprite.setPosition(this.position.x, this.position.y);
        nearCells = new Array<Cell>();

    }

    public void render(SpriteBatch batch) {
        actualSprite.draw(batch);
    }

    public void isClicked(Player player) {
        System.out.println("я в методе Cell.isClicked");

        switch (player.numberOfPlayer) {
            case 1:
                if (condition == Condition.Empty) {
                    condition = Condition.Cross;
                    cellTexture = new Texture(Gdx.files.internal("CrossImgBlue.png"));
                    actualSprite = new Sprite(cellTexture);
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x, position.y);
                    break;
                } else if ((condition == Condition.Cross) && numberMasterOfTheCell != player.numberOfPlayer) {
                    condition = Condition.Quadtrat;
                    brothers = new Array<Cell>();
                    findBrothers(player.numberOfPlayer);
                    cellTexture = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));
                    actualSprite = new Sprite(cellTexture);
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x, position.y);
                    break;

                }

            case 2:
                if (condition == Condition.Empty) {
                    condition = Condition.Cross;
                    cellTexture = new Texture(Gdx.files.internal("CrossImgRed.png"));
                    actualSprite = new Sprite(cellTexture);
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x, position.y);
                    break;

                } else if ((condition == Condition.Cross) && numberMasterOfTheCell != player.numberOfPlayer) {
                    condition = Condition.Quadtrat;
                    brothers = new Array<Cell>();
                    findBrothers(player.numberOfPlayer);
                    cellTexture = new Texture(Gdx.files.internal("QuadrateImgRed.png"));
                    actualSprite = new Sprite(cellTexture);
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x, position.y);
                    break;
                }

        }
        numberMasterOfTheCell = player.numberOfPlayer; //присваивается новый хозяин текущей клетке
    }

    private void findBrothers(int numberMasterOfTheCell) {
        //проверка всех соседей - квадратов
        Iterator<Cell> iterator = nearCells.iterator();
        while (iterator.hasNext()) {
            Cell nearCell = iterator.next();
            if (nearCell.numberMasterOfTheCell == numberMasterOfTheCell && (nearCell.condition == Cell.Condition.Quadtrat||nearCell.condition == Cell.Condition.DeadQuadtrat)) {
                if(nearCell.group!=null){
                    this.group=nearCell.group;
                    group.addCellToGroopCells(this);
                }
                else{
                    group = new GroupCells();
                    group.masterOfTheGroupCells=activePlayer.numberOfPlayer;
                    allGroups.add(group);
                    nearCell.group=group;
                    group.addCellToGroopCells(this);
                    group.addCellToGroopCells(nearCell);
                }

            } else
                System.out.println("по соседству пока квадратов нет");
        }
    }

    public static void electricity(Cell cell,  int switcher) {

        if (switcher == 0) {
            switch (cell.numberMasterOfTheCell) {
                case 1:
                    System.out.println("в методе, квадрат меняет статус");
                    cell.condition = Condition.DeadQuadtrat;
                    cell.cellTexture = new Texture(Gdx.files.internal("QuadrateBlueDead.png"));
                    cell.actualSprite = new Sprite(cell.cellTexture);
                    cell.actualSprite.setSize(cell.width, cell.height);
                    cell.actualSprite.setPosition(cell.position.x, cell.position.y);
                    break;
                case 2:
                    System.out.println("в методе, квадрат меняет статус");
                    cell.condition = Condition.DeadQuadtrat;
                    cell.cellTexture = new Texture(Gdx.files.internal("QuadrateRedDead.png"));
                    cell.actualSprite = new Sprite(cell.cellTexture);
                    cell.actualSprite.setSize(cell.width, cell.height);
                    cell.actualSprite.setPosition(cell.position.x, cell.position.y);
                    break;

            }
        }
            if (switcher == 1) {
                switch (cell.numberMasterOfTheCell) {
                    case 1:
                        System.out.println("в методе, квадрат меняет статус");
                        cell.condition = Condition.Quadtrat;
                        cell.cellTexture = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));
                        cell.actualSprite = new Sprite(cell.cellTexture);
                        cell.actualSprite.setSize(cell.width, cell.height);
                        cell.actualSprite.setPosition(cell.position.x, cell.position.y);
                        break;
                    case 2:
                        System.out.println("в методе, квадрат меняет статус");
                        cell.condition = Condition.Quadtrat;
                        cell.cellTexture = new Texture(Gdx.files.internal("QuadrateImgRed.png"));
                        cell.actualSprite = new Sprite(cell.cellTexture);
                        cell.actualSprite.setSize(cell.width, cell.height);
                        cell.actualSprite.setPosition(cell.position.x, cell.position.y);
                        break;

                }
            }

        }

        {

        }




    public boolean hasACrossNear() {  //  Выясняет есть ли крест своего цвета рядом.

        for (Cell nearCell : nearCells
        ) {
            if (nearCell.condition == Condition.Cross && nearCell.numberMasterOfTheCell == numberMasterOfTheCell)
                return true;
        }
        return false;
    }

    public boolean haveElectricity() {  //Проверка есть ли питание у квадрата
        //temp.add(this.numCell);
        if (this.hasACrossNear()) return true;
        else
            for (Cell bro : brothers) {

                if (!bro.checked){

                bro.checked=true;
                boolean result = bro.haveElectricity();

                if (result) {
                    uncheckAllQuadrats();
                    return true;}
                break;}

            }
        return false;

    }
    public void uncheck(){
        this.checked=false;

    }

}


