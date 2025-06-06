package model.entity;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OrangeGhost extends Entity{

    public OrangeGhost() {
        try {

            textureRight[0] = ImageIO.read(new File("res/ghosts/orange/r-1.png"));
            textureRight[1] = ImageIO.read(new File("res/ghosts/orange/r-2.png"));

            textureUp[0] = ImageIO.read(new File("res/ghosts/orange/u-1.png"));
            textureUp[1] = ImageIO.read(new File("res/ghosts/orange/u-2.png"));

            textureLeft[0] = ImageIO.read(new File("res/ghosts/orange/l-2.png"));
            textureLeft[1] = ImageIO.read(new File("res/ghosts/orange/l-1.png"));

            textureDown[0] = ImageIO.read(new File("res/ghosts/orange/d-1.png"));
            textureDown[1] = ImageIO.read(new File("res/ghosts/orange/d-2.png"));

            textureIdle[0] = ImageIO.read(new File("res/ghosts/i-1.png"));
            textureIdle[1] = ImageIO.read(new File("res/ghosts/i-2.png"));

            textureFreeze[0] = ImageIO.read(new File("res/ghosts/f-1.png"));
            textureFreeze[1] = ImageIO.read(new File("res/ghosts/f-2.png"));

        } catch (IOException e) {
            System.err.println("Could not load orange ghost sprite");
        }

        direction = Direction.UP;
        texture = textureRight[0];
    }

    @Override
    public void nextTexture() {
        switch (direction) {
            case UP -> texture = (texture == textureUp[0]) ? textureUp[1] : textureUp[0];
            case LEFT -> texture = (texture == textureLeft[0]) ? textureLeft[1] : textureLeft[0];
            case DOWN -> texture = (texture == textureDown[0]) ? textureDown[1] : textureDown[0];
            case RIGHT -> texture = (texture == textureRight[0]) ? textureRight[1] : textureRight[0];
            case IDLE -> texture = (texture == textureIdle[0]) ? textureIdle[1] : textureIdle[0];
        }
    }

}
