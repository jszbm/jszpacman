package frame;

import cell.*;
import entity.PacMan;
import main.GameThread;
import score.ScoreMapService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GameFrame extends CustomFrame implements ActionListener, KeyListener {
    //Game objects
    PacMan player = new PacMan();
    ScoreMapService scoreMapService = new ScoreMapService();

    //Visual objects
    int[][] mazeMap = {
            //Empty - 0
            //Wall - 1
            //Tunnel - 2
            //Dot - 3
            //Power pellet - 4

            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1},
            {1, 4, 1, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 3, 3, 1, 3, 3, 1, 4, 1},
            {1, 3, 1, 1, 3, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 3, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 1, 1, 1, 3, 3, 3, 1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 1, 1, 1, 1},
            {0, 0, 0, 1, 3, 1, 3, 0, 0, 0, 0, 0, 0, 0, 3, 1, 3, 1, 0, 0, 0},
            {1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1},
            {2, 0, 0, 0, 3, 3, 1, 0, 1, 0, 0, 0, 1, 0, 1, 3, 3, 0, 0, 0, 2},
            {1, 1, 1, 1, 3, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 3, 1, 1, 1, 1},
            {0, 0, 0, 1, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 1, 0, 0, 0},
            {1, 1, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 3, 1, 3, 1},
            {1, 3, 1, 1, 1, 3, 3, 1, 3, 1, 1, 1, 3, 1, 3, 3, 1, 1, 1, 3, 1},
            {1, 4, 3, 3, 3, 3, 1, 1, 3, 3, 1, 3, 3, 1, 1, 3, 3, 3, 3, 4, 1},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    JPanel mazePanel = new JPanel();

    ImageIcon gameIcon = new ImageIcon("assets/PacManMirrored.png"); //Icon for the window
    JLabel scoreLabel = new JLabel();

    //Functional objects
    int score = 0;

    //Threads
    GameThread gameThread = new GameThread();

    //Frame Constructor
    GameFrame() {

        //Header settings
        setTitle("Pac-Man");
        setIconImage(gameIcon.getImage());

        //Input-related settings
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addKeyListener(gameThread.getMovementHandler()); //Character movement
        addKeyListener(this); //Exit on ESC pressed
        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        try {
                            ScoreMapService.newScore();

                        } catch (IOException ex) {

                        }

                        gameThread.interrupt();
                    }
                }
        );


        //Draw settings
        drawMap();
        this.add(mazePanel, BorderLayout.NORTH);

        //Visual settings
        this.setSize(688, 732);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);

        //Content settings
        scoreLabel.setText("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("VT323", Font.BOLD, 24));
        this.add(scoreLabel, BorderLayout.SOUTH);

        //Threads
        gameThread.start();

        this.scoreMapService = scoreMapService;

    }

    public void drawMap() {
        mazePanel.setLayout(new GridLayout(mazeMap.length, mazeMap[0].length, 0, 0));
        mazePanel.setSize(32 * mazeMap.length, 32 * mazeMap[0].length);
        mazePanel.setBackground(Color.WHITE);


        for (int row = 0; row < mazeMap.length; ) {
            for (int i = 0; i < mazeMap[row].length; ) {
                try {
                    switch (mazeMap[row][i]) {
                        case 0:
                            mazePanel.add(new Cell());
                            break;
                        case 1:
                            mazePanel.add(new Wall());
                            break;
                        case 2:
                            mazePanel.add(new Tunnel());
                            break;
                        case 3:
                            mazePanel.add(new Dot());
                            break;
                        case 4:
                            mazePanel.add(new PowerDot());
                            break;
                        default:
                            mazePanel.add(new Cell());
                    }
                } catch (IOException e) {
                    System.out.println("Texture could not be loaded!");
                }
                i++;
            }
            row++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            //Writing a score into a score map
            scoreMapService.scoreIn = score;

            try {
                ScoreMapService.newScore();

            } catch (IOException ex) {

            }

            this.dispose();
            gameThread.interrupt();

        }

        //Actions for "=" key, for debugging purposes
        if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            score = score + 10;
            scoreLabel.setText("Score: " + score);

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
