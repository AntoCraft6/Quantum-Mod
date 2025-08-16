package net.antocraft.quantummod.screen;

import net.antocraft.quantummod.machines.QuantumMachineBlockEntity3;
import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;


public class QuantumMachineMenu3 extends AbstractContainerMenu {
    public final QuantumMachineBlockEntity3 blockEntity;
    private final Level level;
    public final ContainerData data;

    public QuantumMachineMenu3(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public QuantumMachineMenu3(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(QuantumMachineMenuEntry.QUANTUM_MACHINE_MENU_3.get(), containerId);
        checkContainerSize(inv, 2);
        blockEntity = ((QuantumMachineBlockEntity3) entity);
        this.level = inv.player.level();
        this.data = data;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 11));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 80, 59));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 26;

        return maxProgress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private static final int NO_PLAYER_INV = 0;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        ItemStack sourceStack = sourceSlot.getItem();
        if (!moveItemStackTo(sourceStack, NO_PLAYER_INV, NO_PLAYER_INV, false)) {
            return ItemStack.EMPTY;
        }
        return sourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, QuantumMachineEntry.QUANTUM_MACHINE_3.get());
    }


}