package net.antocraft.quantummod;

import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.antocraft.quantummod.refined.RefinedOverlay;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, QuantumMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MACHINERY_TAB = CREATIVE_MODE_TABS.register("machinery_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(QuantumMachineEntry.QUANTUM_MACHINE_1.get()))
                    .title(Component.translatable("creativetab." + QuantumMod.MOD_ID + ".machinery"))
                    .displayItems((parameters, output) -> {
                        output.accept(QuantumMachineEntry.QUANTUM_MACHINE_1.get());
                        output.accept(QuantumMachineEntry.QUANTUM_MACHINE_2.get());
                        output.accept(QuantumMachineEntry.QUANTUM_MACHINE_3.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> REFINED_TAB = CREATIVE_MODE_TABS.register("refined_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(QuantumMachineEntry.QUANTUM_MACHINE_2.get()))
                    .title(Component.translatable("creativetab." + QuantumMod.MOD_ID + ".refined"))
                    .displayItems((parameters, event) -> {
                        for (RefinedOverlay value : RefinedOverlay.values()) {
                            value.overItem.iall.stream().map(Supplier::get).map(Item::getDefaultInstance).forEach(event::accept);
                            value.overBlock.biall.stream().map(Supplier::get).map(Item::getDefaultInstance).forEach(event::accept);
                        }
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
