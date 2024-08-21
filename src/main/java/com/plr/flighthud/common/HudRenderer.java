package com.plr.flighthud.common;

import com.mojang.blaze3d.vertex.PoseStack;
import com.plr.flighthud.FlightHud;
import com.plr.flighthud.api.HudComponent;
import com.plr.flighthud.api.HudRegistry;
import com.plr.flighthud.api.IFlies;
import com.plr.flighthud.common.config.HudConfig;
import com.plr.flighthud.common.config.SettingsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class HudRenderer extends HudComponent {
    private int updateTick = 0;
    private final Dimensions dim = new Dimensions();
    private final FlightComputer computer = new FlightComputer();

    private final List<HudComponent> components = HudRegistry.getComponents()
            .stream()
            .map(p -> p.provide(computer, dim))
            .toList();

    private void setupConfig(Minecraft client) {
        if (client.player == null) return;
        HudComponent.CONFIG = switch (((IFlies) client.player).flighthud$isActuallyFlying() ?
                SettingsConfig.displayModeWhenFlying.get() :
                SettingsConfig.displayModeWhenNotFlying.get()) {
            case NONE -> null;
            case MIN -> HudConfig.Min.getInstance();
            case FULL -> HudConfig.Full.getInstance();
            default -> null;
        };
    }

    @Override
    public void render(GuiGraphics ctx, float partial, Minecraft client) {
        setupConfig(client);

        if (HudComponent.CONFIG == null) {
            return;
        }

        final PoseStack m = ctx.pose();
        try {
            m.pushPose();
            final float scale0 = HudComponent.CONFIG.scale.get().floatValue();
            if (scale0 != 1.0f) {
                float scale = 1 / scale0;
                m.scale(scale, scale, scale);
            }

            if (updateTick == 0) {
                dim.update(client);
            }
            updateTick++;
            updateTick %= SettingsConfig.hudRefreshInterval.get();

            for (HudComponent component : components) {
                component.render(ctx, partial, client);
            }
            m.popPose();
        } catch (Exception e) {
            FlightHud.LOGGER.error("Error occurred when rendering FlightHud", e);
        }
    }
}
