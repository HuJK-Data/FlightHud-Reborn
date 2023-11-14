package com.plr.flighthud.common.config;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeConfigSpec;

public class SettingsConfig {
    public static final ForgeConfigSpec CFG;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> displayModeWhenNotFlying;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> displayModeWhenFlying;
    public static final ForgeConfigSpec.BooleanValue calculateRoll;
    public static final ForgeConfigSpec.DoubleValue rollTurningForce;
    public static final ForgeConfigSpec.DoubleValue rollSmoothing;
    public static final ForgeConfigSpec.IntValue hudRefreshInterval;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
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
