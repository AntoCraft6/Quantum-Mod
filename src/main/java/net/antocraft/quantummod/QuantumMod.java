package net.antocraft.quantummod;

import com.mojang.logging.LogUtils;
import net.antocraft.quantummod.machines.QuantumMachineEntry;
import net.antocraft.quantummod.recipe.Recipes;
import net.antocraft.quantummod.refined.RefinedOverlayEntry;
import net.antocraft.quantummod.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        QuantumMachineEntry.register(modEventBus);
        RefinedOverlayEntry.register(modEventBus);
        QuantumMachineMenuEntry.register(modEventBus);
        Recipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(QuantumMachineEntry.QUANTUM_MACHINE_1);
            event.accept(QuantumMachineEntry.QUANTUM_MACHINE_2);
            event.accept(QuantumMachineEntry.QUANTUM_MACHINE_3);
        }
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
