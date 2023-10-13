package es.exmaster.expass;

import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import es.exmaster.expass.common.ActionType;
import es.exmaster.expass.common.KeyPairManager;
import es.exmaster.expass.database.ExPassDAO;
import es.exmaster.expass.gui.MastPassDialog;
import es.exmaster.expass.gui.UIExPass;
import es.exmaster.expass.util.ExLogger;
import es.exmaster.expass.util.PopupHandler;

public class ExPasswordManager {
	public static final String VERSION = "v2.3.1";
	public static final KeyPairManager kpm = new KeyPairManager();

	public static boolean isReady = false;

	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
			System.setProperty("flatlaf.useWindowDecorations", "true");
			System.setProperty("flatlaf.menuBarEmbedded", "true");
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            new ExLogger(ExPasswordManager.class).error(ex.getMessage());
        }

		createDBFile();
        ExPassDAO.inicializarBaseDeDatos();

		showSplashScreen();

        while(!isReady) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				new ExLogger(ExPasswordManager.class).error(e.getMessage());
			}
		}

		if(ExPassDAO.leerTabla("master").isEmpty()) {
			MastPassDialog mpd = new MastPassDialog(UIExPass.getFrame());
			mpd.setActionType(ActionType.INIT);
			mpd.setTitle("Inicializar contraseña maestra");
			mpd.getIntroduceLabel().setText("Nueva contraseña maestra:");
			mpd.setVisible(true);
			mpd.getMasterPassField().requestFocus();
		} else {
			java.awt.EventQueue.invokeLater(() -> {
				new UIExPass().setVisible(true);
			});
		}
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
