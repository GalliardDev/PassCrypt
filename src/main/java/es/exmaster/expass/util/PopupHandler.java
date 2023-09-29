package es.exmaster.expass.util;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;

public class PopupHandler {
	public static void emptyFieldsError() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Hay campos obligatorios vacíos", "Error", JOptionPane.ERROR_MESSAGE);
	}
	public static void passwordAdded() {
		JOptionPane.showMessageDialog(null, "Se ha añadido el dato", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void passwordModified() {
		JOptionPane.showMessageDialog(null, "Se ha modificado el dato", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void passwordRemoved() {
		JOptionPane.showMessageDialog(null, "Se ha eliminado el dato", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	}
    public static void passwordAlreadyExists() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Ese dato ya existe", "Error", JOptionPane.ERROR_MESSAGE);
	}
	public static void directoryCreationError() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Hubo un error al crear una carpeta necesaria para la aplicación", "Error", JOptionPane.ERROR_MESSAGE);
	}
	public static void BDDCreationError() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Hubo un error al crear la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
	}
	public static void BDDCreated() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Se ha creado la base de datos", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void columnNumberError() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "El número de columnas no es el apropiado", "Error", JOptionPane.ERROR_MESSAGE);
	}
        public static void wrongMasterPassword() throws HeadlessException {
		JOptionPane.showMessageDialog(null, "Contraseña maestra incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
	}
}
