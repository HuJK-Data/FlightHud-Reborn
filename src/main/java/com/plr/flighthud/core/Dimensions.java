package com.plr.flighthud.core;

import com.plr.flighthud.api.HudComponent;
import com.plr.flighthud.config.HudConfig;
import net.minecraft.client.Minecraft;

public class Dimensions {
    public float hScreen;
    public float wScreen;
    public float degreesPerPixel;
    public float xMid;
    public float yMid;

    public float wFrame;
    public float hFrame;
    public float lFrame;
    public float rFrame;
    public float tFrame;
    public float bFrame;

    public void update(Minecraft client) {
        if (HudComponent.CONFIG == null) {
            return;
        }
        HudConfig c = HudComponent.CONFIG;
        hScreen = client.getWindow().getGuiScaledHeight();
        wScreen = client.getWindow().getGuiScaledWidth();

        final float scale = c.scale.get().floatValue();
        if (scale != 1.0f && scale > 0) {
            hScreen = hScreen * scale;
            wScreen = wScreen * scale;
        }

        degreesPerPixel = hScreen / client.options.fov().get();
        xMid = wScreen / 2;
        yMid = hScreen / 2;

        wFrame = wScreen * c.width.get().floatValue();
        hFrame = hScreen * c.height.get().floatValue();

        lFrame = ((wScreen - wFrame) / 2) + c.xOffset.get().floatValue();
        rFrame = lFrame + wFrame;

        tFrame = ((hScreen - hFrame) / 2) + c.yOffset.get().floatValue();
        bFrame = tFrame + hFrame;
    }

}