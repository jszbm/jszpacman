package model.cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tunnel extends Cell {

    int id = 2;

    public Tunnel() throws IOException {
        try {
            BufferedImage ogTexture = ImageIO.read(new File("res/cells/empty.png"));
            Image resized = ogTexture.getScaledInstance(32, 32, BufferedImage.TYPE_INT_ARGB);
            ImageIcon imageIcon = new ImageIcon(resized);
            this.setIcon(imageIcon);
            setPreferredSize(new Dimension(size, size));
            setOpaque(true);
        } catch (IOException e) {
            System.err.println("Could not load tunnel sprite.");
        }
    }

}
