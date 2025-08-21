package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public class Language extends LanguageProvider {
    public Language(PackOutput output, String lang) {
        super(output, QuantumMod.MOD_ID, lang);
    }

    @Override
    protected void addTranslations() {

        this.add("creativetab." + QuantumMod.MOD_ID + ".machinery", "Quantum Machinery");
        this.add("creativetab." + QuantumMod.MOD_ID + ".refined", "Quantum Refined Material");

        for (int tier = 1; tier <= 3; tier++) {
            this.add("block." + QuantumMod.MOD_ID + ".quantum_machine_" + tier, "Quantum Machine (Tier " + tier + ")");
        }

        for (RefinedOverlay value : RefinedOverlay.values()) {
            for (int tier = 0; tier < 3; tier++) {
                this.add(value.overItem.iall.get(tier).get(), getNames(value.overItem.iall.get(tier).getId(), tier+1));
                this.add(value.overBlock.ball.get(tier).get(), getNames(value.overBlock.ball.get(tier).getId(), tier+1));
            }
        }
    }

    public String getNames(ResourceLocation res, int tier) {
        String name = res.getPath();
        return name.replaceAll("_", " ").replace("" + tier, "(Tier " + tier + ")");
    }
}