package thread;

import frame.GameFrame;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GameThread extends Thread {

    GameFrame gameFrame;

    TimeThread timeThread;
    GameLogicThread gameLogicThread;
    AnimationThread animationThread;
    RedGhostThread redGhostThread;
    GhostThread pinkGhostThread;
    GhostThread cyanGhostThread;
    GhostThread orangeGhostThread;
    GhostLogicThread ghostLogicThread;

    public GameThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        JTable mazeTable = gameFrame.getMazeTable();
        gameLogicThread = new GameLogicThread(gameFrame);
        animationThread = new AnimationThread(gameFrame);
        redGhostThread = new RedGhostThread(gameFrame.getRedGhost(), mazeTable);
        pinkGhostThread = new GhostThread(gameFrame.getPinkGhost(), mazeTable);
        cyanGhostThread = new GhostThread(gameFrame.getCyanGhost(), mazeTable);
        orangeGhostThread = new GhostThread(gameFrame.getOrangeGhost(), mazeTable);
        ghostLogicThread = new GhostLogicThread(gameFrame);
        timeThread = new TimeThread(gameFrame);
    }

    @Override
    public void run() {
        System.out.println("Game thread " + currentThread().getName() + " running");
        timeThread.start();
        ghostLogicThread.start();
        gameLogicThread.start();
        redGhostThread.start();
        pinkGhostThread.start();
        cyanGhostThread.start();
        orangeGhostThread.start();
        animationThread.start();

        try {
            while (isAlive()) {
                TimeUnit.MICROSECONDS.sleep(33333);
            }
        } catch (InterruptedException e) {
            timeThread.interrupt();
            ghostLogicThread.interrupt();
            gameLogicThread.interrupt();
            redGhostThread.interrupt();
            pinkGhostThread.interrupt();
            cyanGhostThread.interrupt();
            orangeGhostThread.interrupt();
            animationThread.interrupt();
        }


    }

}
