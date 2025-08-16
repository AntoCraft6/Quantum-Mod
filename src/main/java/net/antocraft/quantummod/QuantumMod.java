package net.antocraft.quantummod;

import com.mojang.logging.LogUtils;
import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.antocraft.quantummod.recipe.Recipes;
import net.antocraft.quantummod.refined.RefinedOverlayEntry;
import net.antocraft.quantummod.screen.QuantumMachineMenuEntry;
import net.antocraft.quantummod.screen.QuantumMachineScreen1;
import net.antocraft.quantummod.screen.QuantumMachineScreen2;
import net.antocraft.quantummod.screen.QuantumMachineScreen3;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(QuantumMod.MOD_ID)
public class QuantumMod {
    public static final String MOD_ID = "quantummod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public QuantumMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        QuantumMachineEntry.register(bus);
        RefinedOverlayEntry.register(bus);
        QuantumMachineMenuEntry.register(bus);
        CreativeTab.register(bus);
        Recipes.register(bus);

        bus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(QuantumMachineMenuEntry.QUANTUM_MACHINE_MENU_1.get(), QuantumMachineScreen1::new);
            MenuScreens.register(QuantumMachineMenuEntry.QUANTUM_MACHINE_MENU_2.get(), QuantumMachineScreen2::new);
            MenuScreens.register(QuantumMachineMenuEntry.QUANTUM_MACHINE_MENU_3.get(), QuantumMachineScreen3::new);
        }
    }
}
