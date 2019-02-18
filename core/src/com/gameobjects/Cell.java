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


public class Cell {
    public Texture cellTexture;  //текстура клетки
    public Sprite actualSprite;
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
    boolean checked;
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
        Array<Cell> quadratWithNoGroup =new Array<Cell>();//список соседних квадратов без группы
        //проверка всех соседей - квадратов
        Iterator<Cell> iterator = nearCells.iterator();
        while (iterator.hasNext()) {
            Cell nearCell = iterator.next();

            if (nearCell.numberMasterOfTheCell == numberMasterOfTheCell && (nearCell.condition == Cell.Condition.Quadtrat||nearCell.condition == Cell.Condition.DeadQuadtrat)) {
                  if (nearCell.group!=null&&this.group==null){
                    this.group=nearCell.group;
                    group.addCellToGroopCells(this);
                }
                else if (nearCell.group!=null&&this.group!=null){
                    nearCell.group.addCoupleCellsToGroopCells(this.group.brothers);
                    nearCell.group.addCellToGroopCells(this);
                    for (Cell bro :
                            this.group.brothers){
                        bro.group = nearCell.group;
                    this.group=nearCell.group;
                        }

                }
                else{
                      quadratWithNoGroup.add(nearCell);
                }
            }
            }
            if (this.group!=null&&quadratWithNoGroup.size!=0){
            for (Cell quadrat:
                    quadratWithNoGroup) {
                quadrat.group=this.group;
                group.addCellToGroopCells(quadrat);


                }
        }
            else if(this.group==null&&quadratWithNoGroup.size!=0){
                group = new GroupCells();
                group.masterOfTheGroupCells=activePlayer.numberOfPlayer;
                group.brothers.add(this);
                allGroups.add(group);
                for (Cell quadrat:
                        quadratWithNoGroup) {
                    quadrat.group=this.group;
                    group.addCellToGroopCells(quadrat);
                }

        }
            else if(this.group==null&&quadratWithNoGroup.size==0){
                group = new GroupCells();
                group.masterOfTheGroupCells=activePlayer.numberOfPlayer;
                group.brothers.add(this);
                allGroups.add(group);
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

    public void uncheck(){
        this.checked=false;

    }

}


