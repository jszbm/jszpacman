package frame;

import controller.MazeController;
import controller.ScoreMapController;
import model.PacManTableCellRenderer;
import model.PacManTableModel;
import model.cell.*;
import model.entity.*;
import thread.GameThread;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

public class GameFrame extends CustomFrame implements KeyListener {
    static final ScoreMapController scoreMapController = new ScoreMapController();
    static final MazeController mazeController = new MazeController();

    PacMan player;
    RedGhost redGhost;
    PinkGhost pinkGhost;
    CyanGhost cyanGhost;
    OrangeGhost orangeGhost;
    Set<Class<?>> collisions = Set.of(
            Wall.class,
            Tunnel.class,
            PinkGhost.class,
            RedGhost.class,
            CyanGhost.class,
            OrangeGhost.class,
            PacMan.class
    );

    int orangeTimer = 0;

    int time;

    int appleTimer = 0;
    int ghostUpdateTime = 100;

    JTable mazeTable = new JTable();

    PacManTableModel mazeTableModel;

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
        addKeyListener(this);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeContent(mazePanel.getSize());
            }
        });

        this.gameThread = new GameThread(this);
        gameThread.start();
    }

    public void gameOver() {
        shortcutThread.interrupt();
        gameThread.interrupt();
        String name = JOptionPane.showInputDialog("Enter you name: ");
        scoreMapController.addScore(name, score);
        this.dispose();
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
        mazeTableModel = (PacManTableModel) mazeTable.getModel();
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
                    continue;
                }
                if (mazeTable.getValueAt(i, j).getClass() == PinkGhost.class) {
                    pinkGhost = (PinkGhost) mazeTable.getValueAt(i, j);
                    pinkGhost.setRow(i);
                    pinkGhost.setColumn(j);
                    pinkGhost.getCellQueue().add(new Cell());
                    continue;
                }
                if (mazeTable.getValueAt(i, j).getClass() == CyanGhost.class) {
                    cyanGhost = (CyanGhost) mazeTable.getValueAt(i, j);
                    cyanGhost.setRow(i);
                    cyanGhost.setColumn(j);
                    cyanGhost.getCellQueue().add(new Cell());
                    continue;
                }
                if (mazeTable.getValueAt(i, j).getClass() == OrangeGhost.class) {
                    orangeGhost = (OrangeGhost) mazeTable.getValueAt(i, j);
                    orangeGhost.setRow(i);
                    orangeGhost.setColumn(j);
                    orangeGhost.getCellQueue().add(new Cell());
                    continue;
                }

            }
        }

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

    public void animateEntities() {
        player.nextTexture();
        if (appleTimer == 0) {
            redGhost.nextTexture();
            pinkGhost.nextTexture();
            cyanGhost.nextTexture();
            orangeGhost.nextTexture();
        } else {
            redGhost.nextFreezeTexture();
            pinkGhost.nextFreezeTexture();
            cyanGhost.nextFreezeTexture();
            orangeGhost.nextFreezeTexture();
        }
    }

    public void setTime(int time) {
        this.time = time;
        if (orangeTimer != 0) {
            orangeTimer--;
        }
        if (appleTimer != 0) {
            appleTimer--;
            ghostUpdateTime = 250;
        } else {
            ghostUpdateTime = 125;
        }
    }

    public synchronized void updateUi() {
        scoreLabel.setText("SCORE: " + String.format("%04d", score));
        timeLabel.setText("TIME: " + String.format("%02d:%02d", time / 600, time / 10));
        livesLabel.setText("LIVES: " + player.getLives() + "   ");
    }


    private void resizeContent(Dimension dimension) {
        int width = getWidth();
        int height = getHeight();
        int fontSize = Math.min(width, height) / factor;

        Font newFont = defaultFont.deriveFont((float) fontSize);
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
        if (player.getLives() <= 0) {
            gameOver();
        }
        if (mazeTableModel.getDotCount() == 0) {
            gameOver();
        }
        if (player.getDirection() != Direction.IDLE) {
            movePlayer();
        }
    }

    public void executeGhostLogic() {
        if (redGhost.getDirection() == Direction.IDLE && orangeTimer == 0) {
            redGhost.setDirection(Direction.UP);
        }
        if (pinkGhost.getDirection() == Direction.IDLE && orangeTimer == 0) {
            pinkGhost.setDirection(Direction.UP);
        }
        if (cyanGhost.getDirection() == Direction.IDLE && orangeTimer == 0) {
            cyanGhost.setDirection(Direction.UP);
        }

        moveEntity(redGhost);
        moveEntity(pinkGhost);
        moveEntity(cyanGhost);
        moveEntity(orangeGhost);
    }

    public synchronized void moveEntity(Entity entity) {
        int desiredRow;
        int desiredColumn;
        int row = entity.getRow();
        int column = entity.getColumn();

        if (orangeTimer == 0 || entity.getClass() == OrangeGhost.class) {
            switch (entity.getDirection()) {
                case UP:
                    desiredRow = row - 1;
                    desiredColumn = column;
                    processEntity(entity, desiredRow, desiredColumn);
                    break;

                case DOWN:
                    desiredRow = row + 1;
                    desiredColumn = column;
                    processEntity(entity, desiredRow, desiredColumn);
                    break;

                case RIGHT:
                    desiredRow = row;
                    desiredColumn = column + 1;
                    processEntity(entity, desiredRow, desiredColumn);
                    break;

                case LEFT:
                    desiredRow = row;
                    desiredColumn = column - 1;
                    processEntity(entity, desiredRow, desiredColumn);
                    break;

                default:
                    break;

            }
        } else {
            entity.setDirection(Direction.IDLE);
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

            case IDLE:
                break;

        }
    }

    public synchronized void processEntity(Entity entity, int desiredRow, int desiredColumn) {
        try {
            Cell desiredCell = (Cell) mazeTable.getValueAt(desiredRow, desiredColumn);
            Class<?> desiredCellClass = desiredCell.getClass();

            if (desiredCellClass == PacMan.class) {
                player.subtractLives();
            }

            if (!collisions.contains(desiredCellClass)) {

                Cell leaveBehind = entity.getCellQueue().poll();

                if (time > 4 && time % 50 == 0 && Math.random() <= 0.25) {
                    if (leaveBehind.getClass() == Dot.class) {
                        mazeTableModel.deleteDot();
                    }
                    if (Math.random() > 0.5) {
                        leaveBehind = new Orange();
                    } else {
                        leaveBehind = new Apple();
                    }
                }

                entity.getCellQueue().add(desiredCell);
                mazeTable.setValueAt(entity, desiredRow, desiredColumn);
                mazeTable.setValueAt(leaveBehind, entity.getRow(), entity.getColumn());
                entity.setRow(desiredRow);
                entity.setColumn(desiredColumn);


            }
        } catch (ArrayIndexOutOfBoundsException _) {
        }
    }

    public synchronized void processPlayer(JTable maze, int desiredRow, int desiredColumn) {
        try {
            var desiredCellClass = maze.getValueAt(desiredRow, desiredColumn).getClass();

            if (desiredCellClass == Orange.class) {
                score += 100;
                orangeTimer = 50;
            }

            if (desiredCellClass == Apple.class) {
                score += 100;
                appleTimer = 50;
            }

            if (desiredCellClass == RedGhost.class ||
                    desiredCellClass == PinkGhost.class ||
                    desiredCellClass == CyanGhost.class ||
                    desiredCellClass == Orange.class) {
                if (orangeTimer == 0) {
                    player.subtractLives();
                } else {
                    score += 1000;
                }
            }

            if (desiredCellClass != Wall.class && desiredCellClass != Gate.class) {
                if (desiredCellClass == Dot.class) {
                    mazeTableModel.deleteDot();
                    score += 10;
                }
                if (desiredCellClass == PowerDot.class) {
                    mazeTableModel.deleteDot();
                    score += 100;
                }

                maze.setValueAt(player, desiredRow, desiredColumn);
                maze.setValueAt(new Cell(), player.getRow(), player.getColumn());
                player.setRow(desiredRow);
                player.setColumn(desiredColumn);
            } else {
                player.setDirection(Direction.IDLE);
            }
        } catch (ArrayIndexOutOfBoundsException _) {
        }
    }

    @Override
    public void processQuitShortcut() {
        if (isCtrlPressed && isShiftPressed && isQPressed) {
            shortcutThread.interrupt();
            gameThread.interrupt();
            this.dispose();
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

    public OrangeGhost getOrangeGhost() {
        return orangeGhost;
    }

    public CyanGhost getCyanGhost() {
        return cyanGhost;
    }

    public RedGhost getRedGhost() {
        return redGhost;
    }

    public PinkGhost getPinkGhost() {
        return pinkGhost;
    }

    public int getGhostUpdateTime() {
        return ghostUpdateTime;
    }

    public Set<Class<?>> getCollisions() {
        return collisions;
    }
}
