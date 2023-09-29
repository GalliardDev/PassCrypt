package es.exmaster.expass.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JButton abrirDialogButton;

    public MainFrame() {
        setTitle("Ejemplo de JDialog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        abrirDialogButton = new JButton("Abrir Diálogo");
        abrirDialogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirDialogo();
            }
        });

        panel.add(abrirDialogButton);
        add(panel);
    }

    private void abrirDialogo() {
        JDialog dialogo = new JDialog(this, "Diálogo de entrada de texto", true);
        JTextField textField = new JTextField(20);
        JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String textoIngresado = textField.getText();
                // Haz algo con el texto ingresado, por ejemplo, mostrarlo en el JFrame principal.
                JOptionPane.showMessageDialog(MainFrame.this, "Texto ingresado: " + textoIngresado);
                dialogo.dispose(); // Cierra el diálogo.
            }
        });

        dialogo.setLayout(new FlowLayout());
        dialogo.add(new JLabel("Ingresa algo: "));
        dialogo.add(textField);
        dialogo.add(okButton);

        dialogo.pack();
        dialogo.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame ventana = new MainFrame();
                ventana.setVisible(true);
            }
        });
    }
}
