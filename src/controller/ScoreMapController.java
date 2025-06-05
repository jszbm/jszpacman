package controller;

import model.score.ScoreMap;

import java.io.*;

public class ScoreMapController {

    static ScoreMap scoreMap = new ScoreMap();
    static String scoresPath = "scores/scores.ser";

    public ScoreMapController() {
        loadScores();
    }

    public void addScore(String name, int score) {
        if (name == null || name.isEmpty()) {
            System.err.println("Score was abandoned");
        } else {
            try {
                FileOutputStream fileOut = new FileOutputStream(scoresPath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                scoreMap.putScore(name, score);

                objectOut.writeObject(scoreMap);

                objectOut.close();
                fileOut.close();
            } catch (IOException ex) {
                System.err.println("Could not add a new score");
            }

        }
    }

    public void debugScore(String name, int score) {
        try {
            FileOutputStream fileOut = new FileOutputStream(scoresPath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            System.out.println(name + ": " + score);
            scoreMap.putScore(name, score);

            objectOut.writeObject(scoreMap);

            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Could not add a new score");
        }

        System.out.println("Debug score added");
    }

    public void loadScores() {
        try {
            FileInputStream fileIn = new FileInputStream(scoresPath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            scoreMap = (ScoreMap) objectIn.readObject();

            fileIn.close();
            objectIn.close();
        } catch (IOException e) {
            System.err.println("Could not read the scores file");
        } catch (ClassNotFoundException ex) {
            System.err.println("Class not found");
        }
    }

    public ScoreMap getScoreMap() {
        return scoreMap;
    }
}
