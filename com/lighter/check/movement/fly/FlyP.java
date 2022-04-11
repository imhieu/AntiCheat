package com.lighter.check.movement.fly;

import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import com.lighter.util.*;

public class FlyP extends MovementCheck
{
    private int vl;
    private int lastBypassTick;
    
    private void lambda$handle$61(final Cuboid cuboid, final World world, final Cuboid cuboid2, final PlayerData playerData, final double n, final PlayerLocation playerLocation, final int lastBypassTick) {
        if (cuboid.checkBlocks(world, FlyP::lambda$null$59) && cuboid2.checkBlocks(world, FlyP::lambda$null$60)) {
            if (++this.vl > 3) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(n).append(";toy:").append(playerLocation.getY())), false);
            }
            return;
        }
        this.violations -= Math.min(this.violations + 3.5, 0.025);
        this.lastBypassTick = lastBypassTick;
    }
    
    private static boolean lambda$null$59(final Material material) {
        return material == Material.AIR;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerLocation.getOnGround() && playerLocation2.getY() > playerLocation.getY() && playerData.getVelocityTicks() > (playerData.getPingTicks() + 1) * 4 && playerData.getTeleportTicks() > playerData.getMaxPingTicks() * 2 && playerData.isSpawnedIn() && !player.isFlying() && playerData.getTotalTicks() - 10 > this.lastBypassTick) {
            if (playerLocation2.getY() - playerLocation.getY() < 0.41999998688697815 && (playerLocation2.getY() - 0.41999998688697815) % 1.0 > 1.0E-15) {
                if (player.getMaximumNoDamageTicks() <= 13) {
                    return;
                }
                this.run(this::lambda$handle$61);
            }
            else {
                this.violations -= Math.min(this.violations + 3.5, 0.025);
                this.vl = 0;
            }
        }
    }
    
    public FlyP() {
        super(CheckType.FLYP, "P", "Fly", CheckVersion.RELEASE);
        this.lastBypassTick = -10;
        this.violations = -3.5;
    }
    
    private static boolean lambda$null$60(final Material material) {
        return !MaterialList.INVALID_SHAPE.contains(material) && !MaterialList.BAD_VELOCITY.contains(material);
    }
}
