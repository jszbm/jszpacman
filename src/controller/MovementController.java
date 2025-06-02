package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Class for handling player movement
public class MovementController implements KeyListener {
    //Up - 224
    //Down - 225
    //Left - 226
    //Right - 227

    public MovementController() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                System.out.println("up");
            }

            case KeyEvent.VK_DOWN -> {
                System.out.println("down");
            }

            case KeyEvent.VK_LEFT -> {
                System.out.println("left");
            }

            case KeyEvent.VK_RIGHT -> {
                System.out.println("right");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
