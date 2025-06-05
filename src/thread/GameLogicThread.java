package thread;

import frame.GameFrame;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GameLogicThread extends Thread {

    private final GameFrame gameFrame;

    public GameLogicThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SwingUtilities.invokeLater(() -> gameFrame.executeGameLogic());

                TimeUnit.MILLISECONDS.sleep(100);

            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }

    }

}
