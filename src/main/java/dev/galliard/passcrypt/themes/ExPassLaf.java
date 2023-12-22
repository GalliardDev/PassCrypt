package dev.galliard.passcrypt.themes;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class ExPassLaf
        extends FlatMacDarkLaf
{
    public static final String NAME = "expass";

    public static boolean setup() {
        return setup( new ExPassLaf() );
    }

    public static void installLafInfo() {
        installLafInfo( NAME, ExPassLaf.class );
    }

    @Override
    public String getName() {
        return NAME;
    }
}
