package net.antocraft.quantummod.datagen.loot;

import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.antocraft.quantummod.refined.RefinedOverlayEntry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    protected void generate() {
        RefinedOverlayEntry.REFINED_BLOCKS.getEntries().forEach(block -> dropSelf(block.get()));
        QuantumMachineEntry.QUANTUM_MACHINE_BLOCK.getEntries().forEach(block -> dropSelf(block.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        RefinedOverlayEntry.REFINED_BLOCKS.getEntries().stream().map(Supplier::get).forEach(blocks::add);
        QuantumMachineEntry.QUANTUM_MACHINE_BLOCK.getEntries().stream().map(Supplier::get).forEach(blocks::add);
        return blocks;
    }
}
