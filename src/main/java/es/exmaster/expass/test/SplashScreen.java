package es.exmaster.expass.test;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SplashScreen extends JWindow {
    private JProgressBar progressBar;

    public SplashScreen() {
        JPanel content = new JPanel(new BorderLayout());
        JLabel label = new JLabel(new ImageIcon("splash_image.png")); // Reemplaza "splash_image.png" con la ruta de tu imagen de SplashScreen
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        content.add(label, BorderLayout.CENTER);
        content.add(progressBar, BorderLayout.SOUTH);
        setContentPane(content);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); // Simula la carga (reemplaza esto con tu lógica de carga real)
                    publish(i); // Publica el progreso actual
                }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SplashScreen().setVisible(true);
            // Inicia tu aplicación principal después de que el SplashScreen se cierre
            // Ejemplo: new TuAppPrincipal().setVisible(true);
        });
    }
}


