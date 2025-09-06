package net.antocraft.quantummod.util;

import net.antocraft.quantummod.Config;

public class RecipeValue {
    int tier;
    public RecipeValue (int tier) {
        this.tier = tier;
    }

    public int energy = Config.quantumMachineEnergy;
    public int battery = Config.quantumMachineBattery;
    public int mult_2 = Config.quantumMachineMultiplier2;
    public int mult_3 = Config.quantumMachineMultiplier3;

    public int getEnergy() {
        if ( tier == 2 || tier == 3) energy *= mult_2;
        if ( tier == 3 ) energy *= mult_3;
        return this.energy;
    }

    public int getBattery() {
        if ( tier == 2 || tier == 3) battery *= mult_2;
        if ( tier == 3 ) battery *= mult_3;
        return this.battery;
    }
}
