package net.torocraft.flighthud.common.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.api.HudComponent;
import net.torocraft.flighthud.common.Dimensions;
import net.torocraft.flighthud.common.FlightComputer;

public class AltitudeIndicator extends HudComponent {
    private final Dimensions dim;
    private final FlightComputer computer;

    public AltitudeIndicator(FlightComputer computer, Dimensions dim) {
        this.computer = computer;
        this.dim = dim;
    }

    @Override
    public void render(GuiGraphics ctx, float partial, Minecraft mc) {
        float top = dim.tFrame;
        float bottom = dim.bFrame;

        float right = dim.rFrame + 2;
        float left = dim.rFrame;

        float blocksPerPixel = 1;

        float floorOffset = i(computer.altitude * blocksPerPixel);
        float yFloor = dim.yMid - floorOffset;
        float xAltText = right + 5;
        if (CONFIG.altitude_showGroundInfo.get()) {
            drawHeightIndicator(mc, ctx, left - 1, dim.yMid, bottom - dim.yMid);
        }

        if (CONFIG.altitude_showReadout.get()) {
            drawFont(mc, ctx, String.format("%.0f", computer.altitude), xAltText, dim.yMid - 3);
            drawBox(ctx, xAltText - 2, dim.yMid - 4.5f, 28, 10);
        }

        if (CONFIG.altitude_showHeight.get()) {
            drawFont(mc, ctx, "G", xAltText - 10, bottom + 3);
            String heightText = computer.distanceFromGround == null ? "??"
                    : String.format("%d", i(computer.distanceFromGround));
            drawFont(mc, ctx, heightText, xAltText, bottom + 3);
            drawBox(ctx, xAltText - 2, bottom + 1.5f, 28, 10);
        }

        if (CONFIG.altitude_showScale.get()) {
            for (int i = 0; i < 1000; i = i + 10) {

                float y = (dim.hScreen - i * blocksPerPixel) - yFloor;
                if (y < top || y > (bottom - 5))
                    continue;

                if (i % 50 == 0) {
                    drawHorizontalLine(ctx, left, right + 2, y);
                    if (!CONFIG.altitude_showReadout.get() || y > dim.yMid + 7 || y < dim.yMid - 7) {
                        drawFont(mc, ctx, String.format("%d", i), xAltText, y - 3);
                    }
                }
                drawHorizontalLine(ctx, left, right, y);
            }
        }
    }

    private void drawHeightIndicator(Minecraft client, GuiGraphics ctx, float x, float top, float h) {
        if (client.level == null) return;
        float bottom = top + h;
        float blocksPerPixel = h / (client.level.getHeight() + 64f);
        float yAlt = bottom - i((computer.altitude + 64) * blocksPerPixel);
        float yFloor = bottom - i(64 * blocksPerPixel);

        drawVerticalLine(ctx, x, top - 1, bottom + 1);

        if (computer.groundLevel != null) {
            float yGroundLevel = bottom - (computer.groundLevel + 64f) * blocksPerPixel;
            fill(ctx, x - 3, yGroundLevel + 2, x, yFloor);
        }

        drawHorizontalLine(ctx, x - 6, x - 1, top);
        drawHorizontalLine(ctx, x - 6, x - 1, yFloor);
        drawHorizontalLine(ctx, x - 6, x - 1, bottom);

        drawPointer(ctx, x, yAlt, 90);
    }

}
