package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = QuantumMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new Recipe(packOutput));
        generator.addProvider(event.includeServer(), LootTable.create(packOutput));

        generator.addProvider(event.includeClient(), new BlockState(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ItemModel(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new Language(packOutput, "en_us"));

        BlockTag blockTagGenerator = generator.addProvider(event.includeServer(), new BlockTag(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ItemTag(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
    }
}
