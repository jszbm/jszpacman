package model;

import model.cell.Cell;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PacManTableCellRenderer extends JLabel implements TableCellRenderer {

    public PacManTableCellRenderer() {
        setOpaque(true);
        setBackground(Color.BLACK);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Cell cell = getCell(value);
            setIcon(new ImageIcon(cell.getTexture().getScaledInstance(table.getRowHeight(), table.getColumnModel().getColumn(0).getWidth(), Image.SCALE_SMOOTH)));
            return this;
    }

    protected Cell getCell(Object value) {
        return (value instanceof Cell cell) ? cell : null;
    }

}
