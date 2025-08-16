package net.antocraft.quantummod.datagen;

import net.antocraft.quantummod.QuantumMod;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModel extends ItemModelProvider {
    public ItemModel(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, QuantumMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {

        for (RefinedOverlay value : RefinedOverlay.values()) {
            var parent = value.overItem.parent;
            var item = BuiltInRegistries.ITEM.getOptional(parent);

            if (item.isEmpty() || item.get() == Items.AIR) {
                QuantumMod.LOGGER.error("missing item during datagen: {}", parent);
                continue;
            }

            String texture = itemTexture(parent).toString();

            for (int i = 0; i < 3; i++) {
                var path = value.overItem.iall.get(i).getId().getPath();

                simpleItem(path, texture, i + 1);
            }

        }
    }

    private ItemModelBuilder simpleItem(String path, String texture, int tier) {
        return withExistingParent(path, new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(texture))
                .texture("layer1", new ResourceLocation(QuantumMod.MOD_ID + ":item/tier_" + tier));
    }

    public ResourceLocation itemTexture(ResourceLocation key) {
        return ResourceLocation.fromNamespaceAndPath(key.getNamespace(), String.format("item/%s", key.getPath()));
    }

}
