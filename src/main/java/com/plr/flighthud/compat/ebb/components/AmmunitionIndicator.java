package com.plr.flighthud.compat.ebb.components;

import com.google.common.collect.ImmutableList;
import com.plr.flighthud.api.HudComponent;
import com.plr.flighthud.core.Dimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

public class AmmunitionIndicator extends HudComponent {
    private final Dimensions dim;
    private static final List<Item> igniters = ImmutableList.of(Items.FLINT_AND_STEEL, Items.FIRE_CHARGE);

    public AmmunitionIndicator(Dimensions dim) {
        this.dim = dim;
    }

    @Override
    public void render(GuiGraphics ctx, float partial, Minecraft mc) {
        final var player = mc.player;
        if (player == null) return;
        if (!igniters.contains(player.getItemInHand(InteractionHand.MAIN_HAND).getItem())) return;
        final int tntCount = player.getInventory().countItem(Items.TNT);
        final float cd = player.getCooldowns().getCooldownPercent(Items.TNT, partial);
        final float x = dim.wScreen * CONFIG.ebb_tnt_x.get().floatValue();
        final float y = dim.hScreen * CONFIG.ebb_tnt_y.get().floatValue();
        drawBox(ctx, x - 3.5f, y - 1.5f, 30, 10);
        drawFont(mc, ctx, "T", x - 10, y);
        drawFont(mc, ctx, String.valueOf(tntCount), x, y);
        drawFont(mc, ctx, String.format("%.1f", 100 * (1.0F - cd)) + "%", x + 30, y);
    }
}
