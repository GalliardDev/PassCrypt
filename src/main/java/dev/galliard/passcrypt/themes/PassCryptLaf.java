package dev.galliard.passcrypt.themes;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class PassCryptLaf
        extends FlatMacDarkLaf
{
    public static final String NAME = "expass";

    public static boolean setup() {
        return setup( new PassCryptLaf() );
    }

    public static void installLafInfo() {
        installLafInfo( NAME, PassCryptLaf.class );
    }

    @Override
    public String getName() {
        return NAME;
    }
}
