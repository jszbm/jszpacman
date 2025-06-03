package frame;

import thread.ShortcutThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuFrame extends CustomFrame implements ActionListener {
    //Visual objects
    JPanel menuPanel = new JPanel(new GridBagLayout());
    JLabel menuLogoLabel = new JLabel();
    ImageIcon menuLogo = new ImageIcon("res/gui/pacman-logo.png");

    //Interactive objects
    JButton playButton = new JButton();
    JButton exitButton = new JButton();
    JButton scoresButton = new JButton();

    public MainMenuFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        setLayout(new BorderLayout());

        addMenuLogoLabel();
        addButtons();
        addMenuPanel();

        pack();

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        getContentPane().setBackground(Color.BLACK);
        add(menuPanel, BorderLayout.CENTER);
    }


    private void addMenuPanel() {
        menuPanel.setOpaque(true);
        menuPanel.setBackground(Color.BLACK);
    }

    private void addMenuLogoLabel() {
        Image gameLogoScaled = menuLogo.getImage().getScaledInstance(
                (int) (menuLogo.getIconWidth() * 0.5),
                (int) (menuLogo.getIconHeight() * 0.5),
                Image.SCALE_FAST
        );

        menuLogoLabel.setIcon(new ImageIcon(gameLogoScaled));
        menuLogoLabel.setVerticalAlignment(JLabel.CENTER);
        menuLogoLabel.setHorizontalAlignment(JLabel.CENTER);
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.weightx = 0.5;
        layoutConstraints.weighty = 0.5;
        menuPanel.add(menuLogoLabel, layoutConstraints);
    }

    private void addButtons() {
        layoutConstraints.gridx = 0;
        layoutConstraints.weightx = 0.2;
        layoutConstraints.weighty = 0.2;
        layoutConstraints.fill = GridBagConstraints.BOTH;

        //"PLAY"
        playButton.setPreferredSize(new Dimension(width / 3, 100));
        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.WHITE);
        playButton.addActionListener(this);
        playButton.setText("ニューゲーム");
        playButton.setFocusable(false);
        playButton.setFont(menuFont);
        playButton.setBorder(buttonBorder);
        playButton.setMargin(new Insets(10, 20, 10, 20));

        layoutConstraints.gridy = 1;
        menuPanel.add(playButton, layoutConstraints);


        //"SCORES"
        scoresButton.setPreferredSize(new Dimension(width / 3, 100));
        scoresButton.setBackground(Color.BLACK);
        scoresButton.setForeground(Color.WHITE);
        scoresButton.addActionListener(this);
        scoresButton.setText("スコア");
        scoresButton.setFocusable(false);
        scoresButton.setFont(menuFont);
        scoresButton.setBorder(buttonBorder);
        scoresButton.setMargin(new Insets(10, 20, 10, 20));

        layoutConstraints.gridy = 2;
        menuPanel.add(scoresButton, layoutConstraints);

        //"EXIT"
        exitButton.setPreferredSize(new Dimension(width / 3, 100));
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);
        exitButton.setText("出る");
        exitButton.setFocusable(false);
        exitButton.setFont(menuFont);
        exitButton.setBorder(buttonBorder);
        exitButton.setMargin(new Insets(10, 20, 10, 20));

        layoutConstraints.gridy = 3;
        menuPanel.add(exitButton, layoutConstraints);

    }

    @Override
    public void processQuitShortcut() {
        if (isCtrlPressed && isShiftPressed && isQPressed){
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            isQPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            isCtrlPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            new GameFrame();
        }

        if (e.getSource() == scoresButton) {
            new ScoreFrame();
        }

        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

}
