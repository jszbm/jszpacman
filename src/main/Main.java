package main;

import frame.MainMenuFrame;
import score.ScoreMapService;

import java.io.*;
import java.util.HashMap;


public class Main {

    public static HashMap<String, Integer> scoreMap = new HashMap<>();

    public ScoreMapService scoreMapService = new ScoreMapService();

    public Main() throws IOException {

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //Debug command for checking current directory
        System.out.println("Working directory: " + System.getProperty("user.dir"));

        File scores = new File("Scores.ser");

        if (scores.exists()) {

            FileInputStream fileIn = new FileInputStream("Scores.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            try {
                scoreMap = (HashMap<String, Integer>) objectIn.readObject();
            } catch (EOFException e) {
                System.out.println("File is empty!");
            }

            objectIn.close();
            fileIn.close();

        } else {

            scores.createNewFile();

            FileOutputStream fileOut = new FileOutputStream(scores);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            scoreMap.put("Iwatani", 3333360);

            objectOut.writeObject(scoreMap);
            System.out.println("No \"Scores.ser\" file was found, created an new empty file.");

            FileInputStream fileIn = new FileInputStream("Scores.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            scoreMap = (HashMap<String, Integer>) objectIn.readObject();
            objectIn.close();
            fileIn.close();

        }

        System.out.println(Thread.activeCount());
        System.out.println(Thread.currentThread().getName());

        MainMenuFrame game = new MainMenuFrame();
        System.out.println(scoreMap);
        game.requestFocus();

    }

}
