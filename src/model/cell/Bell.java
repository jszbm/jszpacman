package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bell extends Cell {

    public Bell() {
        try {
            texture = ImageIO.read(new File("res/fruits/bell.png"));
        } catch (IOException e) {
            System.err.println("Could not load Bell sprite");
        }

    }

}
