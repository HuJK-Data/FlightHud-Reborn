package com.plr.flighthud.mixin;

import com.plr.flighthud.common.HudRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class MixinGui {

    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private HudRenderer hud;

    @Inject(method = "render", at = @At("RETURN"))
    private void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        getHud().render(guiGraphics, deltaTracker.getRealtimeDeltaTicks(), minecraft);
    }

    @Unique
    private HudRenderer getHud() {
        if (hud == null) hud = new HudRenderer();
        return hud;
    }
}