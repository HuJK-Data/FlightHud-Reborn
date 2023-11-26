package com.plr.flighthud.config;


import net.neoforged.neoforge.common.ModConfigSpec;

import java.awt.*;

public class HudConfig {
    public final ModConfigSpec CFG;
    public final ModConfigSpec.DoubleValue width;
    public final ModConfigSpec.DoubleValue height;
    public final ModConfigSpec.DoubleValue scale;
    public final ModConfigSpec.DoubleValue xOffset;
    public final ModConfigSpec.DoubleValue yOffset;
    public final ModConfigSpec.DoubleValue thickness;

    public final ModConfigSpec.IntValue color_red;
    public final ModConfigSpec.IntValue color_green;
    public final ModConfigSpec.IntValue color_blue;
    public final ModConfigSpec.IntValue color_alpha;

    public final ModConfigSpec.BooleanValue elytra_showHealth;
    public final ModConfigSpec.DoubleValue elytra_x;
    public final ModConfigSpec.DoubleValue elytra_y;

    public final ModConfigSpec.BooleanValue location_showReadout;
    public final ModConfigSpec.DoubleValue location_x;
    public final ModConfigSpec.DoubleValue location_y;

    public final ModConfigSpec.BooleanValue flightPath_show;

    public final ModConfigSpec.IntValue pitchLadder_degreesPerBar;
    public final ModConfigSpec.BooleanValue pitchLadder_showHorizon;
    public final ModConfigSpec.BooleanValue pitchLadder_showLadder;
    public final ModConfigSpec.DoubleValue pitchLadder_optimumGlideAngle;
    public final ModConfigSpec.DoubleValue pitchLadder_optimumClimbAngle;
    public final ModConfigSpec.BooleanValue pitchLadder_showRoll;
    public final ModConfigSpec.BooleanValue pitchLadder_reverseRoll;

    public final ModConfigSpec.BooleanValue speed_showScale;
    public final ModConfigSpec.BooleanValue speed_showReadout;

    public final ModConfigSpec.BooleanValue altitude_showScale;
    public final ModConfigSpec.BooleanValue altitude_showReadout;
    public final ModConfigSpec.BooleanValue altitude_showHeight;
    public final ModConfigSpec.BooleanValue altitude_showGroundInfo;

    public final ModConfigSpec.BooleanValue heading_showScale;
    public final ModConfigSpec.BooleanValue heading_showReadout;
    public final ModConfigSpec.BooleanValue ebb_show_tnt_count;
    public final ModConfigSpec.DoubleValue ebb_tnt_x;
    public final ModConfigSpec.DoubleValue ebb_tnt_y;

    private HudConfig(boolean isFull) {
        final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push(String.format("HudDisplay-%s", isFull ? "FULL" : "MIN"));
        builder.push("General");
        width = builder.defineInRange("width", 0.6, 0.1, 10.0);
        height = builder.defineInRange("height", 0.6, 0.1, 10.0);
        scale = builder.defineInRange("scale", 1.0, 0.1, 10.0);
        xOffset = builder.defineInRange("xOffset", 0.0f, -10.0, 10.0);
        yOffset = builder.defineInRange("yOffset", 0.0f, -10.0, 10.0);
        thickness = builder.defineInRange("thickness", 1.0, 0.1, 3.0);
        color_red = builder.defineInRange("color_red", 0, 0, 255);
        color_green = builder.defineInRange("color_green", 255, 0, 255);
        color_blue = builder.defineInRange("color_blue", 0, 0, 255);
        color_alpha = builder.defineInRange("color_alpha", 100, 0, 255);
        builder.pop();
        builder.push("ElytraHealth");
        elytra_showHealth = builder.define("elytra_showHealth", isFull);
        elytra_x = builder.defineInRange("elytra_x", 0.5, 0.0, 1.0);
        elytra_y = builder.defineInRange("elytra_y", 0.8, 0.0, 1.0);
        builder.pop();
        builder.push("Location");
        location_showReadout = builder.define("location_showReadout", true);
        location_x = builder.defineInRange("location_x", 0.2, 0.0, 1.0);
        location_y = builder.defineInRange("location_y", 0.8, 0.0, 1.0);
        builder.pop();
        builder.push("FlightPath");
        flightPath_show = builder.define("flightPath_show", isFull);
        builder.pop();
        builder.push("PitchLadder");
        pitchLadder_degreesPerBar = builder.defineInRange("pitchLadder_degreesPerBar", 20, 1, 20);
        pitchLadder_showHorizon = builder.define("pitchLadder_showHorizon", isFull);
        pitchLadder_showLadder = builder.define("pitchLadder_showLadder", isFull);
        pitchLadder_optimumGlideAngle = builder.defineInRange("pitchLadder_optimumGlideAngle", isFull ? -2.0f : 0.0f,
                -360.0f, 360.0f);
        pitchLadder_optimumClimbAngle = builder.defineInRange("pitchLadder_optimumClimbAngle", isFull ? 55.0f : 0.0f,
                -360.0f, 360.0f);
        pitchLadder_showRoll = builder.define("pitchLadder_showRoll", true);
        pitchLadder_reverseRoll = builder.define("pitchLadder_reverseRoll", false);
        builder.pop();
        builder.push("Speed");
        speed_showScale = builder.define("speed_showScale", isFull);
        speed_showReadout = builder.define("speed_showReadout", true);
        builder.pop();
        builder.push("Altitude");
        altitude_showScale = builder.define("altitude_showScale", isFull);
        altitude_showReadout = builder.define("altitude_showReadout", true);
        altitude_showHeight = builder.define("altitude_showHeight", isFull);
        altitude_showGroundInfo = builder.define("altitude_showGroundInfo", isFull);
        builder.pop();
        builder.push("Heading");
        heading_showScale = builder.define("heading_showScale", isFull);
        heading_showReadout = builder.define("heading_showReadout", true);
        builder.pop();
        builder.push("ElytraBombing");
        ebb_show_tnt_count = builder.define("ebb_show_tnt_count", true);
        ebb_tnt_x = builder.defineInRange("ebb_tnt_x", 0.6f, 0.0f, 1.0f);
        ebb_tnt_y = builder.defineInRange("ebb_tnt_y", 0.8f, 0.0f, 1.0f);
        builder.pop();
        builder.pop();
        CFG = builder.build();
    }

    public float getHalfThickness() {
        return thickness.get().floatValue() / 2;
    }

    public int getColorRGB() {
        return new Color(color_red.get(), color_green.get(), color_blue.get(), color_alpha.get()).getRGB();
    }

    public static class Min extends HudConfig {
        private static Min instance;

        private Min() {
            super(false);
        }

        public static Min getInstance() {
            if (instance == null) instance = new Min();
            return instance;
        }
    }

    public static class Full extends HudConfig {
        private static Full instance;

        private Full() {
            super(true);
        }

        public static Full getInstance() {
            if (instance == null) instance = new Full();
            return instance;
        }
    }
}
