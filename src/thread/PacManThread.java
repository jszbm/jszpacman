package thread;

import java.util.concurrent.TimeUnit;

public class PacManThread extends Thread {
    int id = 0;
    int counter = 1;

    public PacManThread() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MICROSECONDS.sleep(16666);
            } catch (InterruptedException e) {
                break;
            }

        }
    }

}
