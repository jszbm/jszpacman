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
                TimeUnit.MILLISECONDS.sleep(100);
                time++;
                gameFrame.setTime(time);
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
