package com.lighter.check.movement.velocity;

import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import org.bukkit.util.*;

public class VelocityB extends MovementCheck
{
    private int lastBypassTicks;
    private double total;
    
    public VelocityB() {
        super(CheckType.VELOCITYB, "B", "Velocity", CheckVersion.RELEASE);
        this.lastBypassTicks = 0;
        this.total = 0.0;
        this.violations = -1.5;
    }
    
    private void lambda$handle$36(final Cuboid cuboid, final World world, final double n, final PlayerData playerData, final int lastBypassTicks) {
        if (cuboid.checkBlocks(world, VelocityB::lambda$null$35)) {
            this.total += 1.0 - n;
            if (this.total > 2.0) {
                this.total = 0.0;
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("%").append(n)), false);
            }
        }
        else {
            this.total -= Math.min(this.total, 0.03);
            this.lastBypassTicks = lastBypassTicks;
            this.violations -= Math.min(this.violations + 1.5, 0.01);
        }
    }
    
    private static boolean lambda$null$35(final Material material) {
        return material == Material.AIR;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getVelX() != 0.0 && playerData.getVelZ() != 0.0 && playerData.getHorizontalVelocityTicks() > playerData.getMoveTicks() && playerLocation2.getY() - playerLocation.getY() > 0.0) {
            final double hypot = MathUtil.hypot(playerData.getVelX(), playerData.getVelZ());
            if (playerData.getLastLastLocation().getOnGround() && playerLocation.getOnGround() && !playerLocation2.getOnGround() && MathUtil.onGround(playerLocation.getY()) && !MathUtil.onGround(playerLocation2.getY()) && hypot > 0.0 && playerData.getTotalTicks() - this.lastBypassTicks > 10) {
                final Vector vector = new Vector(playerData.getLastLastLocation().getX(), playerData.getLastLastLocation().getY(), playerData.getLastLastLocation().getZ());
                vector.subtract(new Vector(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ()));
                final PlayerLocation add = playerLocation2.add(vector.getX(), vector.getY(), vector.getZ());
                final double n2 = Math.max(MathUtil.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ()), MathUtil.hypot(add.getX() - playerLocation.getX(), add.getZ() - playerLocation.getZ())) / hypot;
                this.violations -= Math.min(this.violations, 0.01);
                if (n2 < 1.0) {
                    this.run(this::lambda$handle$36);
                }
                else {
                    this.total -= Math.min(this.total, 0.03);
                    this.violations -= Math.min(this.violations + 1.5, 0.01);
                }
            }
            playerData.setVelX(0.0);
            playerData.setVelZ(0.0);
        }
    }
}
