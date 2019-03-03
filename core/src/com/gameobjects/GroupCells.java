package com.gameobjects;

import com.badlogic.gdx.utils.Array;

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
}

