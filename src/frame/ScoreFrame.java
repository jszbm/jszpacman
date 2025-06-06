package frame;

import controller.ScoreMapController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ScoreFrame extends CustomFrame implements ActionListener, KeyListener {
    static ScoreMapController scoreMapController = new ScoreMapController();

    JButton closeButton = new JButton();

    public ScoreFrame() {
        setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        addButtons();
        addScorePane();

        pack();

        setVisible(true);
        setSize(width, height);
        setResizable(true);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        addKeyListener(this);
        requestFocus();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeContent();
            }
        });
    }

    public void addScorePane() {
        DefaultListModel<String> scoreListModel = new DefaultListModel<>();
        for (var scoreEntry : scoreMapController.getScoreMap().getMap().entrySet()) {
            String formattedScore = scoreEntry.getValue() + "    " + scoreEntry.getKey();
            scoreListModel.add(0, formattedScore);
        }

        JList<String> scoreList = new JList<>(scoreListModel);
        scoreList.setFont(defaultFont);
        scoreList.setBackground(Color.BLACK);
        scoreList.setFixedCellHeight(40);
        scoreList.setFocusable(false);
        scoreList.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setOpaque(true);
        scrollPane.setBorder(buttonBorder);
        scrollPane.setVerticalScrollBar(new JScrollBar());
        scrollPane.setPreferredSize(new Dimension(width / 2, height / 2));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(scrollPane, gbc);
    }

    private void addButtons() {
        closeButton.setPreferredSize(new Dimension(width / 3, 100));
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(this);
        //closeButton.setText("出る");
        closeButton.setText("CLOSE");
        closeButton.setFont(buttonFont);
        closeButton.setBorder(buttonBorder);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        add(closeButton, gbc);
    }

    void resizeContent() {
        int width = getWidth();
        int height = getHeight();
        int fontSize = Math.min(width, height) / factor * 2;
        Font newFont = defaultFont.deriveFont((float) fontSize);
        closeButton.setFont(newFont);
    }

    @Override
    public void processQuitShortcut() {
        if (isCtrlPressed && isShiftPressed && isQPressed) {
            dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            dispose();
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
            dispose();
        }

        if (e.getKeyCode() == KeyEvent.VK_BACK_QUOTE) {
            String name = JOptionPane.showInputDialog("Name: ");
            int score = Integer.parseInt(JOptionPane.showInputDialog("Score: "));
            System.err.println("Debug score");
            scoreMapController.debugScore(name, score);
        }
    }

}
