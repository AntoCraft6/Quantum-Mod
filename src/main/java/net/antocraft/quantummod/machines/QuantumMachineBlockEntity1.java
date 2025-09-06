package net.antocraft.quantummod.machines;

import net.antocraft.quantummod.Config;
import net.antocraft.quantummod.recipe.QuantumMachineRecipe1;
import net.antocraft.quantummod.screen.QuantumMachineMenu1;
import net.antocraft.quantummod.util.ItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class QuantumMachineBlockEntity1 extends BlockEntity implements MenuProvider {
    private final ItemHandler itemHandler = new ItemHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private final EnergyStorage energyStorage = new EnergyStorage(Config.quantumMachineBattery);

    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.of(() -> itemHandler);
    private LazyOptional<EnergyStorage> LazyEnergyHandler = LazyOptional.of(() -> energyStorage);

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    public QuantumMachineBlockEntity1(BlockPos pos, BlockState blockState) {
        super(QuantumMachineEntry.QUANTUM_MACHINE_BE_1.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    case 2 -> energyStorage.getEnergyStored();
                    case 3 -> energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                    case 2 -> energyStorage.getEnergyStored();
                    case 3 -> energyStorage.getMaxEnergyStored();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStack getRenderStack() {
        if (itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()) {
            return itemHandler.getStackInSlot(INPUT_SLOT);
        } else {
            return itemHandler.getStackInSlot(OUTPUT_SLOT);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return LazyItemHandler.cast();
        if (cap == ForgeCapabilities.ENERGY) return LazyEnergyHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        LazyItemHandler = LazyOptional.of(() -> itemHandler);
        LazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        LazyItemHandler.invalidate();
        LazyEnergyHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.quantummod.quantum_machine_1");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new QuantumMachineMenu1(containerId, playerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());
        tag.putInt("quantum_machine_1.progress", progress);

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        energyStorage.deserializeNBT(tag.getCompound("energy"));
        progress = tag.getInt("quantum_machine_1.progress");
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (hasRecipe()) {
            if (hasEnergy()) {
                increaseCraftingProgress();
                setChanged(level, pos, state);

                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }
            } else {
                decreaseCraftingProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<QuantumMachineRecipe1> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);
        int inputSize = recipe.get().getInputSize();

        this.itemHandler.extractItem(INPUT_SLOT, inputSize, false, true);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {
        Optional<QuantumMachineRecipe1> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean hasEnergy() {
        Optional<QuantumMachineRecipe1> recipe = getCurrentRecipe();
        int energy = recipe.get().getEnergy();

        if (energy > 0) {
            return energyStorage.extractEnergy(energy, true) >= energy;
        } else return true;
    }

    private Optional<QuantumMachineRecipe1> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(QuantumMachineRecipe1.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        Optional<QuantumMachineRecipe1> recipe = getCurrentRecipe();
        int energy = recipe.get().getEnergy();

        progress++;
        if (energy>0) energyStorage.extractEnergy(energy, false);
        if (energy<0) energyStorage.receiveEnergy(-energy, false);
    }

    private void decreaseCraftingProgress() {
        progress--;
    } //TODO change to failed recipe

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
