package net.torocraft.flighthud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.Dimensions;
import net.torocraft.flighthud.HudComponent;

public class LocationIndicator extends HudComponent {

  private final Dimensions dim;

  public LocationIndicator(Dimensions dim) {
    this.dim = dim;
  }

  @Override
  public void render(GuiGraphics ctx, float partial, Minecraft mc) {
    if (mc.player == null) return;
    if (!CONFIG.location_showReadout) {
      return;
    }

    float x = dim.wScreen * CONFIG.location_x;
    float y = dim.hScreen * CONFIG.location_y;

    int xLoc = mc.player.blockPosition().getX();
    int zLoc = mc.player.blockPosition().getZ();

    drawFont(mc, ctx, String.format("%d / %d", xLoc, zLoc), x, y);
  }
}
