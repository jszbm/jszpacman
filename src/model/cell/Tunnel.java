package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Tunnel extends Cell {

    public Tunnel() {
        try {
            texture = ImageIO.read(new File("res/cells/empty.png"));
        } catch (IOException e) {
            System.err.println("Could not load tunnel sprite");
        }
    }

}
