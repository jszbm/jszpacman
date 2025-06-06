package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Apple extends Cell {

    public Apple() {
        try {
            texture = ImageIO.read(new File("res/fruits/apple.png"));
        } catch (IOException e) {
            System.err.println("Could not load Apple sprite");
        }

    }

}
