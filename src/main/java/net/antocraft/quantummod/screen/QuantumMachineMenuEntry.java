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


    public static final RegistryObject<MenuType<QuantumMachineMenu1>> QUANTUM_MACHINE_MENU_1 = registerMenuType("quantum_machine_1", QuantumMachineMenu1::new);
    public static final RegistryObject<MenuType<QuantumMachineMenu2>> QUANTUM_MACHINE_MENU_2 = registerMenuType("quantum_machine_2", QuantumMachineMenu2::new);
    public static final RegistryObject<MenuType<QuantumMachineMenu3>> QUANTUM_MACHINE_MENU_3 = registerMenuType("quantum_machine_3", QuantumMachineMenu3::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}