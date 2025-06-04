package model.entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RedGhost extends Entity {

    BufferedImage textureIdle;

    public RedGhost() {
        try {
            textureIdle = ImageIO.read(new File("res/ghosts/red-idle.png"));
        } catch (IOException e) {
            System.err.println("Could not load red ghost sprite");
        }

        direction = Direction.RIGHT;
        texture = textureIdle;
    }

}
