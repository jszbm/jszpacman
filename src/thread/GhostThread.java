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
                TimeUnit.MILLISECONDS.sleep(100);

                Direction direction = entity.getDirection();

                int column = entity.getColumn();
                int row = entity.getRow();

                Class<?> leftNeighbour = mazeTable.getValueAt(row, column - 1).getClass();
                Class<?> rightNeighbour = mazeTable.getValueAt(row, column + 1).getClass();
                Class<?> upNeighbour = mazeTable.getValueAt(row - 1, column).getClass();
                Class<?> downNeighbour = mazeTable.getValueAt(row + 1, column).getClass();

                Class<?> forwardNeighbour;
                forwardNeighbour = switch (direction) {
                    case UP -> upNeighbour;
                    case LEFT -> leftNeighbour;
                    case DOWN -> downNeighbour;
                    case RIGHT -> rightNeighbour;
                };

                List<Direction> possibleDirections = new ArrayList<>();

                switch (direction) {
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
                    entity.setDirection(getReverse(direction));
                }

            } catch (InterruptedException e) {
                break;
            }
        }
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
