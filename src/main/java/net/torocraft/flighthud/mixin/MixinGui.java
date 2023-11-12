package net.torocraft.flighthud.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.torocraft.flighthud.HudRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {

  @Shadow
  @Final
  private Minecraft minecraft;
  @Unique
  private final HudRenderer hud = new HudRenderer();

  @Inject(method = "render", at = @At("RETURN"))
  private void render(GuiGraphics context, float tickDelta, CallbackInfo ci) {
    hud.render(context, tickDelta, minecraft);
  }
}