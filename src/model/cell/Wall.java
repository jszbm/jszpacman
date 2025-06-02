package model.cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Wall extends Cell {

    int id = 1;

    public Wall() throws IOException {
        try {
            BufferedImage ogTexture = ImageIO.read(new File("res/cells/wall.png"));
            Image resized = ogTexture.getScaledInstance(32, 32, BufferedImage.TYPE_INT_ARGB);
            ImageIcon imageIcon = new ImageIcon(resized);
            this.setIcon(imageIcon);
            setPreferredSize(new Dimension(size, size));
            setOpaque(true);
        } catch (IOException e) {
            System.err.println("Could not load wall sprite.");
        }
    }

}
