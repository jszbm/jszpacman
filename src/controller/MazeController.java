package controller;

import model.PacManTableModel;
import service.MazeService;

public class MazeController {

    static MazeService mazeService = new MazeService();

    public PacManTableModel getDefaultMazeModel() {
        var maze = mazeService.getDefaultMaze();
        return new PacManTableModel(maze);
    }

    public PacManTableModel generateMazeModel(int rows, int columns) {
        var maze = mazeService.generateMaze(rows, columns);
        return new PacManTableModel(maze);
    }


}
