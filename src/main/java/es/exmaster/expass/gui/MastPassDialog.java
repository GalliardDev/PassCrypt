/*
 * Created by JFormDesigner on Thu Sep 28 03:24:29 CEST 2023
 */

package es.exmaster.expass.gui;

import es.exmaster.expass.ExPassDAO;
import es.exmaster.expass.Main;
import es.exmaster.expass.common.ActionType;
import es.exmaster.expass.util.PopupHandler;
import es.exmaster.expass.util.RSAUtils;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author jomaa
 */
public class MastPassDialog extends JDialog {

    private ActionType actionType;

    private DataPopup dp = new DataPopup();

    public MastPassDialog(Window owner) {
        super(owner);
        initComponents();
    }

    public JPanel getDialogPane() {
        return dialogPane;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JLabel getIntroduceLabel() {
        return introduceLabel;
    }

    public JButton getShowPassLabel() {
        return showPassLabel;
    }

    public JPanel getButtonBar() {
        return buttonBar;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    private void showPassLabelActionPerformed(ActionEvent e) {
        if(masterPassField.getEchoChar()=='\u2022'){
            masterPassField.setEchoChar('\u0000');
        } else {
            masterPassField.setEchoChar('\u2022');
        }
    }

    public JPasswordField getMasterPassField() {
        return masterPassField;
    }

    private void okActionPerformed(ActionEvent e) {
        if(this.isVisible()) {
            String input = new String(masterPassField.getPassword());
            String masterPass = null;
            try {
                masterPass = RSAUtils.decrypt(ExPassDAO.leerTabla("master").get(0), RSAUtils.loadPrivateKeyFromFile(Main.PRIVATE_PATH));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            if (input.equals(masterPass)) {
                switch (actionType) {
                    case NEW:
                        dp.setTitle("Nuevo dato");
                        dp.setVisible(true);
                        DataPopup.userField.setText("");
                        DataPopup.siteField.setText("");
                        DataPopup.userField.setEditable(true);
                        DataPopup.siteField.setEditable(true);
                        break;
                    case VIEW:
                        int rowIndex = UIExPass.getTabla().getSelectedRow();
                        if (rowIndex >= 0) {
                            String password = null;
                            try {
                                password = RSAUtils.decrypt(UIExPass.getTabla().getValueAt(rowIndex, 2).toString(), RSAUtils.loadPrivateKeyFromFile(Main.PRIVATE_PATH));
                            } catch (Exception ex) {
                                // TODO Auto-generated catch block
                                ex.printStackTrace();
                            }
                            if (password != null) {
                                JOptionPane.showMessageDialog(rootPane, password);
                            }
                        }
                        break;
                    case MODIFY:
                        dp.setTitle("Modificar dato");
                        dp.setVisible(true);
                        DataPopup.userField.setText(UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 0).toString());
                        DataPopup.siteField.setText(UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 1).toString());
                        DataPopup.passwordField.setText(UIExPass.getTabla().getValueAt(UIExPass.getTabla().getSelectedRow(), 2).toString());
                        DataPopup.passwordField.requestFocus();
                        break;
                    case IMPORT:
                        importBDD();
                        break;
                    case EXPORT:
                        exportBDD();
                        break;
                }
            } else {
                PopupHandler.wrongMasterPassword();
            }
            dispose();
        }
    }

    private void importBDD() {
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
    }

    private void exportBDD() {
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        introduceLabel = new JLabel();
        showPassLabel = new JButton();
        masterPassField = new JPasswordField();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Acci\u00f3n bloqueada");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                //---- introduceLabel ----
                introduceLabel.setText("Introduce la contrase\u00f1a maestra:");

                //---- showPassLabel ----
                showPassLabel.setIcon(new ImageIcon(getClass().getResource("/java/es/exmaster/expass/images/show_small.png")));
                showPassLabel.setFocusable(false);
                showPassLabel.addActionListener(e -> showPassLabelActionPerformed(e));

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(introduceLabel)
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(masterPassField, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(showPassLabel, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(introduceLabel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(showPassLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addComponent(masterPassField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(3, Short.MAX_VALUE))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.setFocusable(false);
                okButton.addActionListener(e -> okActionPerformed(e));
                buttonBar.add(okButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel introduceLabel;
    private JButton showPassLabel;
    private JPasswordField masterPassField;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
