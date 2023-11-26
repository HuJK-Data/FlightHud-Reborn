package com.plr.flighthud;

import com.mojang.logging.LogUtils;
import com.plr.flighthud.compat.ebb.ElytraBombingCompat;
import com.plr.flighthud.config.HudConfig;
import com.plr.flighthud.config.SettingsConfig;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(FlightHud.MODID)
public class FlightHud {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "flighthud";

    public FlightHud() {
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.CLIENT, SettingsConfig.CFG, "flighthud.settings.toml");
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.CLIENT, HudConfig.Full.getInstance().CFG, "flighthud.hud.full.toml");
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.CLIENT, HudConfig.Min.getInstance().CFG, "flighthud.hud.min.toml");
        if (ModList.get().isLoaded("ebb")) ElytraBombingCompat.init();
    }
}
