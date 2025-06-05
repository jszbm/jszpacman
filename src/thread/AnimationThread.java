package thread;


import frame.GameFrame;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class AnimationThread extends Thread {

    private final GameFrame gameFrame;

    public AnimationThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SwingUtilities.invokeLater(() -> {
                    gameFrame.animateEntities();
                    gameFrame.updateUi();
                    gameFrame.repaint();
                });

                TimeUnit.MILLISECONDS.sleep(66);

            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }
    }

}
