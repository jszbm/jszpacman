package thread;

import model.cell.Tunnel;
import model.cell.Wall;
import model.entity.Direction;
import model.entity.Entity;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

                Class<?> forwardNeighbour;
                forwardNeighbour = switch (currentDirection) {
                    case UP -> upNeighbour;
                    case LEFT -> leftNeighbour;
                    case DOWN -> downNeighbour;
                    case RIGHT -> rightNeighbour;
                };

                List<Direction> possibleDirections = new ArrayList<>();

                switch (currentDirection) {
                    case UP:
                    case DOWN:
                        if (leftNeighbour != Wall.class) possibleDirections.add(Direction.LEFT);
                        if (rightNeighbour != Wall.class) possibleDirections.add(Direction.RIGHT);
                        break;
                    case LEFT:
                    case RIGHT:
                        if (upNeighbour != Wall.class) possibleDirections.add(Direction.UP);
                        if (downNeighbour != Wall.class) possibleDirections.add(Direction.DOWN);
                        break;
                }

                if (!possibleDirections.isEmpty() && Math.random() > 0.4) {
                    Collections.shuffle(possibleDirections);
                    entity.setDirection(possibleDirections.getFirst());
                    continue;
                }

                if (forwardNeighbour == Wall.class || forwardNeighbour == Tunnel.class) {
                    entity.setDirection(getReverse(currentDirection));
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

    private Direction getReverse(Direction dir) {
        return switch (dir) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
        };
    }


}
