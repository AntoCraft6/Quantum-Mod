package net.antocraft.quantummod.recipe;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Recipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, QuantumMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<QuantumMachineRecipe>> QUANTUM_MACHINE_SERIALIZER = SERIALIZERS.register("quantum_process", () -> QuantumMachineRecipe.Serializer.INSTANCE);

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}