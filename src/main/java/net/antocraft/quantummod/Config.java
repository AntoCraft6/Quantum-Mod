package net.antocraft.quantummod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = QuantumMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue QUANTUM_MACHINE_BATTERY = SERVER_BUILDER
            .comment("Define the basic energy capacity for quantum machines.\n{min value is '0' | Default value is '100000'}")
            .defineInRange("quantumMachineBattery", 10, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue QUANTUM_MACHINE_ENERGY = SERVER_BUILDER
            .comment("Define the basic energy consumption for quantum material.\n{min value is '0' | Default value is '0'}")
            .defineInRange("quantumMachineEnergy", 10, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue MULTIPLIER_T2 = SERVER_BUILDER
            .comment("Define the multiplier for the energy consumption of quantum machine t2. { basic * quantumMachineMultiplier2 }\n{min value is '1' | Default value is '10'}")
            .defineInRange("quantumMachineMultiplier2", 10, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue MULTIPLIER_T3 = SERVER_BUILDER
            .comment("Define the multiplier for the energy consumption of quantum machine t3. { basic * quantumMachineMultiplier2 * quantumMachineMultiplier3 }\n{min value is '1' | Default value is '10'}")
            .defineInRange("quantumMachineMultiplier3", 10, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue TIME_1 = SERVER_BUILDER
            .comment("Define the recipe time for quantum machine t1.\n{min value is '0' | Default value is '100'}")
            .defineInRange("quantumMachineTime1", 100, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue TIME_2 = SERVER_BUILDER
            .comment("Define the recipe time for quantum machine t2.\n{min value is '0' | Default value is '100'}")
            .defineInRange("quantumMachineTime2", 100, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue TIME_3 = SERVER_BUILDER
            .comment("Define the recipe time for quantum machine t3.\n{min value is '0' | Default value is '100'}")
            .defineInRange("quantumMachineTime3", 100, 0, Integer.MAX_VALUE);

    public static int quantumMachineBattery;
    public static int quantumMachineEnergy;
    public static int quantumMachineMultiplier2;
    public static int quantumMachineMultiplier3;
    public static int quantumMachineTime1;
    public static int quantumMachineTime2;
    public static int quantumMachineTime3;

    static final ForgeConfigSpec SERVER = SERVER_BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        quantumMachineBattery = QUANTUM_MACHINE_BATTERY.get();
        quantumMachineEnergy = QUANTUM_MACHINE_ENERGY.get();
        quantumMachineMultiplier2 = MULTIPLIER_T2.get();
        quantumMachineMultiplier3 = MULTIPLIER_T3.get();
        quantumMachineTime1 = TIME_1.get();
        quantumMachineTime2 = TIME_2.get();
        quantumMachineTime3 = TIME_3.get();
    }
}
