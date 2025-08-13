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
//TODO type="block" giving error registries
    COAL(Mods.MINECRAFT, "item", "coal"),
    DIAMOND(Mods.MINECRAFT, "item", "diamond"),
    EMERALD(Mods.MINECRAFT, "item", "emerald"),
    LAPIS(Mods.MINECRAFT, "item", "lapis"), //tag: lapis_lazuli ???
    QUARTZ(Mods.MINECRAFT, "item", "quartz"),
    REDSTONE(Mods.MINECRAFT, "item", "redstone"),
    COPPER(Mods.MINECRAFT, "item", "copper"), //missing nugget
    GOLD(Mods.MINECRAFT, "item", "gold"),
    IRON(Mods.MINECRAFT, "item", "iron"),
    NETHERITE(Mods.MINECRAFT, "item", "netherite"); //missing nugget

    public final Mods mod;
    public final RefinedOverlayEntry over;

    RefinedOverlay(Mods mod, String type, String name) {
        this(mod, type, name, null);
    }

    RefinedOverlay(Mods mod, String type, String name, @Nullable String override) {
        this.mod = mod;
        this.over = new RefinedOverlayEntry(ResourceLocation.fromNamespaceAndPath(mod.toString(), name), type, override);
    }

    public static void init() {
        Map<Boolean, List<String>> mods = Arrays.stream(Mods.values()).map(Mods::toString).collect(Collectors.partitioningBy(ModList.get()::isLoaded));

        QuantumMod.LOGGER.info("Registering overlays for loaded mods: {}", mods.get(true));
        QuantumMod.LOGGER.info("Skipping overlays for absent mods: {}", mods.get(false));
    }
}