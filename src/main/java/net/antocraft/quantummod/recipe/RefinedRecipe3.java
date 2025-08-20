package net.antocraft.quantummod.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class RefinedRecipe3 implements RecipeBuilder {
    private final Item output;
    private final int countOut;
    private int energy;
    private List<Ingredient> input = Lists.newArrayList();
    private int countIn;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @Nullable
    private String group;

    public RefinedRecipe3(ItemLike output, int countOut) {
        this.output = output.asItem();
        this.countOut = countOut;
    }

    public static RefinedRecipe3 output(ItemLike output) {
        return new RefinedRecipe3(output, 1);
    }

    public static RefinedRecipe3 output(ItemLike output, int countOut) {
        return new RefinedRecipe3(output, countOut);
    }

    public RefinedRecipe3 input(TagKey<Item> tag) {
        return this.input(Ingredient.of(tag));
    }

    public RefinedRecipe3 input(ItemLike input) {
        return this.input(input, 1);
    }

    public RefinedRecipe3 input(ItemLike input, int countIn) {
        return this.input(Ingredient.of(input), countIn);
    }

    public RefinedRecipe3 input(Ingredient input) {
        return this.input(input, 1);
    }

    public RefinedRecipe3 input(Ingredient input, int countIn) {
        this.input.add(input);
        this.countIn = countIn;
        return this;
    }

    public RefinedRecipe3 energy(int energy) {
        this.energy = energy;
        return this;
    }

    public RefinedRecipe3 unlockedBy(String name, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    public RefinedRecipe3 group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public Item getResult() {
        return this.output;
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        consumer.accept(new Result(recipeId, this.output, this.countOut, this.group == null ? "" : this.group, this.energy, this.input, this.countIn, this.advancement, recipeId.withPrefix("recipes/refinement_3/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item output;
        private final int countOut;
        private final String group;
        private final int energy;
        private final List<Ingredient> input;
        private final int countIn;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item output, int countOut, String group, int energy, List<Ingredient> input, int countIn, Advancement.Builder advancement, ResourceLocation advancementId) {
            super();
            this.id = id;
            this.output = output;
            this.countOut = countOut;
            this.group = group;
            this.energy = energy;
            this.input = input;
            this.countIn = countIn;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        public void serializeRecipeData(JsonObject json) {
            if (!this.group.isEmpty()) json.addProperty("group", this.group);

            JsonArray jsonInput = new JsonArray();
            for (Ingredient input : this.input) jsonInput.add(getJsonInput(input, this.countIn));
            json.add("input", jsonInput);

            json.addProperty("energy", this.energy);

            JsonObject jsonOutput = new JsonObject();
            jsonOutput.addProperty("item", BuiltInRegistries.ITEM.getKey(this.output).toString());
            if (this.countOut > 1) jsonOutput.addProperty("count", this.countOut);
            json.add("output", jsonOutput);
        }

        public JsonObject getJsonInput(Ingredient ingredient, int size) {
            JsonObject jsonInput = new JsonObject();
            String ingredients = ingredient.toJson().toString();
            String remove1 = "{\"item\":\"";
            String remove2 = "\"}";
            ingredients = ingredients.replace(remove1, "");
            ingredients = ingredients.replace(remove2, "");
            jsonInput.addProperty("item", ingredients);
            jsonInput.addProperty("count", size);
            return jsonInput;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return QuantumMachineRecipe3.Serializer.INSTANCE;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}