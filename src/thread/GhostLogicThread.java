package thread;

import frame.GameFrame;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GhostLogicThread extends Thread {

    private final GameFrame gameFrame;
    int updateTime = 0;

    public GhostLogicThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                updateTime = gameFrame.getGhostUpdateTime();

                SwingUtilities.invokeLater(() -> gameFrame.executeGhostLogic());

                TimeUnit.MILLISECONDS.sleep(updateTime);

            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }

    }

}