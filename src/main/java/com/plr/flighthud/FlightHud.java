package com.plr.flighthud;

import com.mojang.logging.LogUtils;
import com.plr.flighthud.common.config.HudConfig;
import com.plr.flighthud.common.config.SettingsConfig;
import com.plr.flighthud.compat.ebb.ElytraBombingCompat;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
