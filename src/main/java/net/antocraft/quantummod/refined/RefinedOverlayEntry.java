package net.antocraft.quantummod.refined;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
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

    public RegistryObject<Item> i1;
    public RegistryObject<Item> i2;
    public RegistryObject<Item> i3;
    public List<RegistryObject<Item>> iall;

    public RegistryObject<Block> b1;
    public RegistryObject<Block> b2;
    public RegistryObject<Block> b3;
    public List<RegistryObject<Block>> ball;

    public RegistryObject<BlockItem> bi1;
    public RegistryObject<BlockItem> bi2;
    public RegistryObject<BlockItem> bi3;
    public List<RegistryObject<BlockItem>> biall;

    public RefinedOverlayEntry(ResourceLocation parent, @Nullable String registryOverride) {
        this.parent = parent;
        String path = registryOverride == null ? parent.getPath() : registryOverride;

            i1 = item(path, 1);
            i2 = item(path, 2);
            i3 = item(path, 3);
            iall = List.of(i1, i2, i3);

            b1 = block(path, 1);
            b2 = block(path, 2);
            b3 = block(path, 3);
            ball = List.of(b1, b2, b3);
            bi1 = blockItem(b1);
            bi2 = blockItem(b2);
            bi3 = blockItem(b3);
            biall = List.of(bi1, bi2, bi3);
    }

    private static final BlockBehaviour.Properties defProp = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(1.0F);

    public RegistryObject<Item> item(String path, int tier) {
        return REFINED_ITEMS.register(generateName(path, tier, ""), () -> new Item(new Item.Properties()));
    }

    public RegistryObject<Block> block(String path, int tier) {
        return REFINED_BLOCKS.register(generateName(path, tier, "_block"), () -> new Block(defProp));
    }

    private RegistryObject<BlockItem> blockItem(RegistryObject<Block> block) {
        return REFINED_ITEMS.register(block.getKey().location().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static String generateName(String path, int tier, String type) {
        return String.format(Locale.ROOT, "%s%s_%d", path, type, tier);
    }

    public static void register(IEventBus modEventBus) {
        RefinedOverlay.init();

        REFINED_ITEMS.register(modEventBus);
        REFINED_BLOCKS.register(modEventBus);
    }
}