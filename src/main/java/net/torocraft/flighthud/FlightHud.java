package net.torocraft.flighthud;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.torocraft.flighthud.config.HudConfig;
import net.torocraft.flighthud.config.SettingsConfig;
import net.torocraft.flighthud.config.loader.ConfigLoader;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

public class FlightHud implements ClientModInitializer {
  public static final Logger LOGGER = LogUtils.getLogger();
  public static final String MODID = "flighthud";

  public static SettingsConfig CONFIG_SETTINGS = new SettingsConfig();
  public static HudConfig CONFIG_MIN = new HudConfig();
  public static HudConfig CONFIG_FULL = new HudConfig();
  
  public static ConfigLoader<SettingsConfig> CONFIG_LOADER_SETTINGS = new ConfigLoader<>(
    new SettingsConfig(), 
    FlightHud.MODID + ".settings.json", 
    config -> FlightHud.CONFIG_SETTINGS = config);
    

  public static ConfigLoader<HudConfig> CONFIG_LOADER_FULL = new ConfigLoader<>(
    new HudConfig(), 
    FlightHud.MODID + ".full.json", 
    config -> FlightHud.CONFIG_FULL = config);
  

  public static ConfigLoader<HudConfig> CONFIG_LOADER_MIN = new ConfigLoader<>(
    HudConfig.getDefaultMinSettings(), 
    FlightHud.MODID + ".min.json", 
    config -> FlightHud.CONFIG_MIN = config);

  private static KeyMapping keyBinding;

  @Override
  public void onInitializeClient() {
    CONFIG_LOADER_SETTINGS.load();
    CONFIG_LOADER_FULL.load();
    CONFIG_LOADER_MIN.load();
    setupKeyCode();
    setupCommand();
  }

  private static void setupKeyCode() {
    keyBinding = new KeyMapping("key.flighthud.toggleDisplayMode", InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_GRAVE_ACCENT, "category.flighthud.toggleDisplayMode");

    KeyBindingHelper.registerKeyBinding(keyBinding);

    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      while (keyBinding.consumeClick()) {
        CONFIG_SETTINGS.toggleDisplayMode();
      }
    });
  }

  private static void setupCommand() {
    ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
      dispatcher.register(ClientCommandManager.literal("flighthud")
          .then(ClientCommandManager.literal("toggle").executes(new SwitchDisplayModeCommand())));
    });
  }
}
