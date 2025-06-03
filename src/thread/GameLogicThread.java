package thread;

import frame.GameFrame;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GameLogicThread extends Thread {

    GameFrame gameFrame;

    public GameLogicThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                SwingUtilities.invokeLater(() -> {
                    gameFrame.executeGameLogic();
                });
            } catch (InterruptedException e) {
                break;
            }
        }

    }

}
