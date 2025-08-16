package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class Recipe extends RecipeProvider implements IConditionBuilder {
    public Recipe(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        for (RefinedOverlay value : RefinedOverlay.values()) {
            int i = 0;
            Item ingredient = ForgeRegistries.ITEMS.getValue(value.overItem.iall.get(i).getId());
            if (ingredient == null || ingredient == Items.AIR) {
                QuantumMod.LOGGER.error("missing block during datagen: {}", ingredient);
                continue;
            }
            for (var items : value.overBlock.biall) {
                ingredient = ForgeRegistries.ITEMS.getValue(value.overItem.iall.get(i).getId());
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ingredient, 9)
                        .requires(items.get())
                        .unlockedBy(getHasName(items.get()), has(items.get()))
                        .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("block_to_item/" + ingredient)));

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, items.get())
                        .define('#', ingredient)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .unlockedBy(getHasName(ingredient), has(ingredient))
                        .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("item_to_block/" + items.getId().getPath())));

                //TODO item and block to refined+1
                // need to make builders

                i++;
            }
        }
    }

}
