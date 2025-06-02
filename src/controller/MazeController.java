package controller;

import model.PacManTableModel;
import service.MazeService;

import javax.swing.*;


public class MazeController {

    static MazeService mazeService = new MazeService();


    public JTable createDefaultMazeTable() {

        var maze = mazeService.getDefaultMaze();

        var mazeTable = new JTable();
        mazeTable.setModel(new PacManTableModel(maze));
        return mazeTable;
    }


}
