package com.plr.flighthud.event.handler;

import com.plr.flighthud.config.SettingsConfig;
import com.plr.flighthud.key.KeyBindings;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ForgeClientEventHandler {
    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {
        if (KeyBindings.toggleMode.consumeClick()) SettingsConfig.toggle();
    }

    @SubscribeEvent
    public static void registerClientCmd(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands
                .literal("flighthud")
                .then(Commands.literal("toggle")
                        .executes(ctx -> {
                            SettingsConfig.toggle();
                            return 0;
                        })
                )
        );
    }
}
