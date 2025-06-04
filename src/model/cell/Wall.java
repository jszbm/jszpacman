package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Wall extends Cell {

    public Wall() {
        try {
            texture = ImageIO.read(new File("res/cells/wall.png"));
        } catch (IOException e) {
            System.err.println("Could not load wall sprite");
        }
    }

}
