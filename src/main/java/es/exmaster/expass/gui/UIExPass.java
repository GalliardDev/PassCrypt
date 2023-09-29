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
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author jomaa
 */
public class UIExPass extends JFrame {
    private DataPopup dp = new DataPopup();
    private String tempUser;
    private String tempSite;

    public UIExPass() {
        initComponents();
        finalizeInit();
    }

    @Override
    public Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("es/exmaster/expass/images/passlogo.png"));
    }

    public static JTable getTabla(){
        return table;
    }

    public void setTempUser(String user){
        this.tempUser = user;
    }

    public void setTempSite(String site){
        this.tempSite = site;
    }

    public String getTempUser(){
        return this.tempUser;
    }

    public String getTempSite() {
        return this.tempSite;
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
        // TODO add your handling code here:
        MastPassDialog mpd = new MastPassDialog(this);
        mpd.setVisible(true);
        mpd.setActionType(ActionType.NEW);
    }

    private void modifyBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        MastPassDialog mpd = new MastPassDialog(this);
        mpd.setVisible(true);
        mpd.setActionType(ActionType.MODIFY);
    }

    private void importBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        MastPassDialog mpd = new MastPassDialog(this);
        mpd.setVisible(true);
        mpd.setActionType(ActionType.IMPORT);
    }

    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        MastPassDialog mpd = new MastPassDialog(this);
        mpd.setVisible(true);
        mpd.setActionType(ActionType.EXPORT);
    }

    private void viewBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        MastPassDialog mpd = new MastPassDialog(this);
        mpd.setVisible(true);
        mpd.setActionType(ActionType.VIEW);
    }

    private void tableKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            evt.consume(); // Evitar el comportamiento predeterminado del tabulador
            remove();
            update();
        }
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        int selectedRowIndex = table.getSelectedRow();
        if(selectedRowIndex >= 0){
            tempUser = table.getValueAt(selectedRowIndex, 0).toString();
            tempSite = table.getValueAt(selectedRowIndex, 1).toString();
            DataPopup.userField.setEditable(false);
            DataPopup.siteField.setEditable(false);
            DataPopup.userField.setText(tempUser);
            DataPopup.siteField.setText(tempSite);
            DataPopup.passwordField.requestFocus();
        }
    }

    private void remove() {
        String[] options = {"Sí", "No"};
        int sel = JOptionPane.showOptionDialog(null, "¿Seguro que quieres eliminar el dato?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        switch (sel) {
            case JOptionPane.YES_OPTION:
                ExPassDAO.eliminarDatosDobleEntrada("passwords", "user", tempUser, "site", tempSite);
                PopupHandler.passwordRemoved();
                break; // Agrega un break para salir del switch
            case JOptionPane.NO_OPTION:
                // No necesitas hacer nada aquí, pero también puedes agregar un break si lo prefieres.
                break;
        }
        //ExPassDAO.eliminarDatosDobleEntrada("passwords", "user", tempUser, "site", tempSite);
    }

    public static void update(){
        ((DefaultTableModel) table.getModel()).setRowCount(0);
        ExPassDAO.fillTableFromDatabase((DefaultTableModel) table.getModel());
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
        setIconImage(new ImageIcon(getClass().getResource("/java/es/exmaster/expass/images/passlogo.png")).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
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
            tablePanel.setViewportView(table);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JToolBar toolBar;
    private JButton newBtn;
    private JButton viewBtn;
    private JButton modifyBtn;
    private JButton importBtn;
    private JButton exportBtn;
    private JScrollPane tablePanel;
    public static JTable table;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
