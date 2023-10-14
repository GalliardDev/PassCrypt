package es.exmaster.expass;

import java.awt.*;
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
	public static final String VERSION = "v2.4.0";
	public static final KeyPairManager kpm = new KeyPairManager();

	public static boolean isReady = false;

	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
			UIManager.put("TextField.selectionBackground", Color.decode("#ffa84f"));
			UIManager.put("TextField.selectionForeground", Color.decode("#000000"));
			UIManager.put("Component.focusedBorderColor", Color.decode("#febf00"));
			UIManager.put("Button.arc", 5);
			UIManager.put("JComponent.background", Color.decode("#c2701b"));
			UIManager.put("TitlePane.closeHoverBackground", Color.decode("#c2701b"));
			UIManager.put("TitlePane.closePressedBackground", Color.decode("#be6000"));
			UIManager.put("TableHeader.background", Color.decode("#c2701b"));
			UIManager.put("Table.selectionBackground", Color.decode("#ffa84f"));
			UIManager.put("Table.selectionForeground", Color.decode("#000000"));
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
