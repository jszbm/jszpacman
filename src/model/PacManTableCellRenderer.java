package model;

import model.cell.Cell;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PacManTableCellRenderer extends JLabel implements TableCellRenderer {

    public PacManTableCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Cell) {
            return (Cell) value;
        } else {
            System.err.println("Could not display a cell. Table cell is not of Cell class.");
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
