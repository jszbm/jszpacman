package entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PacMan extends Entity {

    public PacMan() {
        try {
            BufferedImage sprite = ImageIO.read(new File("res/pacman/pacman-idle-u.png"));
            ImageIcon pacmanIcon = new ImageIcon(sprite.getScaledInstance(32, 32, Image.SCALE_DEFAULT));
            this.setIcon(pacmanIcon);
            this.setPreferredSize(new Dimension(32, 32));
        } catch (IOException e) {
            System.err.println("Could not load PacMan sprite.");
        }
    }

}
