package es.exmaster.expass.util;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

class ProgressRenderer extends JProgressBar implements TableCellRenderer {
    public ProgressRenderer(int min, int max) {
        super(min, max);
        this.setStringPainted(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        this.setValue((Integer) value);
        return this;
    }
}
