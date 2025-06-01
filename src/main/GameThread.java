package main;

import entity.PacThread;

import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

public class GameThread extends Thread {
    int id = 0;

    PacThread pacThread = new PacThread();
    MovementHandler movementHandler = new MovementHandler();

    public GameThread() {

    }

    public KeyListener getMovementHandler() {
        return movementHandler;
    }

    @Override
    public void run() {
        System.out.println("gameThread " + currentThread().getName() + " is running.");

        pacThread.start();
        System.out.println("pacThread " + pacThread.getName() + " is running.");


        while (true) {
            try {
                TimeUnit.MICROSECONDS.sleep(16666);
            } catch (InterruptedException e) {
                pacThread.interrupt();
                System.out.println("pacThread " + pacThread.getName() + " is interrupted.");
                System.out.println("gameThread " + currentThread().getName() + " is interrupted.");
                break;
            }
        }

    }
}
