/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.exmaster.expass.util;

/**
 *
 * @author jomaa
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

@SuppressWarnings("serial")
public class PasswordCellRenderer extends DefaultTableCellRenderer {
    private static final String PASSWORD_PLACEHOLDER = "************";
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value instanceof String password) {
            if (!password.isEmpty()) {
                setText(PASSWORD_PLACEHOLDER);
            } else {
                setText("");
            }
        }

        return this;
    }
}
