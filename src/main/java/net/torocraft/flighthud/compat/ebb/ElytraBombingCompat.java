package net.torocraft.flighthud.compat.ebb;

import net.torocraft.flighthud.FlightHud;
import net.torocraft.flighthud.api.HudRegistry;
import net.torocraft.flighthud.common.config.loader.ConfigLoader;
import net.torocraft.flighthud.compat.ebb.components.AmmunitionIndicator;
import net.torocraft.flighthud.compat.ebb.config.EBBAmmunitionConfig;

public class ElytraBombingCompat {
    public static EBBAmmunitionConfig BOMBING = new EBBAmmunitionConfig();

    public static final ConfigLoader<EBBAmmunitionConfig> CONFIG_LOADER_BOMBING = new ConfigLoader<>(
            new EBBAmmunitionConfig(),
            FlightHud.MODID + ".elytrabombing.json",
            config -> BOMBING = config);

    public static void init() {
        CONFIG_LOADER_BOMBING.load();
        HudRegistry.addComponent(((computer, dim) -> new AmmunitionIndicator(dim)));
    }
}
