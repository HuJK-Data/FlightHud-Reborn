package com.plr.flighthud.api;

import com.google.common.collect.Lists;
import com.plr.flighthud.core.components.*;

import java.util.List;

public final class HudRegistry {
    private static final List<HudComponent.Provider> components = Lists.newArrayList(
            FlightPathIndicator::new, (computer, dim) -> new LocationIndicator(dim),
            HeadingIndicator::new, SpeedIndicator::new,
            AltitudeIndicator::new, PitchIndicator::new,
            ElytraHealthIndicator::new
    );

    public static boolean addComponent(HudComponent.Provider component) {
        return components.add(component);
    }

    public static List<HudComponent.Provider> getComponents() {
        return components;
    }

    public static List<HudComponent.Provider> getComponentsCopied() {
        return Lists.newArrayList(components);
    }
}
