package model.cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cell extends JLabel {

    protected BufferedImage texture;

    public Cell() {
        try {
            texture = ImageIO.read(new File("res/cells/empty.png"));
            setOpaque(true);
        } catch (IOException e) {
            System.err.println("Could not load cell sprite");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        g.drawImage(texture, 0, 0, getWidth(), getHeight(), this);
    }

}

/*Image resized = ogTexture.getScaledInstance(32, 32, BufferedImage.TYPE_INT_ARGB);
            ImageIcon imageIcon = new ImageIcon(resized);
            this.setIcon(imageIcon);
            //setPreferredSize(new Dimension(size, size));*/