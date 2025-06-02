package model.cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PowerDot extends Cell {

    int id = 4;

    public PowerDot() {
        try {
            BufferedImage ogTexture = ImageIO.read(new File("res/cells/power-dot.png"));
            Image resized = ogTexture.getScaledInstance(32, 32, BufferedImage.TYPE_INT_ARGB);
            ImageIcon imageIcon = new ImageIcon(resized);
            this.setIcon(imageIcon);
            setPreferredSize(new Dimension(size, size));
            setOpaque(true);
        } catch (IOException e) {
            System.err.println("Could not load power pellet sprite.");
        }
    }
}
