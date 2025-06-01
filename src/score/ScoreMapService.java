package score;

import main.Main;

import java.io.*;

//Class for handling Score map manipulations
public class ScoreMapService {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    ScoreMap scoreMap = new ScoreMap();

    public ScoreMapService() {

    }

    public void addScore(String name, int score) {
        if (name == null || name.isEmpty()) {
            System.out.println("Score was abandoned");
            System.out.println(scoreMap);

        } else {
            try {

                FileOutputStream fileOut = new FileOutputStream("Scores.ser");
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                scoreMap.getMap().put(name, score);

                objectOut.writeObject(scoreMap);

                objectOut.close();
                fileOut.close();
                System.out.println(scoreMap);
            } catch (IOException ex) {
                System.err.println("Could not add a new score.");
            }

        }
    }

    public void debugScore(String name, int score) {
        try {
            FileOutputStream fileOut = new FileOutputStream("Scores.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            System.out.println("Name: ");
            name = reader.readLine();
            System.out.println("Score: ");
            score = Integer.parseInt(reader.readLine());
            System.out.println(name + ": " + score);
            Main.scoreMap.put(name, score);

            objectOut.writeObject(scoreMap);
            objectOut.close();
            fileOut.close();

        } catch (IOException e) {
            System.err.println("Could not add a new score");
        }

        System.out.println("Debug score added");
    }

}
