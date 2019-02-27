package com.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.managers.GameManager;
import com.managers.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.gameobjects.GroupCells.allGroups;
import static com.managers.InputManager.activePlayer;


public class Cell {
    public Texture cellTexture;  //текстура клетки
    public Texture CrossImgBlueTexture = new Texture(Gdx.files.internal("CrossImgBlue.png"));
    public Texture CrossImgRedTexture = new Texture(Gdx.files.internal("CrossImgRed.png"));
    public Texture QuadrateImgBlueTexture = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));
    public Texture QuadrateImgRedTexture = new Texture(Gdx.files.internal("QuadrateImgRed.png"));
    public Texture QuadrateBlueDeadTexture =  new Texture(Gdx.files.internal("QuadrateBlueDead.png"));
    public Texture QuadrateRedDeadTexture =  new Texture(Gdx.files.internal("QuadrateRedDead.png"));
    public Sprite QuadrateBlueDeadSprite = new Sprite(QuadrateBlueDeadTexture);
    public Sprite QuadrateRedDeadSprite = new Sprite(QuadrateRedDeadTexture);
    public Sprite QuadrateImgRedSprite = new Sprite(QuadrateImgRedTexture);
    public Sprite CrossImgRedSprite = new Sprite(CrossImgRedTexture);
    public Sprite QuadrateImgBlueSprite = new Sprite(QuadrateImgBlueTexture);
    public Sprite CrossImgBlueSprite = new Sprite(CrossImgBlueTexture);
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

        switch (player.numberOfPlayer) {
            case 1:
                if (condition == Condition.Empty) {
                    condition = Condition.Cross;
                    //cellTexture = new Texture(Gdx.files.internal("CrossImgBlue.png"));
                    //actualSprite = new Sprite(cellTexture);
                    actualSprite = CrossImgBlueSprite;
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x, position.y);
                    numberMasterOfTheCell = player.numberOfPlayer;//присваивается новый хозяин текущей клетке
                    checkEElectricity2();
                    break;
                } else if ((condition == Condition.Cross) && numberMasterOfTheCell != player.numberOfPlayer) {
                    condition = Condition.Quadtrat;
                    findBrothers(player.numberOfPlayer);
                    //cellTexture = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));
                    //actualSprite = new Sprite(cellTexture);
                    actualSprite =QuadrateImgBlueSprite;
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x, position.y);
                    numberMasterOfTheCell = player.numberOfPlayer;//присваивается новый хозяин текущей клетке
                    checkEElectricity();
                    break;

                }

            case 2:
                if (condition == Condition.Empty) {
                    condition = Condition.Cross;
                    //cellTexture = new Texture(Gdx.files.internal("CrossImgRed.png"));
                    //actualSprite = new Sprite(cellTexture);
                    actualSprite=CrossImgRedSprite;
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x, position.y);
                    numberMasterOfTheCell = player.numberOfPlayer;//присваивается новый хозяин текущей клетке
                    checkEElectricity2();
                    break;

                } else if ((condition == Condition.Cross) && numberMasterOfTheCell != player.numberOfPlayer) {
                    condition = Condition.Quadtrat;
                    findBrothers(player.numberOfPlayer);
                    //cellTexture = new Texture(Gdx.files.internal("QuadrateImgRed.png"));
                    //actualSprite = new Sprite(cellTexture);
                    actualSprite=QuadrateImgRedSprite;
                    actualSprite.setSize(width, height);
                    actualSprite.setPosition(position.x, position.y);
                    numberMasterOfTheCell = player.numberOfPlayer;//присваивается новый хозяин текущей клетке
                    checkEElectricity();
                    break;
                }

        }

    }

    private void checkEElectricity() {    //проверка соседних групп квадатов на электричество
        Set<GroupCells> quadratsEnemyToOff= new HashSet<GroupCells>();
        Set<GroupCells> quadratsFriendlyToOn= new HashSet<GroupCells>();
        Iterator<Cell> iterator = nearCells.iterator();
        while (iterator.hasNext()) {
            Cell nearCell = iterator.next();


            if (nearCell.numberMasterOfTheCell != numberMasterOfTheCell && nearCell.condition == Cell.Condition.Quadtrat){
                quadratsEnemyToOff.add(nearCell.group);
                }
                else if (nearCell.numberMasterOfTheCell == numberMasterOfTheCell && nearCell.condition == Cell.Condition.DeadQuadtrat){
                quadratsFriendlyToOn.add(nearCell.group);
                }
            }
            if(!quadratsEnemyToOff.isEmpty()){        //рядом с уничтоженым крестиком были группы вражеских квадратов
                Iterator<GroupCells> iterator1 = quadratsEnemyToOff.iterator();
                while (iterator1.hasNext()) {
                   GroupCells group = iterator1.next();
                    boolean result = false;
                    for (Cell bro :
                            group.brothers) {
                        if (bro.hasACrossNear()) {
                            result = true;
                            break;
                        }
                    }
                    if (!result) {
                        for (Cell bro :
                                group.brothers) {
                            electricity(bro, 0);
                        }
                    }
                }
            }
            if(!quadratsFriendlyToOn.isEmpty()){   //рядом с уничтоженым крестиком были группы дружественных мертвых квадратов
                Iterator<GroupCells> iterator2 = quadratsFriendlyToOn.iterator();
                while (iterator2.hasNext()) {
                    GroupCells group = iterator2.next();
                    for (Cell bro :
                                group.brothers) {
                            electricity(bro, 1);
                        }
                    }
            }
    }

    private void checkEElectricity2() {
        Set<GroupCells> quadratsFriendlyToOn = new HashSet<GroupCells>();
        Iterator<Cell> iterator = nearCells.iterator();
        while (iterator.hasNext()) {
            Cell nearCell = iterator.next();
            if (nearCell.numberMasterOfTheCell == numberMasterOfTheCell && nearCell.condition == Cell.Condition.DeadQuadtrat) {
                quadratsFriendlyToOn.add(nearCell.group);
                if (!quadratsFriendlyToOn.isEmpty()) {   //рядом с  крестиком были группы дружественных мертвых квадратов
                    Iterator<GroupCells> iterator2 = quadratsFriendlyToOn.iterator();
                    while (iterator2.hasNext()) {
                        GroupCells group = iterator2.next();
                        for (Cell bro :
                                group.brothers) {
                            electricity(bro, 1);
                        }
                    }
                }
            }
        }
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
                else if (nearCell.group!=null&&this.group!=null&&nearCell.group!=this.group){
                    nearCell.group.addCoupleCellsToGroopCells(this.group.brothers);
                    nearCell.group.addCellToGroopCells(this);
                    for (Cell bro :
                            this.group.brothers){
                        bro.group = nearCell.group;
                    this.group=nearCell.group;
                        }

                }
                  else if (nearCell.group!=null&&this.group!=null&&nearCell.group!=this.group){
                      continue;
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
                    cell.condition = Condition.DeadQuadtrat;
                    //cell.cellTexture = new Texture(Gdx.files.internal("QuadrateBlueDead.png"));
                    //cell.actualSprite = new Sprite(cell.cellTexture);
                    cell.actualSprite=cell.QuadrateBlueDeadSprite;
                    cell.actualSprite.setSize(cell.width, cell.height);
                    cell.actualSprite.setPosition(cell.position.x, cell.position.y);
                    break;
                case 2:
                    cell.condition = Condition.DeadQuadtrat;
                    //cell.cellTexture = new Texture(Gdx.files.internal("QuadrateRedDead.png"));
                    //cell.actualSprite = new Sprite(cell.cellTexture);
                    cell.actualSprite=cell.QuadrateRedDeadSprite;
                    cell.actualSprite.setSize(cell.width, cell.height);
                    cell.actualSprite.setPosition(cell.position.x, cell.position.y);
                    break;
            }
        }
            if (switcher == 1) {
                switch (cell.numberMasterOfTheCell) {
                    case 1:
                        System.out.println("в методе, квадрат меняет статус energy 1 qua1");
                        cell.condition = Condition.Quadtrat;
                        //cell.cellTexture = new Texture(Gdx.files.internal("QuadrateImgBlue.png"));
                        //cell.actualSprite = new Sprite(cell.cellTexture);
                        cell.actualSprite = cell.QuadrateImgBlueSprite;
                        cell.actualSprite.setSize(cell.width, cell.height);
                        cell.actualSprite.setPosition(cell.position.x, cell.position.y);
                        break;
                    case 2:
                        System.out.println("в методе, квадрат меняет статус energy 1 qua2");
                        cell.condition = Condition.Quadtrat;
                        //cell.cellTexture = new Texture(Gdx.files.internal("QuadrateImgRed.png"));
                        //cell.actualSprite = new Sprite(cell.cellTexture);
                        cell.actualSprite=cell.QuadrateImgRedSprite;
                        cell.actualSprite.setSize(cell.width, cell.height);
                        cell.actualSprite.setPosition(cell.position.x, cell.position.y);
                        break;

                }
            }

        }


    public boolean hasACrossNear() {  //  Выясняет есть ли крест своего цвета рядом.

        for (Cell nearCell : nearCells
        ) {
            if (nearCell.condition == Condition.Cross && nearCell.numberMasterOfTheCell == numberMasterOfTheCell)
                return true;
        }
        return false;
    }
}


