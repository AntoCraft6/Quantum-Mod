package net.antocraft.quantummod.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.recipe.*;
import net.antocraft.quantummod.screen.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIQuantumModPLUGIN implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(QuantumMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new QuantumMachineCategory1(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new QuantumMachineCategory2(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new QuantumMachineCategory3(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<QuantumMachineRecipe1> quantumMachineRecipe1 = recipeManager.getAllRecipesFor(QuantumMachineRecipe1.Type.INSTANCE);
        registration.addRecipes(QuantumMachineCategory1.QUANTUM_MACHINE_1_TYPE, quantumMachineRecipe1);

        List<QuantumMachineRecipe2> quantumMachineRecipe2 = recipeManager.getAllRecipesFor(QuantumMachineRecipe2.Type.INSTANCE);
        registration.addRecipes(QuantumMachineCategory2.QUANTUM_MACHINE_2_TYPE, quantumMachineRecipe2);

        List<QuantumMachineRecipe3> quantumMachineRecipe3 = recipeManager.getAllRecipesFor(QuantumMachineRecipe3.Type.INSTANCE);
        registration.addRecipes(QuantumMachineCategory3.QUANTUM_MACHINE_3_TYPE, quantumMachineRecipe3);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(QuantumMachineScreen1.class, 60, 30, 20, 30, QuantumMachineCategory1.QUANTUM_MACHINE_1_TYPE);
        registration.addRecipeClickArea(QuantumMachineScreen2.class, 60, 30, 20, 30, QuantumMachineCategory2.QUANTUM_MACHINE_2_TYPE);
        registration.addRecipeClickArea(QuantumMachineScreen3.class, 60, 30, 20, 30, QuantumMachineCategory3.QUANTUM_MACHINE_3_TYPE);
    }
}
