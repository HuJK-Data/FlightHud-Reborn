package com.plr.flighthud.compat.ebb;

import com.plr.flighthud.FlightHud;
import com.plr.flighthud.api.HudRegistry;
import com.plr.flighthud.compat.ebb.components.AmmunitionIndicator;

public class ElytraBombingCompat {
    public static void init() {
        if (!HudRegistry.addComponent(((computer, dim) -> new AmmunitionIndicator(dim))))
            FlightHud.LOGGER.warn("Failed to add component to flight hud for elytra bombing.");
    }
}
