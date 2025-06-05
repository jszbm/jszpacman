package thread;

import frame.GameFrame;

import java.util.concurrent.TimeUnit;

public class GameThread extends Thread {

    GameFrame gameFrame;

    TimeThread timeThread;
    GameLogicThread gameLogicThread;
    AnimationThread animationThread;
    RedGhostThread redGhostThread;

    public GameThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        gameLogicThread = new GameLogicThread(gameFrame);
        animationThread = new AnimationThread(gameFrame);
        redGhostThread = new RedGhostThread(gameFrame.getRedGhost(), gameFrame.getMazeTable());
        timeThread = new TimeThread();
    }

    @Override
    public void run() {
        System.out.println("Game thread " + currentThread().getName() + " running");
        timeThread.start();
        gameLogicThread.start();
        redGhostThread.start();
        animationThread.start();

        try {
            while (isAlive()) {
                TimeUnit.MICROSECONDS.sleep(33333);
            }
        } catch (InterruptedException e) {
            timeThread.interrupt();
            gameLogicThread.interrupt();
            redGhostThread.interrupt();
            animationThread.interrupt();
        }


    }

    public TimeThread getTimeThread() {
        return timeThread;
    }

}
