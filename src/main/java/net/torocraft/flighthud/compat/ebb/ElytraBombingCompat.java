package net.torocraft.flighthud.compat.ebb;

import net.torocraft.flighthud.FlightHud;
import net.torocraft.flighthud.api.HudRegistry;
import net.torocraft.flighthud.compat.ebb.components.AmmunitionIndicator;

public class ElytraBombingCompat {
    public static void init() {
        if (!HudRegistry.addComponent(((computer, dim) -> new AmmunitionIndicator(dim))))
            FlightHud.LOGGER.warn("Failed to add component to flight hud for elytra bombing.");
    }
}
