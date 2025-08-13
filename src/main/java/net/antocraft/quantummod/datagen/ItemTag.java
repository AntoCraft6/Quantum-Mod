package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ItemTag extends ItemTagsProvider {
    public ItemTag(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper exFileHelper) {
        super(output, lookupProvider, blockTags, QuantumMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //TODO
    }
}
