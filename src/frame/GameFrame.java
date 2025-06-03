package frame;

import controller.MazeController;
import controller.ScoreMapController;
import model.PacManTableCellRenderer;
import model.PacManTableModel;
import model.cell.*;
import model.entity.PacMan;
import thread.GameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends CustomFrame implements ActionListener {
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
        addKeyListener(this);
        addKeyListener(player);
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
        addEntities();

        pack();

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);

        //Threads
        gameThread.start();

    }

    public void gameOver() {
        shortcutThread.interrupt();
        gameThread.interrupt();
        String name = JOptionPane.showInputDialog("Enter you name: ");
        scoreMapController.addScore(name, score);
        this.dispose();
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

    public void addMaze() {
        mazeTable = new JTable(mazeController.getDefaultMazeModel());
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

    public void addEntities() {
        for (int i = mazeTable.getRowCount() / 2 + 2; i < mazeTable.getRowCount(); i++) {
            for (int j = mazeTable.getColumnCount() / 2; j < mazeTable.getColumnCount(); j++) {
                if (mazeTable.getValueAt(i, j).getClass() == Cell.class) {
                    mazeTable.setValueAt(player, i, j);
                    player.setRow(i);
                    player.setColumn(j);
                    System.out.println("PacMan placed at " + i + ", " + j);
                    break;
                }
            }
        }
    }

    public void executeGameLogic() {
        checkMaze();
        movePlayer();
    }

    public void movePlayer() {
        int desiredRow;
        int desiredColumn;
        switch (player.getDirection()) {
            case UP:
                desiredRow = player.getRow() - 1;
                desiredColumn = player.getColumn();
                if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() != Wall.class) {
                    if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() == Dot.class) {
                        score += 10;
                    }
                    if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() == PowerDot.class) {
                        score += 100;
                    }

                    mazeTable.setValueAt(player, desiredRow, desiredColumn);
                    mazeTable.setValueAt(new Cell(), player.getRow(), player.getColumn());
                    player.setRow(desiredRow);
                    player.setColumn(desiredColumn);
                }

                break;
            case DOWN:
                desiredRow = player.getRow() + 1;
                desiredColumn = player.getColumn();
                if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() != Wall.class) {
                    if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() == Dot.class) {
                        score += 10;
                    }
                    if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() == PowerDot.class) {
                        score += 100;
                    }

                    mazeTable.setValueAt(player, desiredRow, desiredColumn);
                    mazeTable.setValueAt(new Cell(), player.getRow(), player.getColumn());
                    player.setRow(desiredRow);
                    player.setColumn(desiredColumn);

                }
                break;
            case RIGHT:
                desiredRow = player.getRow();
                desiredColumn = player.getColumn() + 1;
                if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() != Wall.class) {
                    if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() == Dot.class) {
                        score += 10;
                    }
                    if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() == PowerDot.class) {
                        score += 100;
                    }

                    mazeTable.setValueAt(player, desiredRow, desiredColumn);
                    mazeTable.setValueAt(new Cell(), player.getRow(), player.getColumn());
                    player.setRow(desiredRow);
                    player.setColumn(desiredColumn);
                }
                break;
            case LEFT:
                desiredRow = player.getRow();
                desiredColumn = player.getColumn() - 1;
                if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() != Wall.class) {
                    if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() == Dot.class) {
                        score += 10;
                    }
                    if (mazeTable.getValueAt(desiredRow, desiredColumn).getClass() == PowerDot.class) {
                        score += 100;
                    }

                    mazeTable.setValueAt(player, desiredRow, desiredColumn);
                    mazeTable.setValueAt(new Cell(), player.getRow(), player.getColumn());
                    player.setRow(desiredRow);
                    player.setColumn(desiredColumn);
                }
                break;
            case null:
                break;
        }
    }

    public void checkMaze() {
        PacManTableModel model = (PacManTableModel) mazeTable.getModel();
        if (!model.contains(Dot.class)) {
            gameOver();
        }
    }

    @Override
    public void checkQuitShortcut() {
        if (isCtrlPressed && isShiftPressed && isQPressed) {
            gameOver();
        }
    }

    public void updateUi() {
        scoreLabel.setText("Score: " + score);
        timeLabel.setText("Time: " + gameThread.getTimeThread().getStringTime());
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
            gameOver();
        }

        //For debugging purposes
        if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            score = score + 10;
        }
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
