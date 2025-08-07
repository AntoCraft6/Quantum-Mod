package net.antocraft.quantummod.machines;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class QuantumMachineEntry {
    public static final DeferredRegister<Item> QUANTUM_MACHINE_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, QuantumMod.MOD_ID);
    public static final DeferredRegister<Block> QUANTUM_MACHINE_BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, QuantumMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> QUANTUM_MACHINE_BE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, QuantumMod.MOD_ID);


    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = QUANTUM_MACHINE_BLOCK.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return QUANTUM_MACHINE_ITEM.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static RegistryObject<Block> QUANTUM_MACHINE_1 = registerBlock("quantum_machine_1", () -> new QuantumMachineBlock1(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static RegistryObject<Block> QUANTUM_MACHINE_2 = registerBlock("quantum_machine_2", () -> new QuantumMachineBlock2(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static RegistryObject<Block> QUANTUM_MACHINE_3 = registerBlock("quantum_machine_3", () -> new QuantumMachineBlock3(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<BlockEntityType<QuantumMachineBlockEntity1>> QUANTUM_MACHINE_BE_1 = QUANTUM_MACHINE_BE.register("quantum_machine_be_1", () -> BlockEntityType.Builder.of(QuantumMachineBlockEntity1::new, QUANTUM_MACHINE_1.get()).build(null));
    public static final RegistryObject<BlockEntityType<QuantumMachineBlockEntity1>> QUANTUM_MACHINE_BE_2 = QUANTUM_MACHINE_BE.register("quantum_machine_be_2", () -> BlockEntityType.Builder.of(QuantumMachineBlockEntity1::new, QUANTUM_MACHINE_2.get()).build(null));
    public static final RegistryObject<BlockEntityType<QuantumMachineBlockEntity1>> QUANTUM_MACHINE_BE_3 = QUANTUM_MACHINE_BE.register("quantum_machine_be_3", () -> BlockEntityType.Builder.of(QuantumMachineBlockEntity1::new, QUANTUM_MACHINE_3.get()).build(null));

    public static void register(IEventBus modEventBus) {
        QUANTUM_MACHINE_ITEM.register(modEventBus);
        QUANTUM_MACHINE_BLOCK.register(modEventBus);
        QUANTUM_MACHINE_BE.register(modEventBus);
    }
}