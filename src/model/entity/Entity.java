package model.entity;

import model.cell.Cell;

public class Entity extends Cell {

    int row;
    int column;
    Direction direction = null;


    public Direction getDirection() {
        return direction;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
