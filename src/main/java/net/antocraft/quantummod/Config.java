package net.antocraft.quantummod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = QuantumMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MAX_TIER = BUILDER
            .comment("The max tier available")
            .defineInRange("maxTier", 3, 0, 5); //TODO check how high it can go

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int maxTier;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        maxTier = MAX_TIER.get();
    }
}
