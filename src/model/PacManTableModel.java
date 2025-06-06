package model;

import model.cell.*;
import model.entity.*;

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
                    case 8:
                        convertedMaze[row][i] = new PinkGhost();
                        System.out.println("Pink ghost: " + row + " " + i);
                        break;
                    case 9:
                        convertedMaze[row][i] = new CyanGhost();
                        System.out.println("Cyan ghost: " + row + " " + i);
                        break;
                    case 10:
                        convertedMaze[row][i] = new OrangeGhost();
                        System.out.println("Orange ghost: " + row + " " + i);
                        break;
                   case 11:
                        convertedMaze[row][i] = new Orange();
                        System.out.println("Orange: " + row + " " + i);
                        break;
                   case 12:
                        convertedMaze[row][i] = new Apple();
                        System.out.println("Apple: " + row + " " + i);
                        break;
                   case 13:
                        convertedMaze[row][i] = new Axe();
                        System.out.println("Axe: " + row + " " + i);
                        break;
                   case 14:
                        convertedMaze[row][i] = new Bell();
                        System.out.println("Bell: " + row + " " + i);
                        break;
                   case 15:
                        convertedMaze[row][i] = new Key();
                        System.out.println("Key: " + row + " " + i);
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


