package net.torocraft.flighthud.common;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.FlightHud;
import net.torocraft.flighthud.api.HudComponent;
import net.torocraft.flighthud.api.HudRegistry;
import net.torocraft.flighthud.common.config.SettingsConfig.DisplayMode;

import java.util.List;

public class HudRenderer extends HudComponent {
  private int updateTick = 0;
  private final Dimensions dim = new Dimensions();
  private final FlightComputer computer = new FlightComputer();
  private static final String FULL = DisplayMode.FULL.toString();
  private static final String MIN = DisplayMode.MIN.toString();

    private final List<HudComponent> components = HudRegistry.getComponents()
            .stream()
            .map(p -> p.provide(computer, dim))
            .toList();

  private void setupConfig(Minecraft client) {
    HudComponent.CONFIG = null;
    if (client.player != null && client.player.isFallFlying()) {
      if (FlightHud.CONFIG_SETTINGS.displayModeWhenFlying.equals(FULL)) {
        HudComponent.CONFIG = FlightHud.CONFIG_FULL;
      } else if (FlightHud.CONFIG_SETTINGS.displayModeWhenFlying.equals(MIN)) {
        HudComponent.CONFIG = FlightHud.CONFIG_MIN;
      }
    } else {
      if (FlightHud.CONFIG_SETTINGS.displayModeWhenNotFlying.equals(FULL)) {
        HudComponent.CONFIG = FlightHud.CONFIG_FULL;
      } else if (FlightHud.CONFIG_SETTINGS.displayModeWhenNotFlying.equals(MIN)) {
        HudComponent.CONFIG = FlightHud.CONFIG_MIN;
      }
    }
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

      if (HudComponent.CONFIG.scale != 1d) {
        float scale = 1 / HudComponent.CONFIG.scale;
        m.scale(scale, scale, scale);
      }

      if (updateTick == 0) {
        computer.update(client, partial);
        dim.update(client);
      }
      updateTick++;
      updateTick %= FlightHud.CONFIG_SETTINGS.hudRefreshInterval;

      for (HudComponent component : components) {
        component.render(ctx, partial, client);
      }
      m.popPose();
    } catch (Exception e) {
      FlightHud.LOGGER.error("Error occurred when rendering FlightHud", e);
    }
  }
}
