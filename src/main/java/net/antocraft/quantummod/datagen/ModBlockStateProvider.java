package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider  extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, QuantumMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(QuantumMachineEntry.QUANTUM_MACHINE_1.get(), new ModelFile.UncheckedModelFile(modLoc("block/quantum_machine_1")));
        simpleBlockWithItem(QuantumMachineEntry.QUANTUM_MACHINE_2.get(), new ModelFile.UncheckedModelFile(modLoc("block/quantum_machine_2")));
        simpleBlockWithItem(QuantumMachineEntry.QUANTUM_MACHINE_3.get(), new ModelFile.UncheckedModelFile(modLoc("block/quantum_machine_3")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
