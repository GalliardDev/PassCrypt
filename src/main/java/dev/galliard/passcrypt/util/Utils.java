package dev.galliard.passcrypt.util;

import dev.galliard.passcrypt.database.PassCryptDAO;
import dev.galliard.passcrypt.gui.SplashScreen;

import java.io.File;
import java.io.IOException;

public class Utils {
    public static void appInit() {
        createDBFile();
        PassCryptDAO.inicializarBaseDeDatos();
        showSplashScreen();
    }

    private static void showSplashScreen() {
        java.awt.EventQueue.invokeLater(() -> {
            new SplashScreen().setVisible(true);
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
