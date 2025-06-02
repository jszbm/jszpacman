package frame;

import controller.ScoreMapController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ScoreFrame extends CustomFrame implements ActionListener, KeyListener {
    //Score system objects
    static ScoreMapController scoreMapController = new ScoreMapController();
    //Visual objects
    JPanel scoreMenuPanel = new JPanel(new GridBagLayout());
    //Interactive objects
    JButton closeButton = new JButton();

    public ScoreFrame() {
        this.addKeyListener(this);
        setLayout(new BorderLayout());
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        addButtons();
        addScorePane();
        addScoreMenuPanel();

        //Draw
        pack();

        //Visual
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
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.weightx = 1;
        layoutConstraints.weighty = 1;
        scoreMenuPanel.add(scrollPane, layoutConstraints);
    }

    private void addButtons() {
        closeButton.setPreferredSize(new Dimension(width / 3, 100));
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(this);
        closeButton.setText("出る");
        closeButton.setFont(buttonFont);
        closeButton.setBorder(buttonBorder);
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.weightx = 0.1;
        layoutConstraints.weighty = 0.1;
        scoreMenuPanel.add(closeButton, layoutConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            this.dispose();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }

        if (ke.getKeyCode() == KeyEvent.VK_BACK_QUOTE) {
            String name = JOptionPane.showInputDialog("Name: ");
            int score = Integer.parseInt(JOptionPane.showInputDialog("Score: "));
            System.out.println("Debug score");
            scoreMapController.debugScore(name, score);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
