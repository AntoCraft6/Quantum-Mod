package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTag extends BlockTagsProvider {
    public BlockTag(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper exFileHelper) {
        super(output, lookupProvider, QuantumMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //TODO
    }
}
