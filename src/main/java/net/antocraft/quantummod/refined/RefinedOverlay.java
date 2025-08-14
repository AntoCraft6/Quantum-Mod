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

    COAL(Mods.MINECRAFT, "coal"),
    DIAMOND(Mods.MINECRAFT, "diamond"),
    EMERALD(Mods.MINECRAFT, "emerald"),
    LAPIS(Mods.MINECRAFT, "lapis"),
    QUARTZ(Mods.MINECRAFT, "quartz"),
    REDSTONE(Mods.MINECRAFT, "redstone"),
    COPPER(Mods.MINECRAFT, "copper"),
    GOLD(Mods.MINECRAFT, "gold"),
    IRON(Mods.MINECRAFT, "iron"),
    NETHERITE(Mods.MINECRAFT, "netherite");

    public final Mods mod;
    public final RefinedOverlayEntry overItem;
    public final RefinedOverlayEntry overBlock;

    RefinedOverlay(Mods mod, String name) {
        this(mod, name, null);
    }

    RefinedOverlay(Mods mod, String name, @Nullable String override) {
        this.mod = mod;
        this.overItem = new RefinedOverlayEntry(ResourceLocation.fromNamespaceAndPath(mod.toString(), name), "item", override);
        this.overBlock = new RefinedOverlayEntry(ResourceLocation.fromNamespaceAndPath(mod.toString(), name+"_block"), "block", override);
    }

    public static void init() {
        Map<Boolean, List<String>> mods = Arrays.stream(Mods.values()).map(Mods::toString).collect(Collectors.partitioningBy(ModList.get()::isLoaded));

        QuantumMod.LOGGER.info("Registering overlays for loaded mods: {}", mods.get(true));
        QuantumMod.LOGGER.info("Skipping overlays for absent mods: {}", mods.get(false));
    }
}