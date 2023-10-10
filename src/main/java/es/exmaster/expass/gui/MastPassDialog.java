/*
 * Created by JFormDesigner on Thu Sep 28 03:24:29 CEST 2023
 */

package es.exmaster.expass.gui;

import es.exmaster.expass.database.ExPassDAO;
import es.exmaster.expass.ExPasswordManager;
import es.exmaster.expass.common.ActionType;
import es.exmaster.expass.util.ExLogger;
import es.exmaster.expass.util.PopupHandler;
import es.exmaster.expass.util.RSAUtils;

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
        setLocationRelativeTo(UIExPass.getFrame());
        setResizable(false);
        requestFocus(true);
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
            masterPass = RSAUtils.decrypt(ExPassDAO.leerTabla("master").get(0), ExPasswordManager.kpm.getKeyPair().getPrivate());
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
                ExPassDAO.agregarDatos("master", new String[] {RSAUtils.encrypt(input, ExPasswordManager.kpm.getKeyPair().getPublic())});
                ExPassDAO.agregarDatos("passwords",
                        new String[] {"", "", "", "", "0"});
                java.awt.EventQueue.invokeLater(() -> {
                    new UIExPass().setVisible(true);
                });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if(masterPassOk()) {
            switch (actionType) {
                case LOGIN:
                    UIExPass.getGuiManager().login(UIExPass.getInstance());
                    break;
                case NEW:
                    UIExPass.getGuiManager().newPass();
                    break;
                case VIEW:
                    UIExPass.getGuiManager().view();
                    break;
                case MODIFY:
                    UIExPass.getGuiManager().modify();
                    break;
                case IMPORT:
                    UIExPass.getGuiManager().importBDD(UIExPass.getInstance());
                    break;
                case EXPORT:
                    UIExPass.getGuiManager().exportBDD();
                    break;
                case REMOVE:
                    UIExPass.getGuiManager().remove();
                    UIExPass.getGuiManager().update(UIExPass.getInstance());
                    break;
            }
        } else {
            if(actionType == ActionType.LOGIN) {
                PopupHandler.wrongMasterPassword();
                System.exit(0);
            } else {
                PopupHandler.wrongMasterPassword();
                new ExLogger(MastPassDialog.class).error("Contraseña maestra incorrecta");
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
                showPassLabel.setIcon(UIManager.getIcon("PasswordField.revealIcon"));
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
    private static JPasswordField masterPassField;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
