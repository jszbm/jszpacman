package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainMenuFrame extends CustomFrame implements ActionListener, KeyListener {

    //Visual objects
    JPanel menuPanel = new JPanel(new GridBagLayout()); //Panel containing the label with Pac-Man logo
    JLabel menuLogoLabel = new JLabel();
    ImageIcon menuLogo = new ImageIcon("res/gui/pacman-logo.png");

    //Interactive objects
    JButton playButton = new JButton();
    JButton exitButton = new JButton();
    JButton scoresButton = new JButton();

    //Threads

    public MainMenuFrame() {
        setTitle("Pac-Man");
        setIconImage(gameIcon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        layoutConstraints.insets = new Insets(40, 40, 40, 40);
        createMenuLogoLabel();

        addButtons();

        addMenuPanel();

        pack();

        setSize(960, 720);
        setLocationRelativeTo(null); //Centering the frame
        setResizable(true);
        setLayout(new BorderLayout());
        setVisible(true);
        getContentPane().setBackground(Color.BLACK);
        add(menuPanel, BorderLayout.CENTER);
    }


    private void addMenuPanel() {
        menuPanel.setOpaque(true);
        menuPanel.setBackground(Color.BLACK);
    }

    private void createMenuLogoLabel() {
        Image gameLogoScaled = menuLogo.getImage().getScaledInstance(
                menuLogo.getIconWidth() / 2,
                menuLogo.getIconHeight() / 2,
                Image.SCALE_SMOOTH
        );

        menuLogo = new ImageIcon(gameLogoScaled);
        menuLogoLabel.setIcon(menuLogo);
        menuLogoLabel.setVerticalAlignment(JLabel.CENTER);
        menuLogoLabel.setHorizontalAlignment(JLabel.CENTER);
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        menuPanel.add(menuLogoLabel, layoutConstraints);
    }

    private void addButtons() {
        //"PLAY" button settings
        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.WHITE);
        playButton.addActionListener(this);
        playButton.setText("NEW GAME");
        playButton.setFocusable(false);
        playButton.setFont(new Font("VT323", Font.BOLD, 50));
        playButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        layoutConstraints.gridy = 1;
        menuPanel.add(playButton, layoutConstraints);


        //"SCORES" button settings
        scoresButton.setBackground(Color.BLACK);
        scoresButton.setForeground(Color.WHITE);
        scoresButton.addActionListener(this);
        scoresButton.setText("SCORES");
        scoresButton.setFocusable(false);
        scoresButton.setFont(new Font("VT323", Font.BOLD, 50));
        scoresButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        layoutConstraints.gridy = 2;
        menuPanel.add(scoresButton, layoutConstraints);

        //"EXIT" button settings
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);
        exitButton.setText("EXIT");
        exitButton.setFocusable(false);
        exitButton.setFont(new Font("VT323", Font.BOLD, 50));
        exitButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        layoutConstraints.gridy = 3;
        menuPanel.add(exitButton, layoutConstraints);

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Exit on "ESC"
        if (e.getKeyCode() == 27) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println(e.getKeyChar() + " " + e.getKeyCode());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Open GameFrame
        if (e.getSource() == playButton) {
            System.out.println(playButton.getText());
            new GameFrame();
        }

        if (e.getSource() == scoresButton) {
            System.out.println(scoresButton.getText());
            new ScoresFrame();
        }

        if (e.getSource() == exitButton) {
            System.out.println(exitButton.getText());
            //System.out.println("\\033[H\\033[2J");
            //System.out.flush();
            System.exit(0);
        }
    }

}
