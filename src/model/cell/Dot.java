package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Dot extends Cell {

    public Dot() {
        try {
            texture = ImageIO.read(new File("res/cells/dot.png"));
            setOpaque(true);
        } catch (IOException e) {
            System.err.println("Could not load dot sprite.");
        }
    }

}
