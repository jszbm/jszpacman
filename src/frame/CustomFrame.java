package frame;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class CustomFrame extends JFrame {
    ImageIcon gameIcon = new ImageIcon("res/gui/icon.png");
    GridBagConstraints layoutConstraints = new GridBagConstraints();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int width = 1024;
    int height = 768;
    int factor = 32;

    MatteBorder buttonBorder = BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE);
    Font scoreListFont = new Font("Ark Pixel 10px Monospaced latin Regular", Font.PLAIN, 32);
    Font menuFont = new Font("Ark Pixel 10px Monospaced ja Regular", Font.PLAIN, 50);
    Font buttonFont = new Font("Ark Pixel 10px Monospaced ja Regular", Font.PLAIN, 50);

    public CustomFrame() {
        setTitle("Pac-Man");
        setIconImage(gameIcon.getImage());
        layoutConstraints.insets = new Insets(height / factor, width / factor, height / factor, width / factor);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
