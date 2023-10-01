package es.exmaster.expass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.UIManager;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import es.exmaster.expass.gui.MastPassDialog;
import es.exmaster.expass.gui.UIExPass;
import es.exmaster.expass.util.PopupHandler;
import javax.swing.JOptionPane;

public class Main {
	public static final String PUBLIC_PATH = 
			"C:/Users/" + System.getenv("USERNAME") + "/AppData/Local/.keys/public.txt";
	public static final String PRIVATE_PATH = 
			"C:/Users/" + System.getenv("USERNAME") + "/AppData/Local/.keys/private.txt";
	public static final String BDD = "C:/Databases/expass.db";
	
	public static final String VERSION = "v2.0.0";
	
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIExPass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
        initBDD();
		ExPassDAO.parseOldStrengthValues();
        initFolder();
        generateKeys();
        ExPassDAO.inicializarBaseDeDatos();
                            
        if(ExPassDAO.leerTabla("master").isEmpty()) {
                ExPassDAO.agregarDatos("master", new String[]{JOptionPane.showInputDialog("Introduce una contraseña maestra")});
            }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new UIExPass().setVisible(true);
        });
	}
	
	private static void generateKeys() {
		final String PUBLIC = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAnj3G3S2vu3m0UkUwUJ3iz03psQg36mcU7K6nEqevcBEf29hfIaeSS9OmCWPqoZ6zO1lGCxVFdWQVUyayRJSfWWrXvUuVejGU8H9gATwJrgYrr3aAZXTMkzFb8pUmIxIzloPjcihMzNfBNb4abFi82P2qFvE9YeJs0EcRPSIl63lvqnawWk56SrtEM9AmvMBrDt9KqTKWdXccQ8O2BvKxnThjhke27Z6ODZPzHZWwArpHoXe3Lxx0FXd3dnT1VzBreW/yxMsNi/hk+3ZZKecDrjM2Ryu5SJ3A+DVZd6Edjtmfy8NeOMX/jjHBWqfxUpqCCWReSj48Xb4mGaBvfLgOSpoaG1kE2N7pRZ5PbBKL1v8wkfm5+TsWLzw0eJ7YA4JgFP1s0IglQ0rzPh2yxrPbkD5+AtHzzbTH2OC+W5QZCVK7m+DSEm6gcFCJua6Zz2Q6MFwNcV8tOEAkBbB4J8syhcM9OpGrI3y5XMHlP7KOiUCKSI5mbTYvQb0g0/3xPGdX78PKulqbjDEg7p3MK33kfNSDCeOpjB8W7iYytts+7ZOwUFc9Me89ozvJeSkRSALeKOu/yOufLfNklaf/ruRdBeXp+abij+CcJKHXYwJSNoPJudIbVokNZdgRouSe+o5v60vnMvTwnbYsCYNyvsDhyWh3o1600P3AluUIRKXe5NECAwEAAQ==";
		final String PRIVATE = "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";
		try {
			Files.writeString(Path.of(PRIVATE_PATH), PRIVATE);
			Files.writeString(Path.of(PUBLIC_PATH), PUBLIC);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void initFolder() {
		String folderPath = "C:/Users/" + System.getenv("USERNAME") + "/AppData/Local/.keys";
		File[] documents = new File("C:/").listFiles();
		
		for(File f:documents) {
			if(!(f.getName().equals(".keys"))) {
				try {
					Files.createDirectories(Paths.get(folderPath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					
				}	
			}
		}
	}
	
	private static void initBDD() {
		String databaseFolderPath = "C:/Databases";
		
		File db = new File(databaseFolderPath, "expass.db");
		
    	if(!(db.exists())) {
    		try {
    	        db.createNewFile();
    	        System.out.println("Archivos creados correctamente.");
    	        PopupHandler.BDDCreated();
    	    } catch (IOException e) {
    	        System.out.println("Error al crear los archivos.");
    	        PopupHandler.BDDCreationError();
    	    }
    	} else {
    	    System.out.println("Los archivos ya existen.");
    	}
	}

}
