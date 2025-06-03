package model.cell;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PowerDot extends Dot {

    public PowerDot() {
        try {
            texture = ImageIO.read(new File("res/cells/power-dot.png"));
            setOpaque(true);
        } catch (IOException e) {
            System.err.println("Could not load power pellet sprite.");
        }
    }
}
