package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.antocraft.quantummod.refined.RefinedOverlayEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

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
            simpleBlockWithItem(getQuantumMachine(tier), new ModelFile.UncheckedModelFile(modLoc("block/quantum_machine_" + (tier + 1))));
        }

//        for (RefinedOverlay value : RefinedOverlay.values()) { //TODO
//            var parent = value.over.parent;
//            var block = BuiltInRegistries.BLOCK.getOptional(parent);
//
//            if (block.isEmpty() || block.get() == Blocks.AIR) {
//                QuantumMod.LOGGER.error("missing block during datagen: {}", parent);
//                continue;
//            }
//
//            for (int i = 0; i < 3; i++) {
//                var each = value.over.ball.get(i); //TODO
//                var path = each.getId().getPath();
//
//                BlockModelBuilder model = models()
//                        .getBuilder(path);
//
//                String texture = blockTexture(parent).toString();
//
//                model.element()
//                        .cube("#all");
//                model.texture("all", texture);
//                model.element()
//                        .cube("#over")
//                        .end()
//                        .renderType("minecraft:cutout")
//                        .texture("over", String.format("%s:block/tier_%s", QuantumMod.MOD_ID, i++))
//                        .texture("particle", "minecraft:block/bedrock") //TODO
//                        .parent(defaultBlock);
//            }
//        }
    }

    private static Block getQuantumMachine ( int tier){
        List<Block> blocks = new ArrayList<>();
        QuantumMachineEntry.QUANTUM_MACHINE_BLOCK.getEntries().stream().map(Supplier::get).forEach(blocks::add);
        return blocks.get(tier);
    }


    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
