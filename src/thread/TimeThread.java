package thread;

import frame.GameFrame;

import java.util.concurrent.TimeUnit;

public class TimeThread extends Thread {


    GameFrame gameFrame;
    int time;

    public TimeThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!gameFrame.isPaused()) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    time++;
                    gameFrame.setTime(time);
                } else {
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }
    }

    public int getTime() {
        return time;
    }

}
