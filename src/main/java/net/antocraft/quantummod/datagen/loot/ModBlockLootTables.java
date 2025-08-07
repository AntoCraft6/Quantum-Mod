package net.antocraft.quantummod.datagen.loot;

import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    protected void generate() {
        this.dropSelf(QuantumMachineEntry.QUANTUM_MACHINE_1.get());
        this.dropSelf(QuantumMachineEntry.QUANTUM_MACHINE_2.get());
        this.dropSelf(QuantumMachineEntry.QUANTUM_MACHINE_3.get());
    }
}
