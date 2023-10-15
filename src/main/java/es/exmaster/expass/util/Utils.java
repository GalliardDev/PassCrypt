package es.exmaster.expass.util;

import es.exmaster.expass.database.ExPassDAO;

import java.io.File;
import java.io.IOException;

public class Utils {
    public static void appInit() {
        createDBFile();
        ExPassDAO.inicializarBaseDeDatos();
        showSplashScreen();
    }

    private static void showSplashScreen() {
        java.awt.EventQueue.invokeLater(() -> {
            new es.exmaster.expass.gui.SplashScreen().setVisible(true);
        });
    }

    private static void createDBFile() {
        String databaseFolderPath = "C:/Databases";

        File db = new File(databaseFolderPath, "expass.db");

        if(!(db.exists())) {
            try {
                db.createNewFile();
                PopupHandler.BDDCreated();
            } catch (IOException e) {
                PopupHandler.BDDCreationError();
            }
        }
    }
}
