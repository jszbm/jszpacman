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
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends CustomFrame implements WindowListener, KeyListener {
    static final ScoreMapController scoreMapController = new ScoreMapController();
    static final MazeController mazeController = new MazeController();

    PacMan player;
    RedGhost redGhost;

    JTable mazeTable = new JTable();

    JPanel mazePanel = new JPanel();
    JLabel scoreLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JLabel livesLabel = new JLabel();

    int rows;
    int columns;

    int score = 0;

    GameThread gameThread;

    public GameFrame(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        setLayout(new GridBagLayout());

        addMaze();
        addEntities();
        addText();

        pack();

        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        addWindowListener(this);
        addKeyListener(this);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeContent(mazePanel.getSize());
            }
        });

        gameThread = new GameThread(this);
        gameThread.start();
    }

    public void gameOver() {
        shortcutThread.interrupt();
        gameThread.interrupt();
        String name = JOptionPane.showInputDialog("Enter you name: ");
        scoreMapController.addScore(name, score);
        this.dispose();
    }

    public void addText() {
        gbc.insets = new Insets(0, width / factor, width / factor, width / factor);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;

        scoreLabel.setText("SCORE: 0000");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(defaultFont);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(scoreLabel, gbc);

        timeLabel.setText("TIME: 00:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(defaultFont);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(timeLabel, gbc);

        livesLabel.setText("LIVES: " + player.getLives() + "   ");
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setFont(defaultFont);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(livesLabel, gbc);
    }

    public void addMaze() {
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        //mazeTable = new JTable(mazeController.getDefaultMazeModel());
        mazeTable = new JTable(mazeController.generateMazeModel(rows, columns));
        for (int i = 0; i < mazeTable.getColumnCount(); i++) {
            TableColumn col = mazeTable.getColumnModel().getColumn(i);
            col.setMinWidth(0);
            col.setMaxWidth(1000);
        }
        mazeTable.setDefaultRenderer(Object.class, new PacManTableCellRenderer());
        mazeTable.setEnabled(false);
        mazeTable.setShowGrid(false);
        mazeTable.setIntercellSpacing(new Dimension(0, 0));
        mazePanel.add(mazeTable);
        mazePanel.setBackground(Color.BLACK);
        add(mazePanel, gbc);
    }

    public void addEntities() {
        outer:
        for (int i = 0; i < mazeTable.getRowCount(); i++) {
            for (int j = 0; j < mazeTable.getColumnCount(); j++) {
                if (mazeTable.getValueAt(i, j).getClass() == PacMan.class) {
                    player = (PacMan) mazeTable.getValueAt(i, j);
                    addKeyListener(player);
                    player.setRow(i);
                    player.setColumn(j);
                    player.getCellQueue().add(new Cell());
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
                    redGhost.getCellQueue().add(new Cell());
                    break outer;
                }
            }
        }

    }

    public void updateUi() {
        scoreLabel.setText("SCORE: " + String.format("%04d", score));
        timeLabel.setText("TIME: " + gameThread.getTimeThread().getStringTime());
        livesLabel.setText("LIVES: " + player.getLives() + "   ");
    }


    private void resizeContent(Dimension dimension) {
        int width = getWidth();
        int height = getHeight();
        int fontSize = Math.min(width, height)/factor;

        Font newFont = defaultFont.deriveFont((float)fontSize);
        scoreLabel.setFont(newFont);
        timeLabel.setFont(newFont);
        livesLabel.setFont(newFont);

        int cols = mazeTable.getColumnCount();
        int rows = mazeTable.getRowCount();

        int cellSize = Math.min(dimension.width / cols, dimension.height / rows);

        mazeTable.setRowHeight(cellSize);
        mazeTable.setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
    }

    public void executeGameLogic() {
        checkMaze((PacManTableModel) mazeTable.getModel());
        movePlayer();
        moveRedGhost();
    }

    public void moveRedGhost() {
        int desiredRow;
        int desiredColumn;
        int row = redGhost.getRow();
        int column = redGhost.getColumn();

        switch (redGhost.getDirection()) {
            case UP:
                desiredRow = row - 1;
                desiredColumn = column;
                processEntity(redGhost, desiredRow, desiredColumn);
                break;

            case DOWN:
                desiredRow = row + 1;
                desiredColumn = column;
                processEntity(redGhost, desiredRow, desiredColumn);
                break;

            case RIGHT:
                desiredRow = row;
                desiredColumn = column + 1;
                processEntity(redGhost, desiredRow, desiredColumn);
                break;

            case LEFT:
                desiredRow = row;
                desiredColumn = column - 1;
                processEntity(redGhost, desiredRow, desiredColumn);
                break;

            case null:
                break;

        }

    }

    public void movePlayer() {
        int desiredRow;
        int desiredColumn;
        int row = player.getRow();
        int column = player.getColumn();

        switch (player.getDirection()) {
            case UP:
                desiredRow = row - 1;
                desiredColumn = column;
                processPlayer(mazeTable, desiredRow, desiredColumn);
                break;

            case DOWN:
                desiredRow = row + 1;
                desiredColumn = column;
                processPlayer(mazeTable, desiredRow, desiredColumn);
                break;

            case RIGHT:
                desiredRow = row;
                desiredColumn = column + 1;
                processPlayer(mazeTable, desiredRow, desiredColumn);
                break;

            case LEFT:
                desiredRow = row;
                desiredColumn = column - 1;
                processPlayer(mazeTable, desiredRow, desiredColumn);
                break;

            case null:
                desiredRow = 0;
                desiredColumn = 0;
                processPlayer(mazeTable, desiredRow, desiredColumn);
                break;

        }
    }

    public void processEntity(Entity entity, int desiredRow, int desiredColumn) {
        try {
            Cell desiredCell = (Cell) mazeTable.getValueAt(desiredRow, desiredColumn);

            if (desiredCell.getClass() == PacMan.class) {
                player.subtractLives();
            }

            if (desiredCell.getClass() != Wall.class && desiredCell.getClass() != Tunnel.class) {
                entity.getCellQueue().add(desiredCell);
                mazeTable.setValueAt(entity, desiredRow, desiredColumn);
                mazeTable.setValueAt(entity.getCellQueue().poll(), entity.getRow(), entity.getColumn());
                entity.setRow(desiredRow);
                entity.setColumn(desiredColumn);
                entity.nextTexture();
            }
        } catch (ArrayIndexOutOfBoundsException _) {
        }
    }

    public void processPlayer(JTable maze, int desiredRow, int desiredColumn) {
        try {
            var desiredCell = maze.getValueAt(desiredRow, desiredColumn).getClass();
            PacManTableModel model = (PacManTableModel) mazeTable.getModel();

            if (desiredCell == RedGhost.class && player.getLives() > 0) {
                player.subtractLives();
            } else if (player.getLives() == 0) {
                gameOver();
            }

            if (desiredCell != Wall.class && desiredCell != Gate.class) {
                if (desiredCell == Dot.class) {
                    model.deleteDot();
                    score += 10;
                }
                if (desiredCell == PowerDot.class) {
                    model.deleteDot();
                    score += 100;
                }

                maze.setValueAt(player, desiredRow, desiredColumn);
                maze.setValueAt(new Cell(), player.getRow(), player.getColumn());
                player.setRow(desiredRow);
                player.setColumn(desiredColumn);
                player.nextTexture();
            }
        } catch (ArrayIndexOutOfBoundsException _) {
        }
    }

    public void checkMaze(PacManTableModel model) {
        if (model.getDotCount() == 0) {
            gameOver();
        }
    }

    @Override
    public void processQuitShortcut() {
        if (isCtrlPressed && isShiftPressed && isQPressed) {
            gameOver();
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

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        gameOver();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
