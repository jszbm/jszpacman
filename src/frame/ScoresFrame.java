package frame;

import main.Main;
import score.ScoreMap;
import score.ScoreMapController;
import score.ScoreMapService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

import static score.ScoreMapController.scoreMapService;


public class ScoresFrame extends CustomFrame implements ActionListener, KeyListener {
    //Visual objects
    JPanel scoreMenuPanel = new JPanel(new GridBagLayout());

    //Interactive objects
    JButton closeButton = new JButton(); //"CLOSE" button

    //Score system objects
    ScoreMapController scoreMapController = new ScoreMapController();
    ScoreMap scoreMap = new ScoreMap();


    ScoresFrame() {
        this.addKeyListener(this);
        //Header settings
        setTitle("Pac-Man");
        setIconImage(gameIcon.getImage());

        //Input-related settings
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Content settings
        layoutConstraints.insets = new Insets(40, 40, 40, 40);

        addButtons();
        addScorePane();
        createScoreMenuPanel();

        //Draw settings
        pack();

        //Visual settings
        setVisible(true);
        setSize(800,600);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        requestFocus();
        add(scoreMenuPanel);
    }

    private void createScoreMenuPanel() {
        scoreMenuPanel.setOpaque(true);
        scoreMenuPanel.setBackground(Color.BLACK);
    }

    private void addButtons() {
        //"CLOSE" button settings
        closeButton.setBounds((this.getWidth() - 200) / 2 - 10, (this.getHeight() + 100) / 2 - 10, 200, 50);
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(this);
        closeButton.setText(" CLOSE ");
        closeButton.setFocusable(false);
        closeButton.setFont(new Font("VT323", Font.BOLD, 50));
        closeButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        layoutConstraints.gridy = 1;
        scoreMenuPanel.add(closeButton, layoutConstraints);
    }

    public void addScorePane() {
        DefaultListModel<String> scoreListModel = new DefaultListModel<>();
        //List<> = new ArrayList<>(scoreMap.values());
        int iter = 0;
        for (HashMap.Entry<String, Integer> scoreEntry : Main.scoreMap.entrySet()) {
            String formattedScore = scoreEntry.getKey() + ": " + scoreEntry.getValue();
            scoreListModel.add(0, formattedScore);
            iter++;
        }

        JList<String> scoreList = new JList<>(scoreListModel);
        JScrollPane scrollPane = new JScrollPane(scoreList);

        scrollPane.setOpaque(true);
        scrollPane.setForeground(Color.GRAY);
        scrollPane.setFont(new Font("VT323", Font.BOLD, 50));

        scoreMenuPanel.add(scrollPane, layoutConstraints);
    }

    @Override
    public void  actionPerformed(ActionEvent e) {
        if(e.getSource() == closeButton) {
            this.dispose();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }

        //Debug operation, press Back quote to add a new custom score.
        if (ke.getKeyCode() == KeyEvent.VK_BACK_QUOTE) {
            System.out.println("debug");
            try {
                scoreMapService.debugScore();
                scoreMapService.printMap();

            } catch (IOException e) {

            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
