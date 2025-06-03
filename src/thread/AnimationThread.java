package thread;

import frame.GameFrame;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class AnimationThread extends Thread {

    GameFrame gameFrame;

    public AnimationThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
                SwingUtilities.invokeLater(() -> {
                    //gameFrame.animatePlayer();
                });
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
