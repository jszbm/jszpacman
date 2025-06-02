package thread;

import java.util.concurrent.TimeUnit;

public class TimeThread extends Thread{

    int time = 0;

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                time++;
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public int getTime() {
        return time;
    }

    public String getStringTime() {
        return String.format("%d:%02d.%02d", time/60000, time%60000/1000, time%1000);
    }
}
