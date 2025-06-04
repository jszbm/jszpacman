package model.entity;

import model.cell.Cell;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Entity extends Cell {

    int row;
    int column;
    Queue<Cell> cellQueue = new ArrayBlockingQueue<>(2);

    Direction direction = null;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public Queue<Cell> getCellQueue() {
        return cellQueue;
    }
}
