package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.CompositeModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockState extends BlockStateProvider {
    public BlockState(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, QuantumMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for (int tier = 0; tier < 3; tier++) {
            simpleBlockWithItem(getQuantumMachine(tier), new ModelFile.UncheckedModelFile(modLoc("block/quantum_machine_" + (tier+1))));
        }

        for (RefinedOverlay value : RefinedOverlay.values()) {
            var parent = value.overBlock.parent;
            var block = BuiltInRegistries.BLOCK.getOptional(parent);

            if (block.isEmpty() || block.get() == Blocks.AIR) {
                QuantumMod.LOGGER.error("missing block during datagen: {}", parent);
                continue;
            }

            ModelFile defaultBlock = models().getExistingFile(new ResourceLocation(parent.getNamespace() + ":block/" + parent.getPath()));

            for (int i = 0; i < 3; i++) {
                var each = value.overBlock.ball.get(i);
                var path = each.getId().getPath();

                BlockModelBuilder model = models()
                        .getBuilder(path)
                        .customLoader(CompositeModelBuilder::begin)
                        .child("solid", models().nested().renderType("minecraft:solid").parent(defaultBlock))
                        .child("translucent", models().nested().renderType("minecraft:translucent")
                                .parent(models().getExistingFile(new ResourceLocation("block/cube_all")))
                                .texture("all", new ResourceLocation(QuantumMod.MOD_ID, "block/tier_" + (i+1))))
                        .end().parent(defaultBlock);
                simpleBlockWithItem(each.get(), model);
            }
        }
    }

    private static Block getQuantumMachine (int tier) {
        List<Block> blocks = new ArrayList<>();
        QuantumMachineEntry.QUANTUM_MACHINE_BLOCK.getEntries().stream().map(Supplier::get).forEach(blocks::add);
        return blocks.get(tier);
    }

}
