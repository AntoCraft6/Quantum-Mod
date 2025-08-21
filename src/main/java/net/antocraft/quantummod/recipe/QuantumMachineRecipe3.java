package net.antocraft.quantummod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.antocraft.quantummod.QuantumMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

public class QuantumMachineRecipe3 implements Recipe<Container> {
    private final NonNullList<Ingredient> input;
    private final int inputSize;
    private final ItemStack output;
    private final int energy;
    private final ResourceLocation id;

    public QuantumMachineRecipe3(NonNullList<Ingredient> input, int inputSize, ItemStack output, int energy, ResourceLocation id) {
        this.input = input;
        this.inputSize = inputSize;
        this.output = output;
        this.energy = energy;
        this.id = id;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return input.get(0).test(container.getItem(0)) && inputSize <= container.getItem(0).getCount();
    }

    public NonNullList<Ingredient> getInput() {
        return input;
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getEnegy() {
        return energy;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static ItemStack itemStackFromJson(JsonObject stackObject) {
        return CraftingHelper.getItemStack(stackObject, true, true);
    }

    public static int intFromJson(JsonObject json, String type) {
        int value = -1;
        try {
            value = GsonHelper.getAsJsonObject(json, type).getAsInt();
        } catch (Exception ignored) {
            if (type.equals("energy")) value = 0;
            throw new UnsupportedOperationException();
        } finally {
            return value;
        }
    }

    public static class Type implements RecipeType<QuantumMachineRecipe3> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "quantum_process_3";
    }

    public static class Serializer implements RecipeSerializer<QuantumMachineRecipe3> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(QuantumMod.MOD_ID, "quantum_process_3");


        @Override
        public QuantumMachineRecipe3 fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "input");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
            int inputSize = 1;
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, getIngredientStack(ingredients.get(i).getAsJsonObject()));
                inputSize = GsonHelper.getAsInt(ingredients.get(i).getAsJsonObject(), "count");
            }

            int energy = intFromJson(json, "energy");

            return new QuantumMachineRecipe3(inputs, inputSize, output, energy, id);
        }

        private Ingredient getIngredientStack(JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(json);
            int count = 1;

            if (json.getAsJsonObject().has("count")) {
                count = GsonHelper.getAsInt(json, "count");
            }

            ItemStack itemStack = ingredient.getItems()[0];
            itemStack.setCount(count);

            return ingredient;
        }

        @Override
        public @Nullable QuantumMachineRecipe3 fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) inputs.set(i, Ingredient.fromNetwork(buffer));

            ItemStack output = buffer.readItem();

            int energy = buffer.readVarInt();

            int inputSize = buffer.readVarInt();

            return new QuantumMachineRecipe3(inputs, inputSize, output, energy, id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, QuantumMachineRecipe3 recipe) {
            buffer.writeInt(recipe.input.size());
            for (Ingredient ingredient : recipe.getIngredients()) ingredient.toNetwork(buffer);

            buffer.writeItem(recipe.output);

            buffer.writeVarInt(recipe.energy);

            buffer.writeVarInt(recipe.inputSize);
        }
    }
}