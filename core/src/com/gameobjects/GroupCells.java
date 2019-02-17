package com.gameobjects;

import com.badlogic.gdx.utils.Array;
import static com.gameobjects.Cell.electricity;

public class GroupCells {
    public static Array<GroupCells> allGroups = new Array<GroupCells>();
    public int masterOfTheGroupCells;
    public Array<Cell> brothers = new Array<Cell>();


    public void addCellToGroopCells(Cell cell) {
        brothers.add(cell);

    }

    public void addCoupleCellsToGroopCells(Array<Cell> cells) {
        brothers.addAll(cells);

    }

    public void checkElectricity() {

        boolean result = false;
        for (Cell bro :
                brothers) {
            if (bro.hasACrossNear()) {
                result = true;
                break;
            }
        }
        if (result) {
            for (Cell bro :
                    brothers) {
                electricity(bro, 1);
            }
        } else {
            for (Cell bro :
                    brothers) {
                electricity(bro, 0);
            }

        }
    }


}

