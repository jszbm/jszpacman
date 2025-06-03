package thread;

import frame.GameFrame;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GameThread extends Thread {

    GameFrame gameFrame;

    TimeThread timeThread;
    GameLogicThread gameLogicThread;
    AnimationThread animationThread;
    GhostThread redGhostThread;

    public GameThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.gameLogicThread = new GameLogicThread(gameFrame);
        this.animationThread = new AnimationThread(gameFrame);
        this.redGhostThread = new GhostThread(gameFrame.getRedGhost(), gameFrame.getMazeTable());
        this.timeThread = new TimeThread();
    }

    @Override
    public void run() {
        System.out.println("Game thread " + currentThread().getName() + " running");
        timeThread.start();
        gameLogicThread.start();
        redGhostThread.start();
        while (true) {
            try {
                TimeUnit.MICROSECONDS.sleep(16666);
                SwingUtilities.invokeLater(() -> {
                    gameFrame.updateUi();
                });
            } catch (InterruptedException e) {
                timeThread.interrupt();
                gameLogicThread.interrupt();
                animationThread.interrupt();
                break;
            }
        }

    }

    public TimeThread getTimeThread() {
        return timeThread;
    }

}
