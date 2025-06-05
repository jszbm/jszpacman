package model.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RedGhost extends Entity {

    BufferedImage[] textureRight = new BufferedImage[2];
    BufferedImage[] textureLeft = new BufferedImage[2];
    BufferedImage[] textureUp = new BufferedImage[2];
    BufferedImage[] textureDown = new BufferedImage[2];

    public RedGhost() {
        try {

            textureRight[0] = ImageIO.read(new File("res/ghosts/red/r-1.png"));
            textureRight[1] = ImageIO.read(new File("res/ghosts/red/r-2.png"));

            textureUp[0] = ImageIO.read(new File("res/ghosts/red/u-1.png"));
            textureUp[1] = ImageIO.read(new File("res/ghosts/red/u-2.png"));

            textureLeft[0] = ImageIO.read(new File("res/ghosts/red/l-2.png"));
            textureLeft[1] = ImageIO.read(new File("res/ghosts/red/l-1.png"));

            textureDown[0] = ImageIO.read(new File("res/ghosts/red/d-1.png"));
            textureDown[1] = ImageIO.read(new File("res/ghosts/red/d-2.png"));

        } catch (IOException e) {
            System.err.println("Could not load red ghost sprite");
        }

        direction = Direction.RIGHT;
        texture = textureRight[0];
    }

    public void nextTexture() {
        switch (direction) {
            case UP -> texture = (texture == textureUp[0]) ? textureUp[1] : textureUp[0];
            case LEFT -> texture = (texture == textureLeft[0]) ? textureLeft[1] : textureLeft[0];
            case DOWN -> texture = (texture == textureDown[0]) ? textureDown[1] : textureDown[0];
            case RIGHT -> texture = (texture == textureRight[0]) ? textureRight[1] : textureRight[0];
        }
    }

}
