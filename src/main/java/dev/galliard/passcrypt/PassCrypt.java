package dev.galliard.passcrypt;

import java.awt.*;

import javax.swing.*;

import dev.galliard.passcrypt.common.ActionType;
import dev.galliard.passcrypt.common.KeyPairManager;
import dev.galliard.passcrypt.database.PassCryptDAO;
import dev.galliard.passcrypt.gui.MastPassDialog;
import dev.galliard.passcrypt.gui.UIPassCrypt;
import dev.galliard.passcrypt.themes.PassCryptLaf;
import dev.galliard.passcrypt.util.PCLogger;
import dev.galliard.passcrypt.util.Utils;

public class PassCrypt {
	public static final String VERSION = "v2.4.4";
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
		PassCryptLaf.setup();
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
				new PCLogger(PassCrypt.class).error(e.getMessage());
			}
		}

		if(PassCryptDAO.leerTabla("master").isEmpty()) {
			MastPassDialog mpd = new MastPassDialog(UIPassCrypt.getFrame());
			mpd.setActionType(ActionType.INIT);
			mpd.setTitle("Inicializar contraseña maestra");
			mpd.getIntroduceLabel().setText("Nueva contraseña maestra:");
			mpd.setVisible(true);
			mpd.getMasterPassField().requestFocus();
		} else {
			java.awt.EventQueue.invokeLater(() -> {
				new UIPassCrypt().setVisible(true);
			});
		}
	}
}
