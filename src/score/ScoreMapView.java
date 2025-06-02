/*
package score;

import controller.ScoreMapController;

import javax.swing.*;

public class ScoreMapView {

    static public String name;
    static public int score;
    static private ScoreMapController scoreMapController = new ScoreMapController();

    public static void newScore(int score) {
        name = JOptionPane.showInputDialog("Enter your name: ");
        scoreMapController.addScore(name, score);

    }

    public void debugScore() {
        name = JOptionPane.showInputDialog("Name: ");
        score = Integer.parseInt(JOptionPane.showInputDialog("Score: "));

        scoreMapController.debugScore(name, score);
    }

    public void printMap() {
        System.out.println(scoreMapController.getScoreMap().getMap().toString());
    }

}
*/
