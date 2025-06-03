package model.entity;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PacMan extends Entity implements KeyListener {

    private static PacMan pacMan = null;

    int lives;

    BufferedImage textureIdle;
    BufferedImage textureRightOpen;
    BufferedImage textureRightClosed;
    BufferedImage textureUpOpen;
    BufferedImage textureUpClosed;
    BufferedImage textureLeftOpen;
    BufferedImage textureLeftClosed;
    BufferedImage textureDownOpen;
    BufferedImage TextureDownClosed;

    public PacMan() {
        try {
            textureIdle = ImageIO.read(new File("res/pacman/idle.png"));
            textureRightOpen = ImageIO.read(new File("res/pacman/r-open.png"));
            textureUpOpen = ImageIO.read(new File("res/pacman/u-open.png"));
            textureLeftOpen = ImageIO.read(new File("res/pacman/l-open.png"));
            textureDownOpen = ImageIO.read(new File("res/pacman/d-open.png"));
        } catch (IOException e) {
            System.err.println("Could not load PacMan sprite.");
        }

        texture = textureIdle;
        this.lives = 3;
    }

    public static PacMan getInstance() {
        if (pacMan == null){
            pacMan = new PacMan();
        }
        return pacMan;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                direction = Direction.UP;
                texture = textureUpOpen;

            }

            case KeyEvent.VK_DOWN -> {
                direction = Direction.DOWN;
                texture = textureDownOpen;
            }

            case KeyEvent.VK_LEFT -> {
                direction = Direction.LEFT;
                texture = textureLeftOpen;
            }

            case KeyEvent.VK_RIGHT -> {
                direction = Direction.RIGHT;
                texture = textureRightOpen;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
