package cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tunnel extends Cell {
    int id = 4;

    public Tunnel() throws IOException {
        BufferedImage ogTexture = ImageIO.read(new File("res/tiles/empty.png"));
        Image resized = ogTexture.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(resized);
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(size, size));
    }

}
