package net.antocraft.quantummod.refined;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class RefinedOverlayEntry {
    public static final DeferredRegister<Item> REFINED_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QuantumMod.MOD_ID);
    public static final DeferredRegister<Block> REFINED_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QuantumMod.MOD_ID);

    public final ResourceLocation parent;

    public final RegistryObject<Item> i1;
    public final RegistryObject<Item> i2;
    public final RegistryObject<Item> i3;
    public final List<RegistryObject<Item>> iall;

    public RefinedOverlayEntry(ResourceLocation parent, @Nullable String registryOverride) {
        this.parent = parent;
        String path = registryOverride == null ? parent.getPath() : registryOverride;

        i1 = item(path, 1);
        i2 = item(path, 2);
        i3 = item(path, 3);
        iall = List.of(i1, i2, i3);

    }

    public static RegistryObject<Item> item(String path, int tier) {
        return REFINED_ITEMS.register(path + "_tier_" + tier, () -> new Item(new Item.Properties()));
    }

    private static String generateName(String path, int level) {
        return String.format(Locale.ROOT, "%s_%dx", path, level);
    }

    public static void register(IEventBus modEventBus) {
        RefinedOverlay.init();

        REFINED_ITEMS.register(modEventBus);
// TODO        REFINED_BLOCKS.register(modEventBus);
    }
}