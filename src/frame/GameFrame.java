package frame;

import controller.MazeController;
import controller.ScoreMapController;
import model.PacManTableCellRenderer;
import model.PacManTableModel;
import model.cell.*;
import model.entity.Entity;
import model.entity.PacMan;
import model.entity.RedGhost;
import thread.GameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends CustomFrame implements ActionListener {
    static final ScoreMapController scoreMapController = new ScoreMapController();
    static final MazeController mazeController = new MazeController();

    //Game objects
    PacMan player;
    RedGhost redGhost;

    //Visual objects
    JTable mazeTable = new JTable();
    JLabel scoreLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JLabel livesLabel = new JLabel();

    int score = 0;

    //Game thread
    GameThread gameThread;

    public GameFrame() {
        setLayout(new GridBagLayout());
        layoutConstraints = new GridBagConstraints();
        addKeyListener(this);
        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        gameOver();
                    }
                }
        );

        addMaze();
        addEntities();
        addText();

        pack();

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);

        //Threads
        gameThread = new GameThread(this);
        gameThread.start();

    }

    private static void resizeTableCells(JTable table, Dimension dimension) {
        int cols = table.getColumnCount();
        int rows = table.getRowCount();

        int cellWidth = dimension.width / cols;
        int cellHeight = dimension.height / rows;
        //int cellSize = Math.min(cellWidth, cellHeight);

        for (int i = 0; i < cols; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(Math.min(cellWidth, cellHeight));
        }

        table.setRowHeight(Math.min(cellWidth, cellHeight));
    }

    /*public static void main(String[] args) {
        new GameFrame();
    }*/

    public void gameOver() {
        shortcutThread.interrupt();
        gameThread.interrupt();
        String name = JOptionPane.showInputDialog("Enter you name: ");
        scoreMapController.addScore(name, score);
        this.dispose();
    }

    public void addText() {
        layoutConstraints.gridy = 1;
        layoutConstraints.gridx = 1;
        layoutConstraints.fill = GridBagConstraints.NONE;

        scoreLabel.setText("SCORE: 0000");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(scoreListFont);
        layoutConstraints.gridx = 0;
        layoutConstraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(scoreLabel, layoutConstraints);

        timeLabel.setText("TIME: 00:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(scoreListFont);
        layoutConstraints.gridx = 1;
        layoutConstraints.anchor = GridBagConstraints.PAGE_END;
        add(timeLabel, layoutConstraints);

        livesLabel.setText("LIVES: " + player.getLives() + "   ");
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setFont(scoreListFont);
        layoutConstraints.gridx = 2;
        layoutConstraints.anchor = GridBagConstraints.LAST_LINE_END;
        add(livesLabel, layoutConstraints);
    }

    public void addMaze() {
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.weighty = 1;
        layoutConstraints.weightx = 1;
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 3;
        layoutConstraints.anchor = GridBagConstraints.CENTER;

        mazeTable = new JTable(mazeController.getDefaultMazeModel());
        //mazeTable = new JTable(mazeController.generateMazeModel());
        mazeTable.setDefaultRenderer(Object.class, new PacManTableCellRenderer());
        mazeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mazeTable.setEnabled(false);
        mazeTable.setShowGrid(false);
        mazeTable.setIntercellSpacing(new Dimension(0, 0));
        JPanel mazePanel = new JPanel();
        mazePanel.add(mazeTable);
        mazePanel.setBackground(Color.BLACK);
        add(mazePanel, layoutConstraints);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeTableCells(mazeTable, mazePanel.getSize());
            }
        });
    }


    public void addEntities() {
        outer:
        for (int i = mazeTable.getRowCount() / 2 + 2; i < mazeTable.getRowCount(); i++) {
            for (int j = mazeTable.getColumnCount() / 2; j < mazeTable.getColumnCount(); j++) {
                if (mazeTable.getValueAt(i, j).getClass() == Cell.class && player == null) {
                    player = new PacMan();
                    addKeyListener(player);
                    mazeTable.setValueAt(player, i, j);
                    player.setRow(i);
                    player.setColumn(j);
                    System.out.println("PacMan placed at " + i + ", " + j);
                    break outer;
                }
            }
        }

        outer:
        for (int i = 0; i < mazeTable.getRowCount(); i++) {
            for (int j = 0; j < mazeTable.getColumnCount(); j++) {
                if (mazeTable.getValueAt(i, j).getClass() == RedGhost.class) {
                    redGhost = (RedGhost) mazeTable.getValueAt(i, j);
                    redGhost.setRow(i);
                    redGhost.setColumn(j);
                    System.out.println("Red ghost placed at " + i + ", " + j);
                    break outer;
                }
            }
        }

    }

    public void executeGameLogic() {
        checkMaze();
        processPlayer();
        processGhost();
    }

    public void processGhost() {
        int desiredRow;
        int desiredColumn;
        switch (redGhost.getDirection()) {
            case UP:
                desiredRow = redGhost.getRow() - 1;
                desiredColumn = redGhost.getColumn();
                moveEntity(redGhost, desiredRow, desiredColumn);
                break;

            case DOWN:
                desiredRow = redGhost.getRow() + 1;
                desiredColumn = redGhost.getColumn();
                moveEntity(redGhost, desiredRow, desiredColumn);
                break;

            case RIGHT:
                desiredRow = redGhost.getRow();
                desiredColumn = redGhost.getColumn() + 1;
                moveEntity(redGhost, desiredRow, desiredColumn);
                break;

            case LEFT:
                desiredRow = redGhost.getRow();
                desiredColumn = redGhost.getColumn() - 1;
                moveEntity(redGhost, desiredRow, desiredColumn);
                break;

            case null:
                break;

        }


    }

    public void processPlayer() {
        int desiredRow;
        int desiredColumn;
        switch (player.getDirection()) {
            case UP:
                desiredRow = player.getRow() - 1;
                desiredColumn = player.getColumn();
                movePlayer(mazeTable, desiredRow, desiredColumn);
                break;

            case DOWN:
                desiredRow = player.getRow() + 1;
                desiredColumn = player.getColumn();
                movePlayer(mazeTable, desiredRow, desiredColumn);
                break;

            case RIGHT:
                desiredRow = player.getRow();
                desiredColumn = player.getColumn() + 1;
                movePlayer(mazeTable, desiredRow, desiredColumn);
                break;

            case LEFT:
                desiredRow = player.getRow();
                desiredColumn = player.getColumn() - 1;
                movePlayer(mazeTable, desiredRow, desiredColumn);
                break;

            case null:
                break;

        }
    }

    public void moveEntity(Entity entity, int desiredRow, int desiredColumn) {
        try {
            var desiredCell = mazeTable.getValueAt(desiredRow, desiredColumn);

            if (desiredCell.getClass() == PacMan.class) {
                player.subtractLives();
            }

            if (desiredCell.getClass() != Wall.class && desiredCell.getClass() != Tunnel.class) {
                mazeTable.setValueAt(entity, desiredRow, desiredColumn);
                mazeTable.setValueAt(desiredCell, entity.getRow(), entity.getColumn());
                entity.setRow(desiredRow);
                entity.setColumn(desiredColumn);
            }
        } catch (ArrayIndexOutOfBoundsException _) {
        }
    }

    public void movePlayer(JTable maze, int desiredRow, int desiredColumn) {
        try {
            var desiredCell = maze.getValueAt(desiredRow, desiredColumn).getClass();

            if (desiredCell == RedGhost.class && player.getLives() > 0) {
                player.subtractLives();
            } else if (player.getLives() == 0) {
                gameOver();
            }

            if (desiredCell != Wall.class && desiredCell != Gate.class) {
                if (desiredCell == Dot.class) {
                    score += 10;
                }
                if (desiredCell == PowerDot.class) {
                    score += 100;
                }

                maze.setValueAt(player, desiredRow, desiredColumn);
                maze.setValueAt(new Cell(), player.getRow(), player.getColumn());
                player.setRow(desiredRow);
                player.setColumn(desiredColumn);
            }
        } catch (ArrayIndexOutOfBoundsException _) {
        }
    }

    public void checkMaze() {
        PacManTableModel model = (PacManTableModel) mazeTable.getModel();
        if (!model.contains(Dot.class)) {
            gameOver();
        }
    }

    @Override
    public void processQuitShortcut() {
        if (isCtrlPressed && isShiftPressed && isQPressed) {
            gameOver();
        }
    }

    public void updateUi() {
        scoreLabel.setText("SCORE: " + String.format("%04d", score));
        timeLabel.setText("TIME: " + gameThread.getTimeThread().getStringTime());
        livesLabel.setText("LIVES: " + player.getLives() + "   ");
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

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

    public JTable getMazeTable() {
        return mazeTable;
    }

    public RedGhost getRedGhost() {
        return redGhost;
    }
}
