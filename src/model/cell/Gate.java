package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Gate extends Cell {

    public Gate() {
        try {
            texture = ImageIO.read(new File("res/cells/gate.png"));
        } catch (IOException e) {
            System.err.println("Could not load gate sprite");
        }
    }

}
