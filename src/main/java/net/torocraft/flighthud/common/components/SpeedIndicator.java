package net.torocraft.flighthud.common.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.api.HudComponent;
import net.torocraft.flighthud.common.Dimensions;
import net.torocraft.flighthud.common.FlightComputer;

public class SpeedIndicator extends HudComponent {
  private final Dimensions dim;
  private final FlightComputer computer;

  public SpeedIndicator(FlightComputer computer, Dimensions dim) {
    this.computer = computer;
    this.dim = dim;
  }

  @Override
  public void render(GuiGraphics ctx, float partial, Minecraft mc) {
    float top = dim.tFrame;
    float bottom = dim.bFrame;

    float left = dim.lFrame - 2;
    float right = dim.lFrame;
    float unitPerPixel = 30;

    float floorOffset = computer.speed * unitPerPixel;
    float yFloor = dim.yMid - floorOffset;

    float xSpeedText = left - 5;

    if (CONFIG.speed_showReadout) {
      drawRightAlignedFont(mc, ctx, String.format("%.2f", computer.speed), xSpeedText, dim.yMid - 3);
      drawBox(ctx, xSpeedText - 29.5f, dim.yMid - 4.5f, 30, 10);
    }


    if (CONFIG.speed_showScale) {
      for (float i = 0; i <= 100; i = i + 0.25f) {
        float y = dim.hScreen - i * unitPerPixel - yFloor;
        if (y < top || y > (bottom - 5))
          continue;

        if (i % 1 == 0) {
          drawHorizontalLine(ctx, left - 2, right, y);
          if (!CONFIG.speed_showReadout || y > dim.yMid + 7 || y < dim.yMid - 7) {
            drawRightAlignedFont(mc, ctx, String.format("%.0f", i), xSpeedText, y - 3);
          }
        }
        drawHorizontalLine(ctx, left, right, y);
      }
    }
  }
}