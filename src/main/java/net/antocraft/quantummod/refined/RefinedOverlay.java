package net.antocraft.quantummod.refined;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum RefinedOverlay {

    // minecraft vanilla
    COAL(Mods.MINECRAFT, "coal"),
    COPPER(Mods.MINECRAFT, "copper_ingot"),
    DIAMOND(Mods.MINECRAFT, "diamond"),
    EMERALD(Mods.MINECRAFT, "emerald"),
    GOLD(Mods.MINECRAFT, "gold_ingot"),
    IRON(Mods.MINECRAFT, "iron_ingot"),
    LAPIS(Mods.MINECRAFT, "lapis"),
    NETHERITE(Mods.MINECRAFT, "netherite_ingot"),
    QUARTZ(Mods.MINECRAFT, "quartz"),
    REDSTONE(Mods.MINECRAFT, "redstone");

    public final Mods mod;
    public final RefinedOverlayEntry over;

    RefinedOverlay(Mods mod, String item) {
        this(mod, item, null);
    }

    RefinedOverlay(Mods mod, String item, @Nullable String override) {
        this.mod = mod;
        this.over = new RefinedOverlayEntry(ResourceLocation.fromNamespaceAndPath(mod.toString(), item), override);
    }

    public static void init() {
        Map<Boolean, List<String>> mods = Arrays.stream(Mods.values()).map(Mods::toString).collect(Collectors.partitioningBy(ModList.get()::isLoaded));

        QuantumMod.LOGGER.info("Registering overlays for loaded mods: {}", mods.get(true));
        QuantumMod.LOGGER.info("Skipping overlays for absent mods: {}", mods.get(false));
    }
}