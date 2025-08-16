package net.antocraft.quantummod.refined;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class RefinedOverlayItem extends Item {
    private String tier;

    public RefinedOverlayItem(Properties properties, int tier) {
        super(properties);
        // placeholder tier name
        if (tier==1) {
            this.tier = "basic";
        } else if (tier==2) {
            this.tier = "advanced";
        } else if (tier==3) {
            this.tier = "ultimate";
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip." + QuantumMod.MOD_ID + ".tier", tier));
    }
}
