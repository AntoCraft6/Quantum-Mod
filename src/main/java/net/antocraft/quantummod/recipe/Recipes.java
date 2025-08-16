package net.antocraft.quantummod.recipe;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Recipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, QuantumMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<QuantumMachineRecipe1>> QUANTUM_MACHINE_SERIALIZER_1 = SERIALIZERS.register("quantum_process_1", () -> QuantumMachineRecipe1.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<QuantumMachineRecipe2>> QUANTUM_MACHINE_SERIALIZER_2 = SERIALIZERS.register("quantum_process_2", () -> QuantumMachineRecipe2.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<QuantumMachineRecipe3>> QUANTUM_MACHINE_SERIALIZER_3 = SERIALIZERS.register("quantum_process_3", () -> QuantumMachineRecipe3.Serializer.INSTANCE);

    public static void register(IEventBus modEventBus) {
        SERIALIZERS.register(modEventBus);
    }
}