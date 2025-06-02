package model;

import model.cell.*;

import javax.swing.table.AbstractTableModel;
import java.io.IOException;

public class PacManTableModel extends AbstractTableModel {

    private final Cell[][] items;
    private final int[] columns;

    public PacManTableModel(Cell[][] items, int[] columns) {
        this.items = items;
        this.columns = columns;
    }

    public PacManTableModel(int[][] maze) {
        this.items = convert(maze);
        this.columns = new int[maze[0].length];
    }

    public Cell[][] convert(int[][] maze) {

        Cell[][] convertedMaze = new Cell[maze.length][maze[0].length];

        for (int row = 0; row < maze.length; ) {
            for (int i = 0; i < maze[row].length; ) {
                try {
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
                            break;
                        case 4:
                            convertedMaze[row][i] = new PowerDot();
                            break;
                        default:
                            convertedMaze[row][i] = new Cell();
                    }
                } catch (IOException e) {
                    System.out.println("Texture could not be loaded!");
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
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        items[rowIndex][columnIndex] = (Cell) aValue;
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return items[rowIndex][columnIndex];
    }

}


