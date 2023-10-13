package es.exmaster.expass.util;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;

public class PopupHandler {
	public static void BDDCreationError() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Hubo un error al crear la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
	}
	public static void BDDCreated() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Se ha creado la base de datos", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void wrongMasterPassword() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Contraseña maestra incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
