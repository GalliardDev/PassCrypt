/*
 * Created by JFormDesigner on Wed Oct 04 03:33:26 CEST 2023
 */

package es.exmaster.expass.gui;

import es.exmaster.expass.ExPasswordManager;
import es.exmaster.expass.common.KeyPairManager;
import es.exmaster.expass.database.ExPassDAO;
import es.exmaster.expass.util.ExLogger;

import java.awt.*;
import javax.swing.*;
import java.util.List;

/**
 * @author jomaa
 */
public class SplashScreen extends JFrame {
    public SplashScreen() {
        initComponents();
        finalizeInit();
    }

    private void finalizeInit() {
        setLocationRelativeTo(null);
        loadBarProgress();
    }

    private void loadBarProgress() {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                int currentProgress = 0;
                if (!ExPassDAO.leerTabla("keys").isEmpty()
                        && !ExPassDAO.leerTabla("master").isEmpty()
                        && !ExPassDAO.leerTabla("passwords").isEmpty()) {

                    ExPassDAO.parseOldStrengthValues();
                    currentProgress += 20;
                    publish(currentProgress);
                    new ExLogger(SplashScreen.class).info("Progreso: " + currentProgress + "%");
                    Thread.sleep(50);

                    KeyPairManager.decryptOldThenEncryptNew();
                    currentProgress += 70;
                    publish(currentProgress);
                    new ExLogger(SplashScreen.class).info("Progreso: " + currentProgress + "%");
                    Thread.sleep(50);

                    KeyPairManager.saveKeysBase64();
                    currentProgress += 10;
                    publish(currentProgress);
                    new ExLogger(SplashScreen.class).info("Progreso: " + currentProgress + "%");
                    Thread.sleep(50);
                } else {
                    KeyPairManager.saveKeysBase64();
                    currentProgress += 100;
                    publish(currentProgress);
                    new ExLogger(SplashScreen.class).info("Progreso: " + currentProgress + "%");
                }
                ExPasswordManager.isReady = true;
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int latestProgress = chunks.get(chunks.size() - 1);
                progressBar.setValue(latestProgress); // Actualiza la barra de progreso
            }

            @Override
            protected void done() {
                dispose(); // Cierra el SplashScreen después de que la carga esté completa
            }
        };

        worker.execute(); // Inicia el SwingWorker
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
        progressBar = new JProgressBar();
        bgLabel = new JLabel();

        //======== this ========
        setUndecorated(true);
        setIconImage(new ImageIcon(getClass().getResource("/images/passlogo.png")).getImage());
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- progressBar ----
        progressBar.setForeground(new Color(0x3399ff));
        progressBar.setBorder(null);
        progressBar.setBackground(new Color(0x00323232, true));
        contentPane.add(progressBar);
        progressBar.setBounds(10, 460, 380, 10);

        //---- bgLabel ----
        bgLabel.setIcon(new ImageIcon(getClass().getResource("/images/splashscreen.png")));
        contentPane.add(bgLabel);
        bgLabel.setBounds(new Rectangle(new Point(0, 0), bgLabel.getPreferredSize()));

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - José Manuel Amador Gallardo (José Manuel Amador)
    private JProgressBar progressBar;
    private JLabel bgLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SplashScreen().setVisible(true);
        });
    }
}
