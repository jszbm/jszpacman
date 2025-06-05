package frame;

import thread.ShortcutThread;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class CustomFrame extends JFrame implements KeyListener {
    ImageIcon gameIcon = new ImageIcon("res/gui/icon.png");
    GridBagConstraints gbc = new GridBagConstraints();

    ShortcutThread shortcutThread;

    boolean isCtrlPressed;
    boolean isShiftPressed;
    boolean isQPressed;

    int width = 1024;
    int height = 768;
    int factor = 32;

    MatteBorder buttonBorder = BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE);
    Font defaultFont = new Font("Ark Pixel 10px Monospaced latin Regular", Font.PLAIN, 32);
    Font menuFont = new Font("Ark Pixel 10px Monospaced ja Regular", Font.PLAIN, 50);
    Font buttonFont = new Font("Ark Pixel 10px Monospaced ja Regular", Font.PLAIN, 50);

    public CustomFrame() {
        setTitle("Pac-Man");
        setIconImage(gameIcon.getImage());
        gbc.insets = new Insets(width / factor, width / factor, width / factor, width / factor);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        shortcutThread = new ShortcutThread(this);
        shortcutThread.start();
    }

    public abstract void processQuitShortcut();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            isQPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            isCtrlPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftPressed = false;
        }
    }
}
