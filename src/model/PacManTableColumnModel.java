package model;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class PacManTableColumnModel extends DefaultTableColumnModel {

    @Override
    public void addColumn(TableColumn aColumn) {
        aColumn.setMinWidth(0);
        aColumn.setMaxWidth(1000);
        super.addColumn(aColumn);
    }
}
