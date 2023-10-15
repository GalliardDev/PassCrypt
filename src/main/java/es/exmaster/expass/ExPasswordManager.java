package es.exmaster.expass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.swing.*;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import com.formdev.flatlaf.ui.FlatTextBorder;
import es.exmaster.expass.common.ActionType;
import es.exmaster.expass.common.KeyPairManager;
import es.exmaster.expass.database.ExPassDAO;
import es.exmaster.expass.gui.MastPassDialog;
import es.exmaster.expass.gui.UIExPass;
import es.exmaster.expass.themes.ExPassLaf;
import es.exmaster.expass.util.ExLogger;
import es.exmaster.expass.util.PopupHandler;
import es.exmaster.expass.util.Utils;

public class ExPasswordManager {
	public static final String VERSION = "v2.4.2";
	public static final KeyPairManager kpm = new KeyPairManager();

	private static final String ORANGE = "#fc8600";
	private static final String YELLOW = "#ffae00";
	private static final String LIGHT_YELLOW = "#eac575";
	private static final String LIGHT_ORANGE = "#dca261";
	private static final String DARK_ORANGE = "#a65800";
	private static final String BLACK = "#000000";
	private static final String BACKGROUND = "#1e1e1e";

	public static boolean isReady = false;

	public static void main(String[] args) {
		ExPassLaf.setup();
		UIManager.put("TextField.selectionBackground", Color.decode(LIGHT_ORANGE));
		UIManager.put("TextField.selectionForeground", Color.decode(BLACK));

		UIManager.put("Component.focusedBorderColor", Color.decode(YELLOW));
		UIManager.put("Component.focusWidth", 0);
		UIManager.put("Component.innerFocusWidth", 0);

		UIManager.put("Button.arc", 10);
		UIManager.put("Button.background", Color.decode(ORANGE));

		UIManager.put("TitlePane.closeHoverBackground", Color.decode(ORANGE));
		UIManager.put("TitlePane.closePressedBackground", Color.decode(DARK_ORANGE));
		UIManager.put("TitlePane.borderColor", Color.decode(ORANGE));

		UIManager.put("ScrollPane.smoothScrolling", true);

		UIManager.put("TableHeader.background", Color.decode(YELLOW));
		UIManager.put("TableHeader.foreground", Color.decode(BLACK));
		UIManager.put("TableHeader.cellBorder", BorderFactory.createEmptyBorder());
		UIManager.put("Table.selectionBackground", Color.decode(LIGHT_YELLOW));
		UIManager.put("Table.selectionForeground", Color.decode(BLACK));
		UIManager.put("Table.border", BorderFactory.createEmptyBorder());

		UIManager.put("ComboBox.squareButton", true);
		UIManager.put("ComboBox.selectionBackground", Color.decode(LIGHT_ORANGE));
		UIManager.put("ComboBox.selectionForeground", Color.decode(BLACK));
		UIManager.put("ComboBox.buttonDarkShadow", Color.decode(BLACK));
		UIManager.put("ComboBox.buttonBackground", Color.decode(ORANGE));
		UIManager.put("ComboBox.buttonHighlight", Color.decode(YELLOW));
		UIManager.put("ComboBox.buttonShadow", Color.decode(ORANGE));

		UIManager.put("ScrollBar.thumb", Color.decode(ORANGE));
		UIManager.put("ScrollBar.thumbDarkShadow", Color.decode(ORANGE));
		UIManager.put("ScrollBar.thumbHighlight", Color.decode(ORANGE));
		UIManager.put("ScrollBar.thumbShadow", Color.decode(ORANGE));
		UIManager.put("ScrollBar.track", Color.decode(BACKGROUND));
		UIManager.put("ScrollBar.trackHighlight", Color.decode(BACKGROUND));
		UIManager.put("ScrollBar.trackHighlightForeground", Color.decode(BACKGROUND));
		UIManager.put("ScrollBar.trackForeground", Color.decode(BACKGROUND));

		Utils.appInit();

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
}
