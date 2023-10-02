package es.exmaster.expass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import es.exmaster.expass.common.ActionType;
import es.exmaster.expass.common.KeyPairManager;
import es.exmaster.expass.gui.MastPassDialog;
import es.exmaster.expass.gui.UIExPass;
import es.exmaster.expass.util.PopupHandler;
import es.exmaster.expass.util.RSAUtils;

import javax.swing.JOptionPane;

public class Main {
	public static final String VERSION = "v2.2.0";
	public static final KeyPairManager kpm = new KeyPairManager();

	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIExPass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

		initBDD();
        ExPassDAO.inicializarBaseDeDatos();
		if(!ExPassDAO.leerTabla("keys").isEmpty()
				&& !ExPassDAO.leerTabla("master").isEmpty()
				&& !ExPassDAO.leerTabla("passwords").isEmpty()) {
			ExPassDAO.parseOldStrengthValues();
			KeyPairManager.decryptOldThenEncryptNew();
			KeyPairManager.saveKeysBase64();
		} else {
			KeyPairManager.saveKeysBase64();
		}

        if(ExPassDAO.leerTabla("master").isEmpty()) {
			MastPassDialog mpd = new MastPassDialog(null);
			mpd.setTitle("Inicializar contraseña maestra");
			mpd.getIntroduceLabel().setText("Nueva contraseña maestra:");
			mpd.setActionType(ActionType.INIT);
			mpd.setVisible(true);
		} else {
			java.awt.EventQueue.invokeLater(() -> {
				new UIExPass().setVisible(true);
			});
		}
	}

	private static void initBDD() {
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
