package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.List;

public class Language extends LanguageProvider {
    public Language(PackOutput output, String lang) {
        super(output, QuantumMod.MOD_ID, lang);
    }

    @Override
    protected void addTranslations() {

        this.add("creativetab." + QuantumMod.MOD_ID + ".machinery", "Quantum Machinery");
        this.add("creativetab." + QuantumMod.MOD_ID + ".refined", "Quantum Refined Material");
        this.add("tooltip." + QuantumMod.MOD_ID + ".tier", "Quantum Tier: ");

        for (int tier = 1; tier <= 3; tier++) {
            this.add("block." + QuantumMod.MOD_ID + ".quantum_machine_" + tier, "Quantum Machine (Tier " + tier + ")");
        }

        for (RefinedOverlay value : RefinedOverlay.values()) {
            for (int tier = 1; tier <= 3; tier++) {
                this.add("item." + getNames(value.overItem.parent, tier), value.overItem.parent + " (Tier " + tier + ")");
                this.add("block." + getNames(value.overBlock.parent, tier), value.overItem.parent + " (Tier " + tier + ")");
            }
        }
    }

    private String getNames(ResourceLocation key, int tier) {
        String name = String.valueOf(key);
        return name.replace(":", ".").concat("_" + (tier+1));
    }
}