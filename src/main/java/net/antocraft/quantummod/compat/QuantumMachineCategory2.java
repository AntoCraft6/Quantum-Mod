package net.antocraft.quantummod.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.antocraft.quantummod.recipe.QuantumMachineRecipe2;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class QuantumMachineCategory2 implements IRecipeCategory<QuantumMachineRecipe2> {
    public static final ResourceLocation UID = new ResourceLocation(QuantumMod.MOD_ID, "quantum_machine_2");
    public static final ResourceLocation TEXTURE = new ResourceLocation(QuantumMod.MOD_ID, "textures/gui/quantum_machine_2_gui.png");

    public static final RecipeType<QuantumMachineRecipe2> QUANTUM_MACHINE_2_TYPE = new RecipeType<>(UID, QuantumMachineRecipe2.class);

    private final IDrawable background;
    private final IDrawable icon;

    public QuantumMachineCategory2(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(QuantumMachineEntry.QUANTUM_MACHINE_2.get()));
    }

    @Override
    public RecipeType<QuantumMachineRecipe2> getRecipeType() {
        return QUANTUM_MACHINE_2_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.quantummod.quantum_machine_2");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, QuantumMachineRecipe2 recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 80 ,11).addIngredients(recipe.getInput().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80 ,59).addItemStack(recipe.getResultItem(null));
    }
}
