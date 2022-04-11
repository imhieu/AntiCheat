package com.lighter.check.movement.fly;

import java.text.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.*;
import com.lighter.util.*;

public class FlyH extends MovementCheck
{
    Double lastYDiff;
    int lastBypassTick;
    
    private void lambda$handle$29(final Cuboid cuboid, final World world, final PlayerData playerData, final DecimalFormat decimalFormat, final double n, final double n2, final Player player, final PlayerLocation playerLocation, final int lastBypassTick) {
        if (cuboid.checkBlocks(world, FlyH::lambda$null$28)) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("pdiff:").append(decimalFormat.format(n)).append(",ydiff:").append(decimalFormat.format(n2))), false);
            if (OptionsManager.getInstance().isRollback() && this.violations >= 1.0) {
                playerData.cancelMove(player, playerLocation, world);
            }
        }
        else {
            this.violations -= Math.min(this.violations + 5.0, 0.025);
            this.lastBypassTick = lastBypassTick;
        }
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && !playerData.hasLag() && playerLocation2.getY() != playerLocation.getY() && playerData.getTeleportTicks() > playerData.getMaxPingTicks() * 2 + 10) {
            final double n2 = playerLocation2.getY() - playerLocation.getY();
            if (this.lastYDiff != null && Math.abs(n2 / 0.9800000190734863 + 0.08) > 1.0E-12 && Math.abs(n2 + 0.9800000190734863) > 1.0E-12 && Math.abs(n2 - 0.019999999105930755) > 1.0E-12) {
                final double abs = Math.abs((this.lastYDiff - 0.08) * 0.9800000190734863 - n2);
                if (!playerLocation2.getOnGround() && !playerLocation.getOnGround() && playerData.getVelocityTicks() > 20 + playerData.getMaxPingTicks() && playerData.getTeleportTicks() > playerData.getPingTicks() && playerData.getTotalTicks() - 10 > this.lastBypassTick) {
                    if (abs > 0.01) {
                        this.run(this::lambda$handle$29);
                    }
                    else {
                        this.violations -= Math.min(this.violations + 5.0, 0.025);
                    }
                }
            }
            this.lastYDiff = n2;
        }
        else {
            this.lastYDiff = null;
        }
    }
    
    public FlyH() {
        super(CheckType.FLYH, "H", "Fly", CheckVersion.RELEASE);
        this.lastYDiff = null;
        this.lastBypassTick = 0;
        this.violations = -5.0;
    }
    
    private static boolean lambda$null$28(final Material material) {
        return !MaterialList.BAD_VELOCITY.contains(material);
    }
}
