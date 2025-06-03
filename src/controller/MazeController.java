package controller;

import model.PacManTableCellRenderer;
import model.PacManTableModel;
import service.MazeService;

import javax.swing.*;


public class MazeController {

    static MazeService mazeService = new MazeService();

    public PacManTableModel getDefaultMazeModel() {
        var maze = mazeService.getDefaultMaze();
        return new PacManTableModel(maze);
    }

    /*public JTable generateMazeModel() {
        var maze = mazeService.generateMaze();
        return new PacManTableModel(maze);
    }*/


}
