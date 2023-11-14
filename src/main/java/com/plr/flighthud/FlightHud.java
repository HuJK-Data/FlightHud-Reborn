package com.plr.flighthud;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import com.plr.flighthud.common.config.HudConfig;
import com.plr.flighthud.common.config.SettingsConfig;
import com.plr.flighthud.compat.ebb.ElytraBombingCompat;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.fml.config.ModConfig;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

public class FlightHud implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "flighthud";

    private static KeyMapping keyBinding;

    @Override
    public void onInitializeClient() {
        ForgeConfigRegistry.INSTANCE.register(MODID,
                ModConfig.Type.CLIENT, SettingsConfig.CFG, "flighthud.settings.toml");
        ForgeConfigRegistry.INSTANCE.register(MODID,
                ModConfig.Type.CLIENT, HudConfig.Full.getInstance().CFG, "flighthud.hud.full.toml");
        ForgeConfigRegistry.INSTANCE.register(MODID,
                ModConfig.Type.CLIENT, HudConfig.Min.getInstance().CFG, "flighthud.hud.min.toml");
        setupKeyCode();
        setupCommand();
        if (FabricLoader.getInstance().isModLoaded("ebb")) ElytraBombingCompat.init();
    }

    private static void setupKeyCode() {
        keyBinding = new KeyMapping("key.flighthud.toggleDisplayMode", InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT, "category.flighthud.toggleDisplayMode");

        KeyBindingHelper.registerKeyBinding(keyBinding);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.consumeClick()) {
                SettingsConfig.toggle();
            }
        });
    }

    private static void setupCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager
                .literal("flighthud")
                .then(ClientCommandManager.literal("toggle")
                        .executes(ctx -> {
                            SettingsConfig.toggle();
                            return 0;
                        }))
        ));
    }
}
