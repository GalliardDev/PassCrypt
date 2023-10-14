/*
 * Created by JFormDesigner on Thu Sep 28 02:37:02 CEST 2023
 */

package es.exmaster.expass.gui;

import java.awt.event.*;
import java.util.*;
import es.exmaster.expass.database.ExPassDAO;
import es.exmaster.expass.ExPasswordManager;
import es.exmaster.expass.common.ActionType;
import es.exmaster.expass.util.ExLogger;
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
    private static JFrame frame;
    private GUIManager guiManager = new GUIManager();

    public UIExPass() {
        frame = this;
        initComponents();
        finalizeInit();

    }

    protected static UIExPass getInstance() {
        return (UIExPass) frame;
    }

    public static GUIManager getGuiManager() {
        return UIExPass.getInstance().guiManager;
    }

    @Override
    public Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/passlogo.png"));
    }

    protected static JTable getTabla(){
        return table;
    }

    public static JFrame getFrame() {
        return frame;
    }
    private void finalizeInit() {
        setLocationRelativeTo(null);
        parseVersion();
        guiManager.update(this);
        guiManager.applyPassFilter();
        guiManager.blockUntilLogin();
        guiManager.addListenerToSearchBar(this);
        guiManager.setColumnWidths(table, new int[] {200,140,80,68});
        this.getRootPane().setJMenuBar(toolBar);
        if(!(MastPassDialog.getActionType() == ActionType.INIT)) {
            MastPassDialog mpd = new MastPassDialog(this);
            mpd.setVisible(true);
            mpd.setActionType(ActionType.LOGIN);
        } else {
            guiManager.login(this);
        }

    }

    private void parseVersion() {
        this.setTitle(this.getTitle().replace("{VERSION}", ExPasswordManager.VERSION));
    }

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {
        guiManager.newPass();
    }

    private void modifyBtnActionPerformed(java.awt.event.ActionEvent evt) {
        guiManager.modify();
    }

    private void importBtnActionPerformed(java.awt.event.ActionEvent evt) {
        guiManager.importBDD(this);
    }

    private void exportBtnActionPerformed(java.awt.event.ActionEvent evt) {
        guiManager.exportBDD();
    }

    private void viewBtnActionPerformed(java.awt.event.ActionEvent evt) {
        guiManager.view();
    }

    private void tableKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            evt.consume(); // Evitar el comportamiento predeterminado del tabulador
            guiManager.remove();
            guiManager.update(this);
        }
    }

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRowIndex = table.getSelectedRow();
        if(selectedRowIndex >= 0 && evt.getButton() == MouseEvent.BUTTON1){
            guiManager.setTempUser(table.getValueAt(selectedRowIndex, 0).toString());
            guiManager.setTempSite(table.getValueAt(selectedRowIndex, 1).toString());
        } else if(selectedRowIndex >= 0 && evt.getButton() == MouseEvent.BUTTON3) {
            ContextMenu cm = new ContextMenu();
            cm.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }

    public JButton getNewBtn() {
        return newBtn;
    }

    public JButton getViewBtn() {
        return viewBtn;
    }

    public JButton getModifyBtn() {
        return modifyBtn;
    }

    public JButton getImportBtn() {
        return importBtn;
    }

    public JButton getExportBtn() {
        return exportBtn;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    private void searchFieldInputMethodTextChanged(InputMethodEvent e) {
        // TODO add your code here

    }

    public JScrollPane getTablePanel() {
        return tablePanel;
    }

    public JTable getTable() {
        return table;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        tablePanel = new JScrollPane();
        table = new JTable();
        searchField = new JTextField();
        buscarLabel = new JLabel();
        toolBar = new JMenuBar();
        newBtn = new JButton();
        viewBtn = new JButton();
        modifyBtn = new JButton();
        separator1 = new JPopupMenu.Separator();
        importBtn = new JButton();
        exportBtn = new JButton();

        //======== this ========
        System.setProperty("flatlaf.useWindowDecorations", "true");
        System.setProperty("flatlaf.menuBarEmbedded", "true");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/images/passlogo.png")).getImage());
        setAutoRequestFocus(false);
        setResizable(false);
        var contentPane = getContentPane();

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
            guiManager.setColumnWidths(table, new int[] {200,140,80,68});
            tablePanel.setViewportView(table);
        }

        //---- buscarLabel ----
        buscarLabel.setText("Buscar");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(buscarLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(searchField, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buscarLabel)
                        .addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());

        //======== toolBar ========
        {

            //---- newBtn ----
            newBtn.setText("Nueva");
            newBtn.setMaximumSize(new Dimension(88, 26));
            newBtn.setMargin(new Insets(2, 2, 2, 2));
            newBtn.setIcon(new ImageIcon(getClass().getResource("/images/newpass_small.png")));
            newBtn.setFocusable(false);
            newBtn.addActionListener(e -> newBtnActionPerformed(e));
            toolBar.add(newBtn);

            //---- viewBtn ----
            viewBtn.setText("Ver");
            viewBtn.setMargin(new Insets(2, 2, 2, 12));
            viewBtn.setIcon(new ImageIcon(getClass().getResource("/images/view_small.png")));
            viewBtn.setFocusable(false);
            viewBtn.addActionListener(e -> viewBtnActionPerformed(e));
            toolBar.add(viewBtn);

            //---- modifyBtn ----
            modifyBtn.setIcon(new ImageIcon(getClass().getResource("/images/edit_small.png")));
            modifyBtn.setText("Editar");
            modifyBtn.setMargin(new Insets(2, 2, 2, 12));
            modifyBtn.setFocusable(false);
            modifyBtn.addActionListener(e -> modifyBtnActionPerformed(e));
            toolBar.add(modifyBtn);

            //---- separator1 ----
            separator1.setForeground(new Color(0xdddddd));
            toolBar.add(separator1);

            //---- importBtn ----
            importBtn.setIcon(new ImageIcon(getClass().getResource("/images/import_small.png")));
            importBtn.setText("Importar");
            importBtn.setMargin(new Insets(2, 2, 2, 12));
            importBtn.setFocusable(false);
            importBtn.addActionListener(e -> importBtnActionPerformed(e));
            toolBar.add(importBtn);

            //---- exportBtn ----
            exportBtn.setIcon(new ImageIcon(getClass().getResource("/images/export_small.png")));
            exportBtn.setText("Exportar");
            exportBtn.setMargin(new Insets(2, 2, 2, 12));
            exportBtn.setFocusable(false);
            exportBtn.addActionListener(e -> exportBtnActionPerformed(e));
            toolBar.add(exportBtn);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    protected static JScrollPane tablePanel;
    protected static JTable table;
    public static JTextField searchField;
    private JLabel buscarLabel;
    private JMenuBar toolBar;
    protected static JButton newBtn;
    protected static JButton viewBtn;
    protected static JButton modifyBtn;
    private JPopupMenu.Separator separator1;
    protected static JButton importBtn;
    protected static JButton exportBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
