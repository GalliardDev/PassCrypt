/*
 * Created by JFormDesigner on Sun Oct 01 04:29:20 CEST 2023
 */

package es.exmaster.expass.gui;

import java.awt.event.*;
import javax.swing.*;

/**
 * @author jomaa
 */
public class ContextMenu extends JPopupMenu {
    public ContextMenu() {
        initComponents();
    }

    public JButton getCopyBtn() {
        return copyBtn;
    }

    public JButton getModifyBtn() {
        return modifyBtn;
    }

    public JButton getRemoveBtn() {
        return removeBtn;
    }

    private void copyBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        this.setVisible(false);
        UIExPass.getGuiManager().copy();
    }

    private void modifyBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        this.setVisible(false);
        UIExPass.getGuiManager().modify();
    }

    private void removeBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        this.setVisible(false);
        UIExPass.getGuiManager().remove();
        UIExPass.getGuiManager().update(UIExPass.getInstance());
    }

    private void viewBtnActionPerformed(ActionEvent e) {
        // TODO add your code here
        this.setVisible(false);
        UIExPass.getGuiManager().view();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        copyBtn = new JButton();
        viewBtn = new JButton();
        modifyBtn = new JButton();
        removeBtn = new JButton();

        //======== this ========

        //---- copyBtn ----
        copyBtn.setText("Copiar");
        copyBtn.addActionListener(e -> copyBtnActionPerformed(e));
        add(copyBtn);

        //---- viewBtn ----
        viewBtn.setText("Ver");
        viewBtn.addActionListener(e -> viewBtnActionPerformed(e));
        add(viewBtn);

        //---- modifyBtn ----
        modifyBtn.setText("Modificar");
        modifyBtn.addActionListener(e -> modifyBtnActionPerformed(e));
        add(modifyBtn);

        //---- removeBtn ----
        removeBtn.setText("Eliminar");
        removeBtn.addActionListener(e -> removeBtnActionPerformed(e));
        add(removeBtn);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JButton copyBtn;
    private JButton viewBtn;
    private JButton modifyBtn;
    private JButton removeBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
