package thread;

import java.util.concurrent.TimeUnit;

public class TimeThread extends Thread {

    int time;

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                time++;
            } catch (InterruptedException e) {
                interrupt();
                return;
            }
        }
    }

    public String getStringTime() {
        return String.format("%02d:%02d", time / 60, time);
    }
}
