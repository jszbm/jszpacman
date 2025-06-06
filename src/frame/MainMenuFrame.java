package frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuFrame extends CustomFrame implements ActionListener {
    JLabel menuLogoLabel = new JLabel();
    BufferedImage menuLogo;
    int logoWidth;
    int logoHeight;

    JButton playButton = new JButton();
    JButton exitButton = new JButton();
    JButton scoresButton = new JButton();

    public MainMenuFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        setLayout(new GridBagLayout());

        addMenuLogoLabel();
        addButtons();

        pack();

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        getContentPane().setBackground(Color.BLACK);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeContent();
            }
        });
    }

    private void addMenuLogoLabel() {
        try {
            menuLogo = ImageIO.read(new File("res/gui/logo.png"));
        } catch (IOException e) {
            System.err.println("Could not read the logo image");
        }
        logoWidth = menuLogo.getWidth();
        logoHeight = menuLogo.getHeight();
        menuLogoLabel.setIcon(new ImageIcon(menuLogo.getScaledInstance(logoWidth / 2, logoHeight / 2, Image.SCALE_FAST)));
        menuLogoLabel.setVerticalAlignment(JLabel.CENTER);
        menuLogoLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        add(menuLogoLabel, gbc);
    }

    private void addButtons() {
        gbc.gridx = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;

        //"PLAY"
        playButton.setPreferredSize(new Dimension(width / 3, 100));
        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.WHITE);
        playButton.addActionListener(this);
        //playButton.setText("ニューゲーム");
        playButton.setText("NEW GAME");
        playButton.setFocusable(false);
        playButton.setFont(menuFont);
        playButton.setBorder(buttonBorder);
        playButton.setMargin(new Insets(20, 20, 20, 20));
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    new GameFrame(21, 21, true);
                }
            }
        });

        gbc.gridy = 1;
        add(playButton, gbc);

        //"SCORES"
        scoresButton.setPreferredSize(new Dimension(width / 3, 100));
        scoresButton.setBackground(Color.BLACK);
        scoresButton.setForeground(Color.WHITE);
        scoresButton.addActionListener(this);
        //scoresButton.setText("スコア");
        scoresButton.setText("SCORES");
        scoresButton.setFocusable(false);
        scoresButton.setFont(menuFont);
        scoresButton.setBorder(buttonBorder);
        scoresButton.setMargin(new Insets(10, 20, 10, 20));

        gbc.gridy = 2;
        add(scoresButton, gbc);

        //"EXIT"
        exitButton.setPreferredSize(new Dimension(width / 3, 100));
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);
        //exitButton.setText("出る");
        exitButton.setText("EXIT");
        exitButton.setFocusable(false);
        exitButton.setFont(menuFont);
        exitButton.setBorder(buttonBorder);
        exitButton.setMargin(new Insets(10, 20, 10, 20));

        gbc.gridy = 3;
        add(exitButton, gbc);

    }

    private void resizeContent() {
        int currentWidth = getWidth();
        int currentHeight = getHeight();
        int fontSize = Math.min(currentWidth, currentHeight) / factor * 2;

        Font newFont = defaultFont.deriveFont((float) fontSize);
        playButton.setFont(newFont);
        scoresButton.setFont(newFont);
        exitButton.setFont(newFont);
    }

    @Override
    public void processQuitShortcut() {
        if (isCtrlPressed && isShiftPressed && isQPressed) {
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
            JTextField rowsField = new JTextField(3);
            JTextField columnsField = new JTextField(3);
            GridBagConstraints promptGbc = new GridBagConstraints();

            JPanel panel = new JPanel(new GridBagLayout());

            promptGbc.gridx = 0;
            promptGbc.gridy = 0;
            promptGbc.gridwidth = 4;
            promptGbc.insets = new Insets(10, 0, 10, 10);
            promptGbc.fill = GridBagConstraints.BOTH;
            panel.add(new JLabel("Enter maze dimensions: "), promptGbc);
            promptGbc.insets = new Insets(0, 0, 10, 10);
            promptGbc.gridy = 1;
            promptGbc.gridwidth = 1;
            panel.add(new JLabel("rows"), promptGbc);
            promptGbc.gridx = 1;
            panel.add(rowsField, promptGbc);
            promptGbc.gridx = 2;
            panel.add(new JLabel("columns"), promptGbc);
            promptGbc.gridx = 3;
            panel.add(columnsField, promptGbc);

            int optionPane = JOptionPane.showConfirmDialog(null,
                    panel,
                    "Maze dimensions",
                    JOptionPane.OK_CANCEL_OPTION);
            if (optionPane == JOptionPane.OK_OPTION) {
                int rows = Integer.parseInt(rowsField.getText());
                int columns = Integer.parseInt(columnsField.getText());

                if (rows < 10 || columns < 10) {
                    JOptionPane.showMessageDialog(null, "The maze can not be smaller than 10 x 10");
                } else if (rows > 100 || columns > 100) {
                    JOptionPane.showMessageDialog(null, "The maze can not be bigger than 100 x 100");
                } else {
                    new GameFrame(rows, columns, false);
                }
            }


        }

        if (e.getSource() == scoresButton) {
            new ScoreFrame();
        }

        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

}
