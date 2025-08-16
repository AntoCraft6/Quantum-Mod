package net.antocraft.quantummod.refined;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum RefinedOverlay {

    //
    COAL(Mods.MINECRAFT, "coal", "gem"),
    DIAMOND(Mods.MINECRAFT, "diamond", "gem"),
    EMERALD(Mods.MINECRAFT, "emerald", "gem"),
    QUARTZ(Mods.MINECRAFT, "quartz", "gem"),
    REDSTONE(Mods.MINECRAFT, "redstone", "gem"),
    LAPIS(Mods.MINECRAFT, "lapis", "gem"),

    // _ingot
    COPPER(Mods.MINECRAFT, "copper", "mineral"),
    GOLD(Mods.MINECRAFT, "gold", "mineral"),
    IRON(Mods.MINECRAFT, "iron", "mineral"),
    NETHERITE(Mods.MINECRAFT, "netherite", "mineral");

    public final Mods mod;
    public final RefinedOverlayEntry overItem;
    public final RefinedOverlayEntry overBlock;

    RefinedOverlay(Mods mod, String name, String type) {
        this.mod = mod;
        this.overBlock = new RefinedOverlayEntry(new ResourceLocation(mod.toString(), name+"_block"), "block");
        if (type.equals("mineral")) {
            name += "_ingot";
        }
        if (name.equals("lapis")) {
            name += "_lazuli";
        }
        this.overItem = new RefinedOverlayEntry(new ResourceLocation(mod.toString(), name), "item");
    }

    public static void init() {
        Map<Boolean, List<String>> mods = Arrays.stream(Mods.values()).map(Mods::toString).collect(Collectors.partitioningBy(ModList.get()::isLoaded));

        QuantumMod.LOGGER.info("Registering overlays for loaded mods: {}", mods.get(true));
        QuantumMod.LOGGER.info("Skipping overlays for absent mods: {}", mods.get(false));
    }
}