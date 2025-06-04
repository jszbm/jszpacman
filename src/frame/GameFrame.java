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

    int requestRows;
    int requestColumns;

    int score = 0;

    GameThread gameThread;

    public GameFrame() {
        addWindowListener(this);
        addKeyListener(this);
        promptForMazeSize();
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

    public void promptForMazeSize() {
        JTextField rowsField = new JTextField(3);
        JTextField columnsField = new JTextField(3);

        JPanel panel = new JPanel();

        panel.add(new JLabel("rows: "));
        panel.add(rowsField);
        panel.add(new JLabel("columns: "));
        panel.add(columnsField);

        int optionPane = JOptionPane.showConfirmDialog(null,
                panel,
                "Enter maze dimensions: ",
                JOptionPane.OK_CANCEL_OPTION);
        if (optionPane == JOptionPane.OK_OPTION) {
            int rows = Integer.parseInt(rowsField.getText());
            int columns = Integer.parseInt(columnsField.getText());

            if (rows <= 100 && columns <= 100) {
                requestRows = rows;
                requestColumns = columns;
            } else {
                JOptionPane.showMessageDialog(null, "The maze can not be bigger than 100 x 100");
                dispose();
            }
        }
    }

    public void addText() {
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        scoreLabel.setText("SCORE: 0000");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(scoreListFont);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(scoreLabel, gbc);

        timeLabel.setText("TIME: 00:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(scoreListFont);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(timeLabel, gbc);

        livesLabel.setText("LIVES: " + player.getLives() + "   ");
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setFont(scoreListFont);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(livesLabel, gbc);
    }

    public void addMaze() {
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        //mazeTable = new JTable(mazeController.getDefaultMazeModel());
        mazeTable = new JTable(mazeController.generateMazeModel(requestRows, requestColumns));
        mazeTable.setDefaultRenderer(Object.class, new PacManTableCellRenderer());
        mazeTable.setEnabled(false);
        mazeTable.setShowGrid(false);
        mazeTable.setIntercellSpacing(new Dimension(0, 0));
        mazePanel.add(mazeTable);
        mazePanel.setBackground(Color.BLACK);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeTableCells(mazePanel.getSize());
            }
        });
        add(mazePanel, gbc);
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
                    redGhost.getCellQueue().add(new Cell());
                    System.out.println("Red ghost found at " + i + ", " + j);
                    break outer;
                }
            }
        }

    }

    public void updateUi() {
        scoreLabel.setText("SCORE: " + String.format("%04d", score));
        timeLabel.setText("TIME: " + gameThread.getTimeThread().getStringTime());
        livesLabel.setText("LIVES: " + player.getLives() + "   ");
        repaint();
    }

    private void resizeTableCells(Dimension dimension) {
        int cols = mazeTable.getColumnCount();
        int rows = mazeTable.getRowCount();

        int cellSize = Math.min(dimension.width / cols, dimension.height / rows);

        for (int i = 0; i < cols; i++) {
            mazeTable.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
        }

        mazeTable.setRowHeight(cellSize);
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
