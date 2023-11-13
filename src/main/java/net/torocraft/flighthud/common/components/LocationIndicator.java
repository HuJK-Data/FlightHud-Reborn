package net.torocraft.flighthud.common.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.api.HudComponent;
import net.torocraft.flighthud.common.Dimensions;

public class LocationIndicator extends HudComponent {

    private final Dimensions dim;

    public LocationIndicator(Dimensions dim) {
        this.dim = dim;
    }

    @Override
    public void render(GuiGraphics ctx, float partial, Minecraft mc) {
        if (mc.player == null) return;
        if (!CONFIG.location_showReadout.get()) {
            return;
        }

        float x = dim.wScreen * CONFIG.location_x.get().floatValue();
        float y = dim.hScreen * CONFIG.location_y.get().floatValue();

        int xLoc = mc.player.blockPosition().getX();
        int zLoc = mc.player.blockPosition().getZ();

        drawFont(mc, ctx, String.format("%d / %d", xLoc, zLoc), x, y);
    }
}
