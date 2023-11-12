package net.torocraft.flighthud.api;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.torocraft.flighthud.common.Dimensions;
import net.torocraft.flighthud.common.FlightComputer;
import net.torocraft.flighthud.common.config.HudConfig;
import org.joml.Matrix4f;

public abstract class HudComponent {

    public abstract void render(GuiGraphics ctx, float partial, Minecraft client);

    public static HudConfig CONFIG;

    protected int i(double d) {
        return (int) Math.round(d);
    }

    protected void drawPointer(GuiGraphics ctx, float x, float y, float rot) {
        final PoseStack m = ctx.pose();
        m.pushPose();
        m.translate(x, y, 0);
        m.mulPose(Axis.ZP.rotationDegrees(rot + 45));
        drawVerticalLine(ctx, 0, 0, 5);
        drawHorizontalLine(ctx, 0, 5, 0);
        m.popPose();
    }

    protected float wrapHeading(float degrees) {
        degrees = degrees % 360;
        while (degrees < 0) {
            degrees += 360;
        }
        return degrees;
    }

    protected void drawFont(Minecraft mc, GuiGraphics ctx, String s, float x, float y) {
        ctx.drawString(mc.font, s, (int) x, (int) y, CONFIG.color, false);
    }

    protected void drawRightAlignedFont(Minecraft mc, GuiGraphics ctx, String s, float x, float y) {
        int w = mc.font.width(s);
        drawFont(mc, ctx, s, x - w, y);
    }

    protected void drawBox(GuiGraphics ctx, float x, float y, float w, float h) {
        drawHorizontalLine(ctx, x, x + w, y);
        drawHorizontalLine(ctx, x, x + w, y + h);
        drawVerticalLine(ctx, x, y, y + h);
        drawVerticalLine(ctx, x + w, y, y + h);
    }

    protected void drawHorizontalLineDashed(GuiGraphics ctx, float x1, float x2, float y,
                                            int dashCount) {
        float width = x2 - x1;
        int segmentCount = dashCount * 2 - 1;
        float dashSize = width / segmentCount;
        for (int i = 0; i < segmentCount; i++) {
            if (i % 2 != 0) {
                continue;
            }
            float dx1 = i * dashSize + x1;
            float dx2;
            if (i == segmentCount - 1) {
                dx2 = x2;
            } else {
                dx2 = ((i + 1) * dashSize) + x1;
            }
            drawHorizontalLine(ctx, dx1, dx2, y);
        }
    }

    protected void drawHorizontalLine(GuiGraphics ctx, float x1, float x2, float y) {
        if (x2 < x1) {
            float i = x1;
            x1 = x2;
            x2 = i;
        }
        fill(ctx, x1 - CONFIG.halfThickness, y - CONFIG.halfThickness, x2 + CONFIG.halfThickness,
                y + CONFIG.halfThickness);
    }

    protected void drawVerticalLine(GuiGraphics ctx, float x, float y1, float y2) {
        if (y2 < y1) {
            float i = y1;
            y1 = y2;
            y2 = i;
        }

        fill(ctx, x - CONFIG.halfThickness, y1 + CONFIG.halfThickness, x + CONFIG.halfThickness,
                y2 - CONFIG.halfThickness);
    }

    public static void fill(GuiGraphics ctx, float x1, float y1, float x2, float y2) {
        float j;

        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }

        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }
        int color = CONFIG.color;
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        final Matrix4f matrix = ctx.pose().last().pose();
        bufferBuilder.vertex(matrix, x1, y2, 0.0F).color(r, g, b, alpha).endVertex();
        bufferBuilder.vertex(matrix, x2, y2, 0.0F).color(r, g, b, alpha).endVertex();
        bufferBuilder.vertex(matrix, x2, y1, 0.0F).color(r, g, b, alpha).endVertex();
        bufferBuilder.vertex(matrix, x1, y1, 0.0F).color(r, g, b, alpha).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public interface Provider {
        HudComponent provide(FlightComputer computer, Dimensions dim);
    }
}
