package net.antocraft.quantummod.refined;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.antocraft.quantummod.QuantumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

    private boolean blockLoaded = false;

    public boolean isBlockLoaded() {
        return blockLoaded;
    }

    public RefinedOverlayEntry(ResourceLocation parent, String type) {
        this.parent = parent;
        String path = parent.getPath();
        Supplier<BlockBehaviour.Properties> properties = getProperties(parent);

        if (type.equals("item")) {
            i1 = item(path, 1);
            i2 = item(path, 2);
            i3 = item(path, 3);
            iall = List.of(i1, i2, i3);
        }
        if (type.equals("block")) {
            b1 = block(path, properties, 1);
            b2 = block(path, properties, 2);
            b3 = block(path, properties, 3);
            ball = List.of(b1, b2, b3);
            bi1 = blockItem(b1);
            bi2 = blockItem(b2);
            bi3 = blockItem(b3);
            biall = List.of(bi1, bi2, bi3);
        }
    }

    private static final BlockBehaviour.Properties defProp = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(1.0F);

    private Supplier<BlockBehaviour.Properties> getProperties(ResourceLocation parent) {
        return Suppliers.memoize(() -> {
            BlockBehaviour.Properties properties = defProp;
            if (ModList.get().isLoaded(parent.getNamespace())) {
                var block = ForgeRegistries.BLOCKS.getValue(parent);
                if (block != null) {
                    blockLoaded = true;
                    properties = BlockBehaviour.Properties.copy(block);
                } else {
                    QuantumMod.LOGGER.error("Trying to register an overlay for a block that doesn't exist! {}", parent);
                }
            }
            return properties;
        });
    }

    public RegistryObject<Item> item(String path, int tier) {
        return REFINED_ITEMS.register(generateName(path, tier), () -> new RefinedOverlayItem(new Item.Properties(), tier));
    }

    public RegistryObject<Block> block(String path, Supplier<BlockBehaviour.Properties> properties, int tier) {
        return REFINED_BLOCKS.register(generateName(path, tier), () -> new RefinedOverlayBlock(properties.get(), tier));
    }

    private RegistryObject<BlockItem> blockItem(RegistryObject<Block> block) {
        return REFINED_ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static String generateName(String path, int tier) {
        return String.format(Locale.ROOT, "%s_%d", path, tier);
    }

    public static void register(IEventBus modEventBus) {
        RefinedOverlay.init();

        REFINED_ITEMS.register(modEventBus);
        REFINED_BLOCKS.register(modEventBus);
    }
}