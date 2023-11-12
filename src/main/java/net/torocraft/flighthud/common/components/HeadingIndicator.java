package net.torocraft.flighthud.common.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.api.HudComponent;
import net.torocraft.flighthud.common.Dimensions;
import net.torocraft.flighthud.common.FlightComputer;

public class HeadingIndicator extends HudComponent {

  private final Dimensions dim;
  private final FlightComputer computer;

  public HeadingIndicator(FlightComputer computer, Dimensions dim) {
    this.computer = computer;
    this.dim = dim;
  }

  @Override
  public void render(GuiGraphics ctx, float partial, Minecraft mc) {
    float left = dim.lFrame;
    float right = dim.rFrame;
    float top = dim.tFrame - 10;

    float yText = top - 7;
    float northOffset = computer.heading * dim.degreesPerPixel;
    float xNorth = dim.xMid - northOffset;

    if (CONFIG.heading_showReadout) {
      drawFont(mc, ctx, String.format("%03d", i(wrapHeading(computer.heading))), dim.xMid - 8, yText);
      drawBox(ctx, dim.xMid - 15, yText - 1.5f, 30, 10);
    }

    if (CONFIG.heading_showScale) {
      drawPointer(ctx, dim.xMid, top + 10, 0);
      for (int i = -540; i < 540; i = i + 5) {
        float x = (i * dim.degreesPerPixel) + xNorth;
        if (x < left || x > right)
          continue;

        if (i % 15 == 0) {
          if (i % 90 == 0) {
            drawFont(mc, ctx, headingToDirection(i), x - 2, yText + 10);
            drawFont(mc, ctx, headingToAxis(i), x - 8, yText + 20);
          } else {
            drawVerticalLine(ctx, x, top + 3, top + 10);
          }

          if (!CONFIG.heading_showReadout || x <= dim.xMid - 26 || x >= dim.xMid + 26) {
            drawFont(mc, ctx, String.format("%03d", i(wrapHeading(i))), x - 8, yText);
          }
        } else {
          drawVerticalLine(ctx, x, top + 6, top + 10);
        }
      }
    }
  }

  private String headingToDirection(int degrees) {
    return switch (i(wrapHeading(degrees))) {
      case 0, 360 -> "N";
      case 90 -> "E";
      case 180 -> "S";
      case 270 -> "W";
      default -> "";
    };
  }

  private String headingToAxis(int degrees) {
    return switch (i(wrapHeading(degrees))) {
      case 0, 360 -> "-Z";
      case 90 -> "+X";
      case 180 -> "+Z";
      case 270 -> "-X";
      default -> "";
    };
  }

}
