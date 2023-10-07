package es.exmaster.expass.gui;

import es.exmaster.expass.ExPasswordManager;
import es.exmaster.expass.database.ExPassDAO;
import es.exmaster.expass.util.ExLogger;
import es.exmaster.expass.util.PasswordCellRenderer;
import es.exmaster.expass.util.PopupHandler;
import es.exmaster.expass.util.RSAUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class GUIManager {
    protected static String tempUser;
    protected static String tempSite;
    protected static DataPopup dp = new DataPopup();
    public GUIManager() {;
    }

    public String getTempUser() {
        return tempUser;
    }

    public void setTempUser(String tempUser) {
        this.tempUser = tempUser;
    }

    public String getTempSite() {
        return tempSite;
    }

    public void setTempSite(String tempSite) {
        this.tempSite = tempSite;
    }

    protected void parseVersion(UIExPass ui) {
        ui.setTitle(ui.getTitle().replace("{VERSION}", ExPasswordManager.VERSION));
    }

    protected void importBDD(UIExPass ui) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo para importar");
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            File destino = new File("C:/Databases/expass.db");

            try {
                Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "Importación exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                update(ui);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al importar la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void exportBDD() {
        File origen = new File("C:/Databases/expass.db");

        if (origen.exists()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona la ubicación para exportar");
            fileChooser.setSelectedFile(new File(System.getProperty("user.home") + "/Desktop/expass.db"));
            int seleccion = fileChooser.showSaveDialog(null);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File destino = fileChooser.getSelectedFile();

                try {
                    Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(null, "Exportación exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al exportar la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "La base de datos no existe en la ubicación especificada", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void login(UIExPass ui) {
        update(ui);
        ui.getNewBtn().setEnabled(true);
        ui.getViewBtn().setEnabled(true);
        ui.getModifyBtn().setEnabled(true);
        ui.getImportBtn().setEnabled(true);
        ui.getExportBtn().setEnabled(true);
        ui.getSearchField().setEnabled(true);
    }

    protected void newPass() {
        dp.setTitle("Nueva entrada");
        dp.setVisible(true);
        DataPopup.userField.setText("");
        DataPopup.siteField.setText("");
        DataPopup.passwordField.setText("");
        DataPopup.userField.setEditable(true);
        DataPopup.siteField.setEditable(true);
    }

    protected void view() {
        int rowIndex = UIExPass.getTabla().getSelectedRow();
        if (rowIndex >= 0) {
            String password = null;
            try {
                password = RSAUtils.decrypt(UIExPass.getTabla().getValueAt(rowIndex, 2).toString(), ExPasswordManager.kpm.getKeyPair().getPrivate());
            } catch (Exception ex) {
                new ExLogger(UIExPass.class).error("Error al desencriptar la contraseña", ex);
            }
            if (password != null) {
                JOptionPane.showMessageDialog(UIExPass.getFrame(), password, "Contraseña", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    protected void modify() {
        if(UIExPass.table.getSelectedRow()!=-1) {
            dp.setTitle("Modificar entrada");
            dp.setVisible(true);
            DataPopup.userField.setText(UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 0).toString());
            DataPopup.siteField.setText(UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 1).toString());
            String selectedPassword = UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 2).toString();
            String password = "";
            try {
                password = RSAUtils.decrypt(selectedPassword, ExPasswordManager.kpm.getKeyPair().getPrivate());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            DataPopup.passwordField.setText(password);
            DataPopup.passwordField.requestFocus();
        }
    }

    protected void remove() {
        String[] options = {"Sí", "No"};
        int sel = JOptionPane.showOptionDialog(null, "¿Seguro que quieres eliminar el dato?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (sel) {
            case JOptionPane.YES_OPTION:
                ExPassDAO.eliminarDatosDobleEntrada("passwords", "user", tempUser, "site", tempSite);
                break;
            case JOptionPane.NO_OPTION:
                break;
        }
    }

    protected void copy() {
        int rowIndex = UIExPass.getTabla().getSelectedRow();
        if (rowIndex >= 0) {
            String password = null;
            try {
                password = RSAUtils.decrypt(UIExPass.getTabla().getValueAt(rowIndex, 2).toString(), ExPasswordManager.kpm.getKeyPair().getPrivate());
            } catch (Exception ex) {
                new ExLogger(UIExPass.class).error("Error al desencriptar la contraseña", ex);
            }
            if (password != null) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(password), null);
                PopupHandler.passwordCopied();
            }
        }
    }

    protected void update(UIExPass ui) {
        ((DefaultTableModel) UIExPass.table.getModel()).setRowCount(0);
        ExPassDAO.fillTableFromDatabase((DefaultTableModel) UIExPass.table.getModel());
    }

    protected void blockUntilLogin(UIExPass ui) {
        ((DefaultTableModel) UIExPass.table.getModel()).setRowCount(0);
        ui.getNewBtn().setEnabled(false);
        ui.getViewBtn().setEnabled(false);
        ui.getModifyBtn().setEnabled(false);
        ui.getImportBtn().setEnabled(false);
        ui.getExportBtn().setEnabled(false);
        ui.getSearchField().setEnabled(false);
    }

    protected void applyPassFilter(){
        TableColumnModel columnModel = UIExPass.table.getColumnModel();
        // Crear un nuevo CellRenderer personalizado
        TableCellRenderer passwordCellRenderer = new PasswordCellRenderer();

        // Aplicar el CellRenderer personalizado a todas las celdas de la columna de contraseñas
        int passwordColumnIndex = 2;  // Índice de la columna de contraseñas en la JTable
        TableColumn passwordColumn = columnModel.getColumn(passwordColumnIndex);
        passwordColumn.setCellRenderer(passwordCellRenderer);
    }

    protected void setColumnWidths(JTable table, int[] widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            if (i < columnModel.getColumnCount()) {
                columnModel.getColumn(i).setMaxWidth(widths[i]);
            }
            else break;
        }
    }

    protected void addListenerToSearchBar(UIExPass ui) {
        ui.getSearchField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String text = ui.getSearchField().getText();
                if(!text.isEmpty()) {
                    fillTableFrom2DArray(
                            UIExPass.table,
                            search(text),
                            new String[]{"Usuario", "Sitio", "Contraseña", "Fuerza"}
                    );
                } else {
                    update(ui);
                }
            }
        });
    }
    protected void fillTableFrom2DArray(JTable table, Object[][] data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
        applyPassFilter();
        setColumnWidths(table, new int[] {200,140,80,68});
    }

    protected Object[][] search(String text) {
        DefaultTableModel model = (DefaultTableModel) UIExPass.table.getModel();
        int columnCount = model.getColumnCount();
        List<String> aux = ExPassDAO.leerTabla("passwords").stream()
                .filter(s -> s.split(";")[1].toLowerCase().contains(text.toLowerCase()))
                .toList();

        Object[][] result = new Object[aux.size()][columnCount];
        for (int i = 0; i < aux.size(); i++) {
            result[i] = aux.get(i).split(";");
        }
        return result;
    }

    public void blockUntilLogin() {
        ((DefaultTableModel) UIExPass.table.getModel()).setRowCount(0);
        UIExPass.getInstance().getNewBtn().setEnabled(false);
        UIExPass.getInstance().getViewBtn().setEnabled(false);
        UIExPass.getInstance().getModifyBtn().setEnabled(false);
        UIExPass.getInstance().getImportBtn().setEnabled(false);
        UIExPass.getInstance().getExportBtn().setEnabled(false);
        UIExPass.getInstance().getSearchField().setEnabled(false);
        UIExPass.getInstance().getSearchField().setEnabled(false);
    }
}
