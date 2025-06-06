package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Orange extends Cell {

    public Orange() {
        try {
            texture = ImageIO.read(new File("res/fruits/orange.png"));
        } catch (IOException e) {
            System.err.println("Could not load Orange sprite");
        }

    }

}
