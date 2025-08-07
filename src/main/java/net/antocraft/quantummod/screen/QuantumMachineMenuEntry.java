package net.antocraft.quantummod.screen;

import net.antocraft.quantummod.QuantumMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class QuantumMachineMenuEntry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, QuantumMod.MOD_ID);


    public static final RegistryObject<MenuType<QuantumMachineMenu>> QUANTUM_MACHINE_MENU = registerMenuType("quantum_machine", QuantumMachineMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}