/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package es.exmaster.expass.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import es.exmaster.expass.database.ExPassDAO;
import es.exmaster.expass.ExPasswordManager;
import es.exmaster.expass.util.ExLogger;
import es.exmaster.expass.util.RSAUtils;

import java.util.List;
import java.util.Objects;
import javax.swing.*;

/**
 *
 * @author jomaa
 */
public class DataPopup extends javax.swing.JFrame {
    /**
     * Creates new form NewPopup
     */
    public DataPopup() {
        initComponents();
        setLocationRelativeTo(UIExPass.getFrame());
        setResizable(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        userLabel = new javax.swing.JLabel();
        siteLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        siteField = new javax.swing.JTextField();
        userField = new javax.swing.JTextField();
        showBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/passlogo.png"))).getImage());

        userLabel.setText("Usuario:");

        siteLabel.setText("Sitio:");

        passwordLabel.setText("Contraseña:");

        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordFieldKeyPressed(evt);
            }
        });

        siteField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                siteFieldKeyPressed(evt);
            }
        });

        userField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userFieldKeyPressed(evt);
            }
        });

        showBtn.setIcon(UIManager.getIcon("PasswordField.revealIcon")); // NOI18N
        showBtn.setAlignmentX(0.5F);
        showBtn.setFocusable(false);
        showBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showBtn.setIconTextGap(0);
        showBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(passwordLabel)
                        .addGap(18, 18, 18)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(siteLabel)
                            .addComponent(userLabel))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userField)
                            .addComponent(siteField))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(siteLabel)
                    .addComponent(siteField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(showBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void showBtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if(passwordField.getEchoChar()=='\u2022'){
            passwordField.setEchoChar('\u0000');
        } else {
            passwordField.setEchoChar('\u2022');
        }
    }                                       

    private void userFieldKeyPressed(java.awt.event.KeyEvent evt) {                                     
        enterKeyEvent(evt);
    }                                    

    private void siteFieldKeyPressed(java.awt.event.KeyEvent evt) {                                     
        enterKeyEvent(evt);
    }                                    

    private void passwordFieldKeyPressed(java.awt.event.KeyEvent evt) {                                         
        enterKeyEvent(evt);
    }                                        
    
    private void enterKeyEvent(java.awt.event.KeyEvent evt) {
        List<String> registroExistente = ExPassDAO.buscarDatosDobleEntrada("passwords", "user", userField.getText(), "site", siteField.getText());
        if (!(userField.getText().isEmpty()
                && siteField.getText().isEmpty()
                && new String(passwordField.getPassword()).isEmpty())) {
            if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                evt.consume();
                if (!registroExistente.isEmpty()) {
                    // Realiza alguna acción si el registro ya existe en la base de datos
                    modificar();
                } else {
                    añadir();
                }
            }
        }
    }
            
    private void añadir() {
        String id = ExPassDAO.leerTabla("passwords").get(ExPassDAO.leerTabla("passwords").size()-1).split(";")[4];
        try {
            if(ExPassDAO.leerTabla("passwords").get(0).contains(";;;;0")) {
                ExPassDAO.limpiarTabla("passwords");
                UIExPass.update();
            }
			ExPassDAO.agregarDatos("passwords", new String[]{
			    userField.getText(),
			    siteField.getText(),
			    RSAUtils.encrypt(new String(passwordField.getPassword()), ExPasswordManager.kpm.getKeyPair().getPublic()),
			    es.exmaster.expass.password.Password.isStrong(new String(passwordField.getPassword())).name(),
                String.valueOf(Integer.parseInt(id)+1)
			});
		} catch (Exception e) {
			new ExLogger(this.getClass()).error("Error al añadir datos", e);
		}
        UIExPass.update();
        clearFields();
        this.dispose();
    }
    
    private void modificar() {
        try {
			ExPassDAO.modificarDatosDobleEntrada("passwords", "user", userField.getText(),
			        "site", siteField.getText(), 
			        new String[] {"password", "strength"},
			        new String[] {RSAUtils.encrypt(new String(passwordField.getPassword()), ExPasswordManager.kpm.getKeyPair().getPublic()),
			        es.exmaster.expass.password.Password.isStrong(new String(passwordField.getPassword())).name()});
		} catch (Exception e) {
			new ExLogger(this.getClass()).error("Error al modificar datos", e);
		}
        UIExPass.update();
        clearFields();
        this.dispose();
    }
    
    private void clearFields(){
        userField.setText("");
        siteField.setText("");
        passwordField.setText("");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify                     
    public static javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton showBtn;
    public static javax.swing.JTextField siteField;
    private javax.swing.JLabel siteLabel;
    public static javax.swing.JTextField userField;
    private javax.swing.JLabel userLabel;
    // End of variables declaration                   
}
