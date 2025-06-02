package frame;

import controller.MazeController;
import controller.ScoreMapController;
import model.PacManTableCellRenderer;
import model.cell.Cell;
import model.entity.PacMan;
import thread.GameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends CustomFrame implements ActionListener, KeyListener {
    static final ScoreMapController scoreMapController = new ScoreMapController();
    static final MazeController mazeController = new MazeController();

    //Game objects
    PacMan player = new PacMan();

    //Visual objects
    JTable mazeTable = new JTable();
    JLabel scoreLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JLabel livesLabel = new JLabel();

    int score = 0;

    //Game thread
    GameThread gameThread = new GameThread(this);

    public GameFrame() {
        setLayout(new GridBagLayout());
        layoutConstraints = new GridBagConstraints();
        addKeyListener(gameThread.getMovementHandler());
        addKeyListener(this);
        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        String name = JOptionPane.showInputDialog("Enter your name: ");
                        scoreMapController.addScore(name, score);
                        gameThread.interrupt();
                    }
                }
        );

        addTextLabels();
        addMaze();

        pack();

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);

        //Threads
        gameThread.start();

    }

    public static void main(String[] args) {
        new GameFrame();
    }

    private static void resizeTableCells(JTable table, Dimension dimension) {
        int cols = table.getColumnCount();
        int rows = table.getRowCount();

        int cellWidth = dimension.width / cols;
        int cellHeight = dimension.height / rows;
        int cellSize = Math.min(cellWidth, cellHeight);

        for (int i = 0; i < cols; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
        }

        table.setRowHeight(cellSize);
    }

    public void addTextLabels() {
        scoreLabel.setText("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(scoreListFont);
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(scoreLabel, layoutConstraints);

        timeLabel.setText("Time: " + gameThread.getTimeThread().getTime());
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(scoreListFont);
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        layoutConstraints.anchor = GridBagConstraints.PAGE_END;
        add(timeLabel, layoutConstraints);

        livesLabel.setText("Lives: " + score);
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setFont(scoreListFont);
        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 1;
        layoutConstraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(livesLabel, layoutConstraints);
    }

    public void updateUi() {
        scoreLabel.setText("Score: " + score);
        timeLabel.setText("Time: " + gameThread.getTimeThread().getStringTime());
        repaint();
    }

    public void addMaze() {
        mazeTable = mazeController.createDefaultMazeTable();
        mazeTable.setDefaultRenderer(Object.class, new PacManTableCellRenderer());
        mazeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mazeTable.setEnabled(false);
        mazeTable.setShowGrid(false);
        mazeTable.setIntercellSpacing(new Dimension(0, 0));
        JPanel mazePanel = new JPanel();
        mazePanel.add(mazeTable);
        mazePanel.setBackground(Color.BLACK);
        layoutConstraints.weighty = 1;
        layoutConstraints.weightx = 1;
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        add(mazePanel, layoutConstraints);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeTableCells(mazeTable, mazePanel.getSize());
            }
        });
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
            String name = JOptionPane.showInputDialog("Enter you name: ");
            scoreMapController.addScore(name, score);
            this.dispose();
            gameThread.interrupt();
        }

        //For debugging purposes
        if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            score = score + 10;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
