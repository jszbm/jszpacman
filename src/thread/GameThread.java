package thread;

import controller.MovementController;
import frame.GameFrame;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

public class GameThread extends Thread {

    GameFrame gameFrame;

    PacManThread pacManThread = new PacManThread();
    TimeThread timeThread = new TimeThread();
    MovementController movementController = new MovementController();

    public GameThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        System.out.println("Game thread " + currentThread().getName() + " running");
        timeThread.start();
        pacManThread.start();

        while (true) {
            try {
                TimeUnit.MICROSECONDS.sleep(16666);
                SwingUtilities.invokeLater(() -> {
                    gameFrame.updateUi();
                });
            } catch (InterruptedException e) {
                pacManThread.interrupt();
                timeThread.interrupt();
                break;
            }
        }

    }

    public TimeThread getTimeThread() {
        return timeThread;
    }

    public KeyListener getMovementHandler() {
        return movementController;
    }
}
