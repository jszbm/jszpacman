package cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Wall extends Cell {

    int id = 1;

    public Wall() throws IOException {
        BufferedImage ogTexture = ImageIO.read(new File("res/tiles/wall.png"));
        Image resized = ogTexture.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(resized);
        this.setIcon(imageIcon);
        this.setPreferredSize(new Dimension(size, size));

    }


    //new Color(0x1818ff) || wall cell color
}
