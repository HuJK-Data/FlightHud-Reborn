package net.torocraft.flighthud.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.Dimensions;
import net.torocraft.flighthud.FlightComputer;
import net.torocraft.flighthud.HudComponent;

public class FlightPathIndicator extends HudComponent {
  private final Dimensions dim;
  private final FlightComputer computer;

  public FlightPathIndicator(FlightComputer computer, Dimensions dim) {
    this.computer = computer;
    this.dim = dim;
  }

  @Override
  public void render(GuiGraphics ctx, float partial, Minecraft client) {
    if (!CONFIG.flightPath_show) {
      return;
    }

    float deltaPitch = computer.pitch - computer.flightPitch;
    float deltaHeading = wrapHeading(computer.flightHeading) - wrapHeading(computer.heading);

    if (deltaHeading < -180) {
      deltaHeading += 360;
    }

    float y = dim.yMid;
    float x = dim.xMid;

    y += i(deltaPitch * dim.degreesPerPixel);
    x += i(deltaHeading * dim.degreesPerPixel);

    if (y < dim.tFrame || y > dim.bFrame || x < dim.lFrame || x > dim.rFrame) {
      return;
    }

    float l = x - 3;
    float r = x + 3;
    float t = y - 3 - CONFIG.halfThickness;
    float b = y + 3 - CONFIG.halfThickness;

    drawVerticalLine(ctx, l, t, b);
    drawVerticalLine(ctx, r, t, b);

    drawHorizontalLine(ctx, l, r, t);
    drawHorizontalLine(ctx, l, r, b);

    drawVerticalLine(ctx, x, t - 5, t);
    drawHorizontalLine(ctx, l - 4, l, y - CONFIG.halfThickness);
    drawHorizontalLine(ctx, r, r + 4, y - CONFIG.halfThickness);
  }
}
