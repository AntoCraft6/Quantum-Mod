package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.recipe.RefinedRecipe1;
import net.antocraft.quantummod.recipe.RefinedRecipe2;
import net.antocraft.quantummod.recipe.RefinedRecipe3;
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
                Item parentBlock;
                Item parentItem;

                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ingredient, 9)
                        .requires(items.get())
                        .unlockedBy(getHasName(items.get()), has(items.get()))
                        .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("block_to_item/" + ingredient)));

                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, items.get())
                        .define('#', ingredient)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .unlockedBy(getHasName(ingredient), has(ingredient))
                        .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("item_to_block/" + items.getId().getPath())));

                if (i == 0) {
                    parentBlock = ForgeRegistries.BLOCKS.getValue(value.overBlock.parent).asItem();
                    RefinedRecipe1.output(items.get(), 1)
                            .input(parentBlock, 8)
                            .energy(0)
                            .unlockedBy(getHasName(parentBlock), has(parentBlock))
                            .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("refinement_block_1/" + items.getId().getPath())));

                    parentItem = ForgeRegistries.ITEMS.getValue(value.overItem.parent);
                    RefinedRecipe1.output(ingredient, 1)
                            .input(parentItem, 8)
                            .energy(0)
                            .unlockedBy(getHasName(parentItem), has(parentItem))
                            .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("refinement_item_1/" + ingredient)));
                } else if (i == 1) {
                    parentBlock = ForgeRegistries.BLOCKS.getValue(value.overBlock.biall.get(i-1).getId()).asItem();
                    RefinedRecipe2.output(items.get(), 1)
                            .input(parentBlock, 8)
                            .energy(0)
                            .unlockedBy(getHasName(parentBlock), has(parentBlock))
                            .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("refinement_block_2/" + items.getId().getPath())));

                    parentItem = ForgeRegistries.ITEMS.getValue(value.overItem.iall.get(i-1).getId());
                    RefinedRecipe2.output(ingredient, 1)
                            .input(parentItem, 8)
                            .energy(0)
                            .unlockedBy(getHasName(parentItem), has(parentItem))
                            .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("refinement_item_2/" + ingredient)));
                } else {
                    parentBlock = ForgeRegistries.BLOCKS.getValue(value.overBlock.biall.get(i-1).getId()).asItem();
                    RefinedRecipe3.output(items.get(), 1)
                            .input(parentBlock, 8)
                            .energy(0)
                            .unlockedBy(getHasName(parentBlock), has(parentBlock))
                            .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("refinement_block_3/" + items.getId().getPath())));

                    parentItem = ForgeRegistries.ITEMS.getValue(value.overItem.iall.get(i-1).getId());
                    RefinedRecipe3.output(ingredient, 1)
                            .input(parentItem, 8)
                            .energy(0)
                            .unlockedBy(getHasName(parentItem), has(parentItem))
                            .save(writer, new ResourceLocation(QuantumMod.MOD_ID, ("refinement_item_3/" + ingredient)));
                }
                i++;
            }
        }
    }
}