package frame;

import controller.ScoreMapController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class ScoreFrame extends CustomFrame implements ActionListener {
    //Score system objects
    static ScoreMapController scoreMapController = new ScoreMapController();
    //Visual objects
    JPanel scoreMenuPanel = new JPanel(new GridBagLayout());
    //Interactive objects
    JButton closeButton = new JButton();

    public ScoreFrame() {
        this.addKeyListener(this);
        setLayout(new BorderLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        addButtons();
        addScorePane();
        addScoreMenuPanel();

        pack();

        setVisible(true);
        setSize(width, height);
        setResizable(true);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        requestFocus();
    }

    private void addScoreMenuPanel() {
        scoreMenuPanel.setOpaque(true);
        scoreMenuPanel.setBackground(Color.BLACK);
        add(scoreMenuPanel);
    }

    public void addScorePane() {
        DefaultListModel<String> scoreListModel = new DefaultListModel<>();
        for (var scoreEntry : scoreMapController.getScoreMap().getMap().entrySet()) {
            String formattedScore = scoreEntry.getKey() + ": " + scoreEntry.getValue();
            scoreListModel.add(0, formattedScore);
        }

        JList<String> scoreList = new JList<>(scoreListModel);
        scoreList.setFont(scoreListFont);
        scoreList.setBackground(Color.BLACK);
        scoreList.setForeground(Color.WHITE);
        scoreList.setFixedCellHeight(40);

        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setOpaque(true);
        scrollPane.setBorder(buttonBorder);
        scrollPane.setVerticalScrollBar(new JScrollBar());
        scrollPane.setPreferredSize(new Dimension(width / 2, height / 2));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        scoreMenuPanel.add(scrollPane, gbc);
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
        scoreMenuPanel.add(closeButton, gbc);
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
            System.out.println("Debug score");
            scoreMapController.debugScore(name, score);
        }
    }

}
