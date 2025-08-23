package net.antocraft.quantummod.screen;

import net.antocraft.quantummod.machines.QuantumMachineBlockEntity1;
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

public class QuantumMachineMenu1 extends AbstractContainerMenu {
    public final QuantumMachineBlockEntity1 blockEntity;
    private final Level level;
    public final ContainerData data;

    public QuantumMachineMenu1(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public QuantumMachineMenu1(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(QuantumMachineMenuEntry.QUANTUM_MACHINE_MENU_1.get(), containerId);
        checkContainerSize(inv, 2);
        blockEntity = ((QuantumMachineBlockEntity1) entity);
        this.level = inv.player.level();
        this.data = data;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 10000, 10000));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 10000, 10000));
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

    public int getScaledEnergy() {
        int energy = this.data.get(2);
        int maxEnergy = this.data.get(3);
        int energySize = 28;

        return maxEnergy != 0 ? energy * energySize / maxEnergy : 0;
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
                player, QuantumMachineEntry.QUANTUM_MACHINE_1.get());
    }


}