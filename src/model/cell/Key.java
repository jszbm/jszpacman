package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Key extends Cell {

    public Key() {
        try {
            texture = ImageIO.read(new File("res/fruits/key.png"));
        } catch (IOException e) {
            System.err.println("Could not load Key sprite");
        }

    }

}
