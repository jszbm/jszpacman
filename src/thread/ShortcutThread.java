package thread;

import frame.CustomFrame;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class ShortcutThread extends Thread {

    CustomFrame frame;

    public ShortcutThread(CustomFrame frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SwingUtilities.invokeLater(() -> {
                    frame.processQuitShortcut();
                });

                TimeUnit.MILLISECONDS.sleep(100);

            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }

    }

}
