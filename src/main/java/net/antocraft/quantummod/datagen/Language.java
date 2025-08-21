package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class Language extends LanguageProvider {
    public Language(PackOutput output, String lang) {
        super(output, QuantumMod.MOD_ID, lang);
    }

    @Override
    protected void addTranslations() {
        for (RefinedOverlay value : RefinedOverlay.values()) {
            this.add();
        }
    }
}