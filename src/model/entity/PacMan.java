package model.entity;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PacMan extends Entity implements KeyListener {

    int lives;

    BufferedImage textureIdle;

    BufferedImage[] textureRight = new BufferedImage[2];
    BufferedImage[] textureLeft = new BufferedImage[2];
    BufferedImage[] textureUp = new BufferedImage[2];
    BufferedImage[] textureDown = new BufferedImage[2];

    public PacMan() {
        try {
            textureIdle = ImageIO.read(new File("res/pacman/idle.png"));

            textureRight[0] = ImageIO.read(new File("res/pacman/r-open.png"));
            textureRight[1] = ImageIO.read(new File("res/pacman/r-normal.png"));

            textureUp[0] = ImageIO.read(new File("res/pacman/u-open.png"));
            textureUp[1] = ImageIO.read(new File("res/pacman/u-normal.png"));

            textureLeft[0] = ImageIO.read(new File("res/pacman/l-open.png"));
            textureLeft[1] = ImageIO.read(new File("res/pacman/l-normal.png"));

            textureDown[0] = ImageIO.read(new File("res/pacman/d-open.png"));
            textureDown[1] = ImageIO.read(new File("res/pacman/d-normal.png"));

        } catch (IOException e) {
            System.err.println("Could not load PacMan sprite");
        }

        direction = Direction.IDLE;
        texture = textureIdle;
        this.lives = 1;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> direction = Direction.UP;


            case KeyEvent.VK_DOWN -> direction = Direction.DOWN;


            case KeyEvent.VK_LEFT -> direction = Direction.LEFT;


            case KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void nextTexture() {
        switch (direction) {
            case UP -> texture = (texture == textureUp[0]) ? textureUp[1] : textureUp[0];
            case LEFT -> texture = (texture == textureLeft[0]) ? textureLeft[1] : textureLeft[0];
            case DOWN -> texture = (texture == textureDown[0]) ? textureDown[1] : textureDown[0];
            case RIGHT -> texture = (texture == textureRight[0]) ? textureRight[1] : textureRight[0];
            case IDLE -> texture = textureIdle;
        }
    }

    public int getLives() {
        return lives;
    }

    public void subtractLives() {
        lives--;
    }
}
