package thread;

import model.cell.*;
import model.entity.Direction;
import model.entity.Entity;

import javax.swing.*;
import java.lang.foreign.PaddingLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GhostThread extends Thread {

    Entity entity;
    JTable mazeTable;

    public GhostThread(Entity entity, JTable mazeTable) {
        this.entity = entity;
        this.mazeTable = mazeTable;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);

                Direction currentDirection = entity.getDirection();

                Class<?> leftNeighbour = mazeTable.getValueAt(entity.getRow(), entity.getColumn() - 1).getClass();
                Class<?> rightNeighbour = mazeTable.getValueAt(entity.getRow(), entity.getColumn() + 1).getClass();
                Class<?> upNeighbour = mazeTable.getValueAt(entity.getRow() - 1, entity.getColumn()).getClass();
                Class<?> downNeighbour = mazeTable.getValueAt(entity.getRow() + 1, entity.getColumn()).getClass();

                Class<?> directionNeighbour;
                directionNeighbour = switch (currentDirection) {
                    case UP -> mazeTable.getValueAt(entity.getRow() - 1, entity.getColumn()).getClass();
                    case LEFT -> mazeTable.getValueAt(entity.getRow(), entity.getColumn() - 1).getClass();
                    case DOWN -> mazeTable.getValueAt(entity.getRow() + 1, entity.getColumn()).getClass();
                    case RIGHT -> mazeTable.getValueAt(entity.getRow(), entity.getColumn() + 1).getClass();
                };

                List<Class<?>> neighboursList;

                double random = Math.random();

                if (currentDirection == Direction.DOWN || currentDirection == Direction.UP){
                    neighboursList = new ArrayList<>(List.of(leftNeighbour, rightNeighbour));
                    boolean containsNotWall = neighboursList.contains(Entity.class) || neighboursList.contains(Dot.class) || neighboursList.contains(Cell.class) || !neighboursList.contains(Wall.class);
                    if (random > 0.4 && containsNotWall) {
                        turnHorizontal();
                    }
                } else {
                    neighboursList = new ArrayList<>(List.of(upNeighbour, downNeighbour));
                    boolean containsNotWall = neighboursList.contains(Entity.class) || neighboursList.contains(Dot.class) || neighboursList.contains(Cell.class) || !neighboursList.contains(Wall.class);
                    if (random > 0.4 && containsNotWall) {
                        turnVertical();
                    }
                }

                if (directionNeighbour == Wall.class) {
                    chooseDirection();
                }


            } catch (InterruptedException e) {
                break;
            }
        }
    }

    void chooseDirection() {
        List<Direction> allDirections = new ArrayList<>(List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
        allDirections.remove(entity.getDirection());
        Collections.shuffle(allDirections);
        entity.setDirection(allDirections.getFirst());
    }

    void turnHorizontal() {
        if (Math.random() > 0.5){
            entity.setDirection(Direction.LEFT);
        } else {
            entity.setDirection(Direction.RIGHT);
        }

    }

    void turnVertical() {
        if (Math.random() > 0.5){
            entity.setDirection(Direction.UP);
        } else {
            entity.setDirection(Direction.DOWN);
        }

    }

}
