package model.cell;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cell {

    protected BufferedImage texture;

    public Cell() {
        try {
            texture = ImageIO.read(new File("res/cells/empty.png"));
        } catch (IOException e) {
            System.err.println("Could not load cell sprite");
        }
    }

    public BufferedImage getTexture() {
        return texture;
    }
}