package model.cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cell extends JLabel {

    int size = 32;
    int id = 0;

    int xPos = 0;
    int yPos = 0;

    public Cell() {
        try {
            BufferedImage ogTexture = ImageIO.read(new File("res/cells/empty.png"));
            Image resized = ogTexture.getScaledInstance(32, 32, BufferedImage.TYPE_INT_ARGB);
            ImageIcon imageIcon = new ImageIcon(resized);
            this.setIcon(imageIcon);
            setPreferredSize(new Dimension(size, size));
            setOpaque(true);
        } catch (IOException e) {
            System.err.println("Could not load cell sprite.");
        }
    }

    public Cell(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getId() {
        return id;
    }

}
