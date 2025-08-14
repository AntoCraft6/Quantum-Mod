package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.antocraft.quantummod.refined.RefinedOverlayEntry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class BlockState extends BlockStateProvider {
    public BlockState(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, QuantumMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ModelFile defaultBlock = models().getExistingFile(ResourceLocation.withDefaultNamespace("block/block"));

        for (int tier = 0; tier < 3; tier++) {
            simpleBlockWithItem(getQuantumMachine(tier), new ModelFile.UncheckedModelFile(modLoc("block/quantum_machine_" + (tier+1))));
        }

        int entry = 0;
        for (RefinedOverlay value : RefinedOverlay.values()) {
            var parent = value.overBlock.parent;
            var block = BuiltInRegistries.BLOCK.getOptional(parent);

            if (block.isEmpty() || block.get() == Blocks.AIR) {
                QuantumMod.LOGGER.error("missing block during datagen: {}", parent);
                continue;
            }

            for (int i = 0; i < 3; i++) {
                var each = value.overBlock.ball.get(i);
                var path = each.getId().getPath();

                BlockModelBuilder model = models()
                        .getBuilder(path);

                String texture = blockTexture(parent).toString();

                if (value.equals(RefinedOverlay.QUARTZ)) {
                    model.element()
                            .allFaces(((direction, faceBuilder) -> {
                                if (direction == Direction.UP || direction == Direction.DOWN) {
                                    faceBuilder.texture("#end");
                                } else {
                                    faceBuilder.texture("#side");
                                }
                                faceBuilder.cullface(direction);
                            }));
                    model.texture("end", texture.concat("_top"));
                    model.texture("side", texture.concat("_side"));
                    texture = texture.concat("_side");
                } else {
                    model.element()
                            .cube("#all");
                    model.texture("all", texture);
                }
                model.element()
                        .cube("#over")
                        .end()
                        .renderType("minecraft:cutout")
                        .texture("over", String.format("%s:block/tier_%s", QuantumMod.MOD_ID, i+1))
                        .texture("particle", texture)
                        .parent(defaultBlock);

                simpleBlockWithItem(each.get(), model);
            }
            entry++;
        }
    }

    private static Block getQuantumMachine (int tier) {
        List<Block> blocks = new ArrayList<>();
        QuantumMachineEntry.QUANTUM_MACHINE_BLOCK.getEntries().stream().map(Supplier::get).forEach(blocks::add);
        return blocks.get(tier);
    }

    public ResourceLocation blockTexture(ResourceLocation key) {
        return ResourceLocation.fromNamespaceAndPath(key.getNamespace(), String.format("block/%s", key.getPath()));
    }

}
