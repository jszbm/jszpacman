package model.entity;

import model.cell.Cell;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public abstract class Entity extends Cell {

    BufferedImage[] textureRight = new BufferedImage[2];
    BufferedImage[] textureLeft = new BufferedImage[2];
    BufferedImage[] textureUp = new BufferedImage[2];
    BufferedImage[] textureDown = new BufferedImage[2];
    BufferedImage[] textureIdle = new BufferedImage[2];
    BufferedImage[] textureFreeze = new BufferedImage[2];

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

    public abstract void nextTexture();

    public void nextFreezeTexture() {
        texture = (texture == textureFreeze[0]) ? textureFreeze[1] : textureFreeze[0];
    };

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
