package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Gate extends Cell{

    public Gate() {
        try {
            texture = ImageIO.read(new File("res/cells/gate.png"));
            setOpaque(true);
        } catch (IOException e) {
            System.err.println("Could not load gate sprite.");
        }
    }

}
