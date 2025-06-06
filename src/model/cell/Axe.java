package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Axe extends Cell {

    public Axe() {
        try {
            texture = ImageIO.read(new File("res/fruits/axe.png"));
        } catch (IOException e) {
            System.err.println("Could not load Axe sprite");
        }

    }

}
