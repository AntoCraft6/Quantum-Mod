package net.antocraft.quantummod.refined;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class RefinedOverlayBlock extends Block {
    private String tier;

    public RefinedOverlayBlock(Properties properties, int tier) {
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
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter getter, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip." + QuantumMod.MOD_ID + ".tier", tier));
    }
}
