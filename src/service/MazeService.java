package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MazeService {

    int rows;
    int cols;
    int centerRow;
    int centerColumn;
    int[][] maze;
    Random random = new Random();

    int[][] defaultMaze = {
            //Empty - 0
            //Wall - 1
            //Tunnel - 2
            //Dot - 3
            //Power pellet - 4
            //Gate - 5
            //Red ghost - 6
            //Pac-Man - 7
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1},
            {1, 4, 1, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 1, 4, 1},
            {1, 3, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 3, 3, 3, 1, 3, 1, 1, 1, 3, 1, 3, 3, 3, 1, 1, 1, 1},
            {0, 0, 0, 1, 3, 1, 3, 0, 0, 0, 0, 0, 0, 0, 3, 1, 3, 1, 0, 0, 0},
            {1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 5, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1},
            {2, 0, 0, 0, 3, 1, 1, 0, 1, 6, 0, 0, 1, 0, 1, 1, 3, 0, 0, 0, 2},
            {1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1},
            {0, 0, 0, 1, 3, 3, 3, 0, 0, 0, 7, 0, 0, 0, 3, 3, 3, 1, 0, 0, 0},
            {1, 1, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1},
            {1, 4, 3, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 3, 4, 1},
            {1, 3, 1, 1, 1, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 1, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    // 5x7
    int[][] cage = {
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 5, 1, 1, 0},
            {0, 1, 6, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 7, 0, 0, 0}
    };


    public int[][] getDefaultMaze() {
        return defaultMaze;
    }

    public int[][] generateMaze(int rows, int cols) {

        this.rows = rows;
        this.cols = cols;
        this.centerRow = rows / 2;
        this.centerColumn = cols / 2;
        this.maze = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = 1;
            }
        }

        int startRow = 1 + random.nextInt(rows - 2);
        int startCol = 1 + random.nextInt(cols - 2);
        carve(startRow, startCol);
        fillEdges();
        deleteWalls(0.3);
        addPowerDots(0.02);
        insertCage();

        return maze;
    }

    private void carve(int row, int col) {
        maze[row][col] = 3;

        ArrayList<Direction> directions = new ArrayList<>();
        Collections.addAll(directions, Direction.values());
        Collections.shuffle(directions);

        for (Direction dir : directions) {
            int newRow = row + dir.dr * 2;
            int newCol = col + dir.dc * 2;

            if (isValid(newRow, newCol) && maze[newRow][newCol] == 1) {
                maze[row + dir.dr][col + dir.dc] = 3;
                carve(newRow, newCol);
            }
        }
    }

    private void insertCage() {
        for (int i = 0; i < cage.length; i++) {
            for (int j = 0; j < cage[i].length; j++) {
                int targetRow = centerRow - 2 + i;
                int targetCol = centerColumn - 3 + j;

                if (targetRow < maze.length && targetCol < maze[0].length) {
                    maze[targetRow][targetCol] = cage[i][j];
                }
            }
        }
    }

    private void fillEdges() {
        for (int i = 0; i < maze.length; i++) {
            if (i == 0 || i == rows - 1) {
                Arrays.fill(maze[i], 1);
            }
            maze[i][0] = 1;
            maze[i][cols - 1] = 1;
        }
    }

    private void deleteWalls(double chance) {
        for (int i = 1; i < rows - 2; i++) {
            for (int j = 1; j < cols - 2; j++) {

                if (maze[i][j] != 1) continue;

                int leftNeighbor = maze[i][j - 1];
                int rightNeighbor = maze[i][j + 1];
                int upNeighbor = maze[i - 1][j];
                int dowNeighbor = maze[i + 1][j];

                int sum = leftNeighbor + rightNeighbor + upNeighbor + dowNeighbor;

                if (sum == 3 || sum == 6 || sum == 4) {
                    continue;
                } else if (leftNeighbor + rightNeighbor == 2 && random.nextDouble() < chance) {
                    maze[i][j] = 3;
                } else if (upNeighbor + dowNeighbor == 2 && random.nextDouble() < chance) {
                    maze[i][j] = 3;
                }

            }
        }
    }

    private void addPowerDots(double chance) {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (maze[i][j] == 3 && random.nextDouble() < chance) {
                    maze[i][j] = 4;
                }
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row > 0 && row < rows - 1 && col > 0 && col < cols - 1;
    }

    private enum Direction {
        NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1);

        final int dr;
        final int dc;

        Direction(int dr, int dc) {
            this.dr = dr;
            this.dc = dc;
        }
    }

}
