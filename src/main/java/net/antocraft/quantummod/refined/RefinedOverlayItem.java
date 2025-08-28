package net.antocraft.quantummod.refined;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RefinedOverlayItem extends Item {
    private final int tier;

    public RefinedOverlayItem(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("Quantum Tier: %s", tier));
    }
}
