package entity;

import java.util.concurrent.TimeUnit;

public class PacThread extends Thread {
    int id = 0;
    int counter = 1;

    public PacThread() {

    }

    @Override
    public void run() {
        while (true) {
            //System.out.println("Frame " + counter);
            //counter++;
            try {
                TimeUnit.MICROSECONDS.sleep(16666);
            } catch (InterruptedException e) {
                break;
            }

        }
    }

}
