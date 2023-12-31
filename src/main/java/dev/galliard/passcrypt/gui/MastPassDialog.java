/*
 * Created by JFormDesigner on Thu Sep 28 03:24:29 CEST 2023
 */

package dev.galliard.passcrypt.gui;

import dev.galliard.passcrypt.common.ActionType;
import dev.galliard.passcrypt.database.PassCryptDAO;
import dev.galliard.passcrypt.PassCrypt;
import dev.galliard.passcrypt.util.PCLogger;
import dev.galliard.passcrypt.util.PopupHandler;
import dev.galliard.passcrypt.util.RSAUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author jomaa
 */
public class MastPassDialog extends JDialog {

    private static ActionType actionType;

    public MastPassDialog(Window owner) {
        super(owner);
        actionType = ActionType.INIT;
        initComponents();
        setLocationRelativeTo(UIPassCrypt.getFrame());
        setResizable(false);
        setFocusableWindowState(true);
        setAutoRequestFocus(true);

    }

    public static ActionType getActionType() {
        return actionType;
    }

    public JLabel getIntroduceLabel() {
        return introduceLabel;
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

    protected static boolean masterPassOk() {
        // TODO AQUI HAY RSA
        String input = new String(masterPassField.getPassword());
        String masterPass = null;
        try {
            masterPass = RSAUtils.decrypt(PassCryptDAO.leerTabla("master").get(0), PassCrypt.kpm.getKeyPair().getPrivate());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return input.equals(masterPass);
    }

    private void okActionPerformed(ActionEvent e) {
        // TODO AQUI HAY RSA
        if(actionType.equals(ActionType.INIT)) {
            String input = new String(masterPassField.getPassword());
            try {
                PassCryptDAO.agregarDatos("master", new String[] {RSAUtils.encrypt(input, PassCrypt.kpm.getKeyPair().getPublic())});
                PassCryptDAO.agregarDatos("passwords",
                        new String[] {"", "", "", "", "0"});
                java.awt.EventQueue.invokeLater(() -> {
                    new UIPassCrypt().setVisible(true);
                });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if(masterPassOk()) {
            switch (actionType) {
                case LOGIN:
                    UIPassCrypt.getGuiManager().login(UIPassCrypt.getInstance());
                    break;
                case NEW:
                    UIPassCrypt.getGuiManager().newPass();
                    break;
                case VIEW:
                    UIPassCrypt.getGuiManager().view();
                    break;
                case MODIFY:
                    UIPassCrypt.getGuiManager().modify();
                    break;
                case IMPORT:
                    UIPassCrypt.getGuiManager().importBDD(UIPassCrypt.getInstance());
                    break;
                case EXPORT:
                    UIPassCrypt.getGuiManager().exportBDD();
                    break;
                case REMOVE:
                    UIPassCrypt.getGuiManager().remove();
                    UIPassCrypt.getGuiManager().update(UIPassCrypt.getInstance());
                    break;
            }
        } else {
            if(actionType == ActionType.LOGIN) {
                PopupHandler.wrongMasterPassword();
                System.exit(0);
            } else {
                PopupHandler.wrongMasterPassword();
                new PCLogger(MastPassDialog.class).error("Contraseña maestra incorrecta");
            }
        }
        // de momento así
        setVisible(false);
        new UpdateChecker().run();
    }

    private void thisWindowClosed(WindowEvent e) {
        if(actionType == ActionType.LOGIN) {
            System.exit(0);
        }
    }

    public JPanel getDialogPane() {
        return dialogPane;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JToggleButton getShowPassLabel() {
        return showPassLabel;
    }

    public JPasswordField getMasterPassField() {
        return masterPassField;
    }

    public JPanel getButtonBar() {
        return buttonBar;
    }

    public JButton getOkButton() {
        return okButton;
    }

    private void masterPassFieldKeyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            okActionPerformed(null);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        introduceLabel = new JLabel();
        showPassLabel = new JToggleButton();
        masterPassField = new JPasswordField();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Acci\u00f3n bloqueada");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
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
                showPassLabel.setIcon(new ImageIcon(getClass().getResource("/images/view_small.png")));
                showPassLabel.setFocusable(false);
                showPassLabel.setBackground(new Color(0x585858));
                showPassLabel.setSelectedIcon(new ImageIcon(getClass().getResource("/images/view_selected_small.png")));
                showPassLabel.addActionListener(e -> showPassLabelActionPerformed(e));

                //---- masterPassField ----
                masterPassField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        masterPassFieldKeyPressed(e);
                    }
                });

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(introduceLabel)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(masterPassField, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(showPassLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
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
                okButton.setText("Aceptar");
                okButton.setBackground(new Color(0x585858));
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
    private JToggleButton showPassLabel;
    private static JPasswordField masterPassField;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
