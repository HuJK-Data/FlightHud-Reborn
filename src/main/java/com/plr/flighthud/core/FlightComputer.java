package com.plr.flighthud.core;

import com.plr.flighthud.config.SettingsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FlightComputer {
    private static final float TICKS_PER_SECOND = 20;

    private float previousRollAngle = 0.0f;

    public Vec3 velocity;
    public float speed;
    public float pitch;
    public float heading;
    public Vec3 flightPath;
    public float flightPitch;
    public float flightHeading;
    public float roll;
    public float altitude;
    public Integer groundLevel;
    public Float distanceFromGround;
    public Float elytraHealth;

    public void update(Minecraft client, float partial) {
        if (client.player == null) return;
        velocity = client.player.getDeltaMovement();
        pitch = computePitch(client, partial);
        speed = computeSpeed(client);
        roll = computeRoll(client, partial);
        heading = computeHeading(client);
        altitude = computeAltitude(client);
        groundLevel = computeGroundLevel(client);
        distanceFromGround = computeDistanceFromGround(client, altitude, groundLevel);
        flightPitch = computeFlightPitch(velocity, pitch);
        flightHeading = computeFlightHeading(velocity, heading);
        elytraHealth = computeElytraHealth(client);
    }

    private Float computeElytraHealth(Minecraft client) {
        if (client.player == null) return null;
        final ItemStack stack = client.player.getItemBySlot(EquipmentSlot.CHEST);
        if (!stack.is(Items.ELYTRA)) return null;
        return (stack.getMaxDamage() - stack.getDamageValue()) * 100.0f / stack.getMaxDamage();
    }

    private float computeFlightPitch(Vec3 velocity, float pitch) {
        if (velocity.length() < 0.01) return pitch;
        return (float) (90 - Math.toDegrees(Math.acos(velocity.normalize().y)));
    }

    private float computeFlightHeading(Vec3 velocity, float heading) {
        if (velocity.length() < 0.01) return heading;
        return toHeading((float) Math.toDegrees(-Math.atan2(velocity.x, velocity.z)));
    }

    /**
     * Roll logic is from:
     * https://github.com/Jorbon/cool_elytra/blob/main/src/main/java/edu/jorbonism/cool_elytra/mixin/GameRendererMixin.java
     * to enable both mods will sync up when used together.
     */
    private float computeRoll(Minecraft client, float partial) {
        if (client.player == null) return 0;
        if (!SettingsConfig.calculateRoll.get()) {
            return 0;
        }

        float wingPower = SettingsConfig.rollTurningForce.get().floatValue();
        float rollSmoothing = SettingsConfig.rollSmoothing.get().floatValue();
        Vec3 facing = client.player.getForward();
        Vec3 velocity = client.player.getDeltaMovement();
        double horizontalFacing2 = facing.horizontalDistanceSqr();
        double horizontalSpeed2 = velocity.horizontalDistanceSqr();

        float rollAngle = 0.0f;

        if (horizontalFacing2 > 0.0D && horizontalSpeed2 > 0.0D) {
            double dot = (velocity.x * facing.x + velocity.z * facing.z) / Math.sqrt(horizontalFacing2 * horizontalSpeed2);
            dot = Mth.clamp(dot, -1, 1);
            double direction = Math.signum(velocity.x * facing.z - velocity.z * facing.x);
            rollAngle = (float) (Math.atan(Math.sqrt(horizontalSpeed2) * Math.acos(dot) * wingPower) * direction
                    * 57.29577951308);
        }

        rollAngle = (float) ((1.0 - rollSmoothing) * rollAngle + rollSmoothing * previousRollAngle);
        previousRollAngle = rollAngle;

        return rollAngle;
    }

    private float computePitch(Minecraft client, float parital) {
        if (client.player == null) return 0;
        return client.player.getViewXRot(parital) * -1;
    }

    private boolean isGround(BlockPos pos, Minecraft client) {
        if (client.level == null) return false;
        BlockState block = client.level.getBlockState(pos);
        return !block.isAir();
    }

    public BlockPos findGround(Minecraft client) {
        if (client.player == null) return null;
        BlockPos pos = client.player.blockPosition();
        while (pos.getY() >= 0) {
            pos = pos.below();
            if (isGround(pos, client)) {
                return pos;
            }
        }
        return null;
    }

    private Integer computeGroundLevel(Minecraft client) {
        BlockPos ground = findGround(client);
        return ground == null ? null : ground.getY();
    }

    private Float computeDistanceFromGround(Minecraft client, float altitude, Integer groundLevel) {
        if (groundLevel == null) return null;
        return Math.max(0f, altitude - groundLevel);
    }

    private float computeAltitude(Minecraft client) {
        if (client.player == null) return 0;
        return (float) client.player.position().y - 1;
    }

    private float computeHeading(Minecraft client) {
        if (client.player == null) return 0.0f;
        return toHeading(client.player.getYRot());
    }

    private float computeSpeed(Minecraft client) {
        float speed = 0.0f;
        var player = client.player;
        if (player == null) return speed;
        if (player.isPassenger()) {
            Entity entity = player.getVehicle();
            if (entity == null) return 0.0f;
            speed = (float) entity.getDeltaMovement().length() * TICKS_PER_SECOND;
        } else {
            speed = (float) player.getDeltaMovement().length() * TICKS_PER_SECOND;
        }
        return speed;
    }

    private float toHeading(float yawDegrees) {
        return (yawDegrees + 180) % 360;
    }
}
