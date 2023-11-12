package net.torocraft.flighthud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.Dimensions;
import net.torocraft.flighthud.FlightComputer;
import net.torocraft.flighthud.HudComponent;

public class ElytraHealthIndicator extends HudComponent {

  private final Dimensions dim;
  private final FlightComputer computer;

  public ElytraHealthIndicator(FlightComputer computer, Dimensions dim) {
    this.dim = dim;
    this.computer = computer;
  }

  @Override
  public void render(GuiGraphics ctx, float partial, Minecraft mc) {
    if (!CONFIG.elytra_showHealth || computer.elytraHealth == null) {
      return;
    }

    float x = dim.wScreen * CONFIG.elytra_x;
    float y = dim.hScreen * CONFIG.elytra_y;

    drawBox(ctx, x - 3.5f, y - 1.5f, 30, 10);
    drawFont(mc, ctx, "E", x - 10, y);
    drawFont(mc, ctx, String.format("%d", i(computer.elytraHealth)) + "%", x, y);
  }
}