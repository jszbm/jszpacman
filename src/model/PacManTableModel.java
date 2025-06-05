package model;

import model.cell.*;
import model.entity.PacMan;
import model.entity.RedGhost;

import javax.swing.table.AbstractTableModel;

public class PacManTableModel extends AbstractTableModel {

    private final Cell[][] items;
    private int dotCount;

    public PacManTableModel(int[][] maze) {
        this.items = convert(maze);
    }

    public Cell[][] convert(int[][] maze) {

        Cell[][] convertedMaze = new Cell[maze.length][maze[0].length];

        for (int row = 0; row < maze.length; ) {
            for (int i = 0; i < maze[row].length; ) {
                switch (maze[row][i]) {
                    case 0:
                        convertedMaze[row][i] = new Cell();
                        break;
                    case 1:
                        convertedMaze[row][i] = new Wall();
                        break;
                    case 2:
                        convertedMaze[row][i] = new Tunnel();
                        break;
                    case 3:
                        convertedMaze[row][i] = new Dot();
                        this.dotCount++;
                        break;
                    case 4:
                        convertedMaze[row][i] = new PowerDot();
                        this.dotCount++;
                        break;
                    case 5:
                        convertedMaze[row][i] = new Gate();
                        break;
                    case 6:
                        convertedMaze[row][i] = new RedGhost();
                        System.out.println("Red ghost: " + row + " " + i);
                        break;
                    case 7:
                        convertedMaze[row][i] = new PacMan();
                        System.out.println("Pac Man: " + row + " " + i);
                        break;
                    default:
                        convertedMaze[row][i] = new Cell();
                }
                i++;
            }
            row++;
        }
        return convertedMaze;
    }


    @Override
    public int getRowCount() {
        return items.length;
    }

    @Override
    public int getColumnCount() {
        return items[0].length;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        items[rowIndex][columnIndex] = (Cell) value;
        super.setValueAt(value, rowIndex, columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return items[rowIndex][columnIndex];
    }

    public int getDotCount() {
        return dotCount;
    }

    public void deleteDot() {
        this.dotCount--;
    }
}


