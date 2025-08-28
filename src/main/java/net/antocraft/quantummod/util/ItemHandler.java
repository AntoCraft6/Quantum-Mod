package net.antocraft.quantummod.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class ItemHandler extends ItemStackHandler {
    public ItemHandler (int slot) {
        super(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot == 0) return true;
        return false;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (slot == 1) return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot == 0) return ItemStack.EMPTY;
        return super.extractItem(slot, amount, simulate);
    }
}