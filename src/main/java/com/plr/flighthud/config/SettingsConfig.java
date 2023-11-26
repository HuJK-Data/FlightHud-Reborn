package com.plr.flighthud.config;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SettingsConfig {
    public static final ModConfigSpec CFG;
    public static final ModConfigSpec.ConfigValue<DisplayMode> displayModeWhenNotFlying;
    public static final ModConfigSpec.ConfigValue<DisplayMode> displayModeWhenFlying;
    public static final ModConfigSpec.BooleanValue calculateRoll;
    public static final ModConfigSpec.DoubleValue rollTurningForce;
    public static final ModConfigSpec.DoubleValue rollSmoothing;
    public static final ModConfigSpec.IntValue hudRefreshInterval;

    static {
        final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("HUDSettings");
        displayModeWhenNotFlying = builder.defineEnum("displayModeWhenNotFlying", DisplayMode.NONE);
        displayModeWhenFlying = builder.defineEnum("displayModeWhenFlying", DisplayMode.FULL);
        calculateRoll = builder.define("calculateRoll", true);
        rollTurningForce = builder.defineInRange("rollTurningForce", 1.25, 0.0, Double.MAX_VALUE);
        rollSmoothing = builder.defineInRange("rollSmoothing", 0.85, 0.0, Double.MAX_VALUE);
        hudRefreshInterval = builder.defineInRange("hudRefreshInterval", 5, 1, 40);
        builder.pop();
        CFG = builder.build();
    }

    public enum DisplayMode {
        NONE, MIN, FULL;

        public static DisplayMode cycle(DisplayMode o) {
            return switch (o) {
                case NONE -> MIN;
                case MIN -> FULL;
                default -> NONE;
            };
        }
    }

    public static void toggle(boolean flying) {
        if (flying) displayModeWhenFlying.set(DisplayMode.cycle(displayModeWhenFlying.get()));
        else displayModeWhenNotFlying.set(DisplayMode.cycle(displayModeWhenNotFlying.get()));
        CFG.save();
    }

    public static void toggle() {
        final Player player = Minecraft.getInstance().player;
        if (player == null) return;
        toggle(player.isFallFlying());
    }
}
