/*
 * Created by JFormDesigner on Thu Sep 28 02:37:02 CEST 2023
 */

package es.exmaster.expass.gui;

import es.exmaster.expass.ExPassDAO;
import es.exmaster.expass.Main;
import es.exmaster.expass.common.ActionType;
import es.exmaster.expass.util.PasswordCellRenderer;
import es.exmaster.expass.util.PopupHandler;
import es.exmaster.expass.util.RSAUtils;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author jomaa
 */
public class UIExPass extends JFrame {
    private static String tempUser;
    private static String tempSite;
    private static DataPopup dp = new DataPopup();
    private static JFrame frame;

    public UIExPass() {
        frame = this;
        initComponents();
        finalizeInit();
        emptyTableOnInit();
        blockButtonsUntilLogin();
        MastPassDialog mpd = new MastPassDialog(this);
        mpd.setVisible(true);
        mpd.setActionType(ActionType.LOGIN);
    }

    @Override
    public Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/passlogo.png"));
    }

    protected static JTable getTabla(){
        return table;
    }

    protected static JFrame getFrame() {
        return frame;
    }
    private void finalizeInit() {
        setLocationRelativeTo(null);
        parseVersion();
        update();
        applyPassFilter();
    }

    private void parseVersion() {
        this.setTitle(this.getTitle().replace("{VERSION}", Main.VERSION));
    }

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {
        newPass();
    }

    private void modifyBtnActionPerformed(java.awt.event.ActionEvent evt) {
        modify();
    }

    private void importBtnActionPerformed(java.awt.event.ActionEvent evt) {
        importBDD();
    }

    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {
        exportBDD();
    }

    private void viewBtnActionPerformed(java.awt.event.ActionEvent evt) {
        view();
    }

    private void tableKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            evt.consume(); // Evitar el comportamiento predeterminado del tabulador
            remove();
            update();
        }
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRowIndex = table.getSelectedRow();
        if(selectedRowIndex >= 0 && evt.getButton() == MouseEvent.BUTTON1){
            tempUser = table.getValueAt(selectedRowIndex, 0).toString();
            tempSite = table.getValueAt(selectedRowIndex, 1).toString();
        } else if(selectedRowIndex >= 0 && evt.getButton() == MouseEvent.BUTTON3) {
            ContextMenu cm = new ContextMenu();
            cm.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    protected static void importBDD() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona el archivo para importar");
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            File destino = new File("C:/Databases/expass.db");

            try {
                Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "Importación exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                update();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al importar la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected static void exportBDD() {
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

    protected static void login() {
        update();
        newBtn.setEnabled(true);
        viewBtn.setEnabled(true);
        modifyBtn.setEnabled(true);
        importBtn.setEnabled(true);
        exportBtn.setEnabled(true);
    }

    protected static void newPass() {
        dp.setTitle("Nueva entrada");
        dp.setVisible(true);
        DataPopup.userField.setText("");
        DataPopup.siteField.setText("");
        DataPopup.passwordField.setText("");
        DataPopup.userField.setEditable(true);
        DataPopup.siteField.setEditable(true);
    }

    protected static void view() {
        int rowIndex = UIExPass.getTabla().getSelectedRow();
        if (rowIndex >= 0) {
            String password = null;
            try {
                password = RSAUtils.decrypt(UIExPass.getTabla().getValueAt(rowIndex, 2).toString(), RSAUtils.loadPrivateKeyFromFile(Main.PRIVATE_PATH));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (password != null) {
                JOptionPane.showMessageDialog(frame, password, "Contraseña", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    protected static void modify() {
        dp.setTitle("Modificar entrada");
        dp.setVisible(true);
        DataPopup.userField.setText(UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 0).toString());
        DataPopup.siteField.setText(UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 1).toString());
        String selectedPassword = UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 2).toString();
        String password = "";
        try {
            password = RSAUtils.decrypt(selectedPassword, RSAUtils.loadPrivateKeyFromFile(Main.PRIVATE_PATH));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        DataPopup.passwordField.setText(password);
        DataPopup.passwordField.requestFocus();
    }

    protected static void remove() {
        String[] options = {"Sí", "No"};
        int sel = JOptionPane.showOptionDialog(null, "¿Seguro que quieres eliminar el dato?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (sel) {
            case JOptionPane.YES_OPTION:
                ExPassDAO.eliminarDatosDobleEntrada("passwords", "user", tempUser, "site", tempSite);
                PopupHandler.passwordRemoved();
                break;
            case JOptionPane.NO_OPTION:
                break;
        }
    }

    protected static void copy() {
        int rowIndex = UIExPass.getTabla().getSelectedRow();
        if (rowIndex >= 0) {
            String password = null;
            try {
                password = RSAUtils.decrypt(UIExPass.getTabla().getValueAt(rowIndex, 2).toString(), RSAUtils.loadPrivateKeyFromFile(Main.PRIVATE_PATH));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (password != null) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(password), null);
                PopupHandler.passwordCopied();
            }
        }
    }

    protected static void update(){
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        ExPassDAO.fillTableFromDatabase((DefaultTableModel) table.getModel());
    }

    protected static void emptyTableOnInit() {
        ((DefaultTableModel) table.getModel()).setRowCount(0);
    }

    protected static void blockButtonsUntilLogin() {
        newBtn.setEnabled(false);
        viewBtn.setEnabled(false);
        modifyBtn.setEnabled(false);
        importBtn.setEnabled(false);
        exportBtn.setEnabled(false);
    }

    private void applyPassFilter(){
        TableColumnModel columnModel = table.getColumnModel();
        // Obtener el renderer de celdas por defecto
        TableCellRenderer defaultRenderer = table.getDefaultRenderer(String.class);
        // Crear un nuevo CellRenderer personalizado
        TableCellRenderer passwordCellRenderer = new PasswordCellRenderer();

        // Aplicar el CellRenderer personalizado a todas las celdas de la columna de contraseñas
        int passwordColumnIndex = 2;  // Índice de la columna de contraseñas en la JTable
        TableColumn passwordColumn = columnModel.getColumn(passwordColumnIndex);
        passwordColumn.setCellRenderer(passwordCellRenderer);
    }

    private static void setColumnWidths(JTable table, int[] widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < widths.length; i++) {
            if (i < columnModel.getColumnCount()) {
                columnModel.getColumn(i).setMaxWidth(widths[i]);
            }
            else break;
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        toolBar = new JToolBar();
        newBtn = new JButton();
        viewBtn = new JButton();
        modifyBtn = new JButton();
        importBtn = new JButton();
        exportBtn = new JButton();
        tablePanel = new JScrollPane();
        table = new JTable();

        //======== this ========
        setTitle("ExPass Manager {VERSION}");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource("/images/passlogo.png")).getImage());
        var contentPane = getContentPane();

        //======== toolBar ========
        {

            //---- newBtn ----
            newBtn.setText("Nueva");
            newBtn.addActionListener(e -> newBtnActionPerformed(e));
            toolBar.add(newBtn);

            //---- viewBtn ----
            viewBtn.setText("Ver");
            viewBtn.addActionListener(e -> viewBtnActionPerformed(e));
            toolBar.add(viewBtn);

            //---- modifyBtn ----
            modifyBtn.setText("Modificar");
            modifyBtn.addActionListener(e -> modifyBtnActionPerformed(e));
            toolBar.add(modifyBtn);
            toolBar.addSeparator();

            //---- importBtn ----
            importBtn.setText("Importar");
            importBtn.addActionListener(e -> importBtnActionPerformed(e));
            toolBar.add(importBtn);

            //---- exportBtn ----
            exportBtn.setText("Exportar");
            exportBtn.addActionListener(e -> exportBtnActionPerformed(e));
            toolBar.add(exportBtn);
        }

        //======== tablePanel ========
        {

            //---- table ----
            table.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {

                        },
                        new String [] {
                            "Usuario", "Sitio", "Contraseña", "Fuerza"
                        }
                    ) {
                        @SuppressWarnings("rawtypes")
                        Class[] types = new Class [] {
                            java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                        };
                        boolean[] canEdit = new boolean [] {
                            false, false, false, false
                        };

                        @SuppressWarnings("rawtypes")
                        public Class getColumnClass(int columnIndex) {
                            return types [columnIndex];
                        }

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return canEdit [columnIndex];
                        }
                    });
                    table.getTableHeader().setReorderingAllowed(false);
                    table.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            tableMouseClicked(evt);
                        }
                    });
                    table.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyPressed(java.awt.event.KeyEvent evt) {
                            tableKeyPressed(evt);
                        }
                    });
                    tablePanel.setViewportView(table);
                    if (table.getColumnModel().getColumnCount() > 0) {
                        table.getColumnModel().getColumn(0).setResizable(false);
                        table.getColumnModel().getColumn(1).setResizable(false);
                        table.getColumnModel().getColumn(2).setResizable(false);
                        table.getColumnModel().getColumn(3).setResizable(false);
                    }
            setColumnWidths(table, new int[] {200,140,80,68});
            tablePanel.setViewportView(table);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JToolBar toolBar;
    protected static JButton newBtn;
    protected static JButton viewBtn;
    protected static JButton modifyBtn;
    protected static JButton importBtn;
    protected static JButton exportBtn;
    protected static JScrollPane tablePanel;
    protected static JTable table;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
