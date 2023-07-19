/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package es.exmaster.expass.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import es.exmaster.expass.ExPassDAO;
import es.exmaster.expass.Main;
import es.exmaster.expass.util.PasswordCellRenderer;
import es.exmaster.expass.util.PopupHandler;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author jomaa
 */
@SuppressWarnings("serial")
public class ExPassUI extends javax.swing.JFrame {
    private DataPopup dp = new DataPopup();
    private String tempUser;
    private String tempSite;
    /**
     * Creates new form ExPassUI
     */
    public ExPassUI() {
        initComponents();
        finalizeInit();
    }

    public JTable getTabla(){
        return ExPassUI.table;
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        toolBar = new javax.swing.JToolBar();
        newBtn = new javax.swing.JButton();
        viewBtn = new javax.swing.JButton();
        modifyBtn = new javax.swing.JButton();
        importBtn = new javax.swing.JButton();
        exportBtn = new javax.swing.JButton();
        wrapperPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ExPassword Manager " + Main.VERSION);
        setBackground(new java.awt.Color(255, 255, 255));

        toolBar.setRollover(true);

        newBtn.setText("Nueva");
        newBtn.setFocusable(false);
        newBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });
        toolBar.add(newBtn);

        viewBtn.setText("Ver");
        viewBtn.setFocusable(false);
        viewBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        viewBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        viewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBtnActionPerformed(evt);
            }
        });
        toolBar.add(viewBtn);

        modifyBtn.setText("Modificar");
        modifyBtn.setFocusable(false);
        modifyBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        modifyBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        modifyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyBtnActionPerformed(evt);
            }
        });
        toolBar.add(modifyBtn);

        importBtn.setText("Importar");
        importBtn.setFocusable(false);
        importBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        importBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        importBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importBtnActionPerformed(evt);
            }
        });
        toolBar.add(importBtn);

        exportBtn.setText("Exportar");
        exportBtn.setFocusable(false);
        exportBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exportBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        exportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBtnActionPerformed(evt);
            }
        });
        toolBar.add(exportBtn);

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
        table.setShowGrid(true);
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

        javax.swing.GroupLayout wrapperPanelLayout = new javax.swing.GroupLayout(wrapperPanel);
        wrapperPanel.setLayout(wrapperPanelLayout);
        wrapperPanelLayout.setHorizontalGroup(
            wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrapperPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablePanel)
                .addContainerGap())
        );
        wrapperPanelLayout.setVerticalGroup(
            wrapperPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrapperPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(wrapperPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(wrapperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        dp.setTitle("Nuevo dato");
        dp.setVisible(true);
        DataPopup.userField.setText("");
        DataPopup.siteField.setText("");
        DataPopup.userField.setEditable(true);
        DataPopup.siteField.setEditable(true);
    }                                      

    private void modifyBtnActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    	String input = JOptionPane.showInputDialog("Ingresa la contraseña maestra");
        String masterPass = ExPassDAO.leerTabla("master").get(0);
        if (input != null && input.equals(masterPass)) {
        	dp.setTitle("Modificar dato");
            dp.setVisible(true);
            DataPopup.passwordField.requestFocus();
        } else {
        	PopupHandler.wrongMasterPassword();
        }
    }                                         

    private void importBtnActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        importBDD();
    }                                         

    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        exportBDD();
    }                                         

    private void tableKeyPressed(java.awt.event.KeyEvent evt) {                                 
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            evt.consume(); // Evitar el comportamiento predeterminado del tabulador
            remove();
            update();
        }
    }                                

    private void viewBtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        String input = JOptionPane.showInputDialog("Ingresa la contraseña maestra");
        String masterPass = ExPassDAO.leerTabla("master").get(0);
        if (input != null && input.equals(masterPass)) {
            int rowIndex = table.getSelectedRow();
            if (rowIndex >= 0) {
                Object password = table.getValueAt(rowIndex, 2);
                if (password instanceof String) {
                    JOptionPane.showMessageDialog(rootPane, password);
                }
            }
        } else {
            PopupHandler.wrongMasterPassword();
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
            System.out.println(tempUser);
            System.out.println(tempSite);
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
    
    private void importBDD() {
    	String input = JOptionPane.showInputDialog("Ingresa la contraseña maestra");
        String masterPass = ExPassDAO.leerTabla("master").get(0);
        if(input!=null && input == masterPass) {
        	JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona el archivo para importar");
            int seleccion = fileChooser.showOpenDialog(null);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivoSeleccionado = fileChooser.getSelectedFile();
                File destino = new File("C:/Databases/passwords.db");

                try {
                    Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(null, "Importación exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al importar la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
        	PopupHandler.wrongMasterPassword();
        }
        
    }

    private void exportBDD() {
    	String input = JOptionPane.showInputDialog("Ingresa la contraseña maestra");
        String masterPass = ExPassDAO.leerTabla("master").get(0);
        if(input!=null && input == masterPass) {
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
        } else {
        	PopupHandler.wrongMasterPassword();
        }
    }
    
    private void finalizeInit(){
        setLocationRelativeTo(null);
        update();
        applyPassFilter();
    }
    
    @SuppressWarnings("unused")
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
                    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExPassUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton exportBtn;
    private javax.swing.JButton importBtn;
    private javax.swing.JButton modifyBtn;
    private javax.swing.JButton newBtn;
    public static javax.swing.JTable table;
    private javax.swing.JScrollPane tablePanel;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton viewBtn;
    private javax.swing.JPanel wrapperPanel;
    // End of variables declaration                   
}
