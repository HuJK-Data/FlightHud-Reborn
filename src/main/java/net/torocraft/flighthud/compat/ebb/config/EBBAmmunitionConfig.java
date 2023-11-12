package net.torocraft.flighthud.compat.ebb.config;

import net.torocraft.flighthud.api.IConfig;

public class EBBAmmunitionConfig implements IConfig {
    public float ammunitionDisplayX = .6f;
    public float ammunitionDisplayY = .8f;

    @Override
    public void update() {

    }

    @Override
    public boolean shouldWatch() {
        return true;
    }
}
