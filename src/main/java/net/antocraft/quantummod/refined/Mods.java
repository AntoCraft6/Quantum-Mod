package net.antocraft.quantummod.refined;

import java.util.Locale;

public enum Mods {
    MINECRAFT;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }
}