package score;

import javax.swing.*;

public class ScoreMapController {

    static public String name;
    static public int score;
    static private ScoreMapService scoreMapService = new ScoreMapService();

    public static void newScore(int score) {
        name = JOptionPane.showInputDialog("Enter your name: ");

        scoreMapService.addScore(name, score);

    }

    public void printMap() {
        System.out.println(scoreMapService.scoreMap.getMap().toString());
    }

}
