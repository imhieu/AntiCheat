package com.lighter.check.movement.fly;

import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import org.bukkit.*;
import com.lighter.util.*;
import java.util.*;
import org.bukkit.entity.*;
import com.lighter.data.*;

public class FlyF extends MovementCheck
{
    private int lastBypassTick;
    private Double lastYChange;
    private int threshold;
    private final List<Double> whitelistedValues;
    
    private void lambda$handle$55(final Cuboid cuboid, final World world, final Cuboid cuboid2, final double n, final PlayerData playerData, final DecimalFormat decimalFormat, final int lastBypassTick) {
        if (!cuboid.checkBlocks(world, FlyF::lambda$null$53) || !cuboid2.checkBlocks(world, FlyF::lambda$null$54)) {
            this.threshold = 0;
            this.violations -= Math.min(this.violations + 5.0, 0.01);
            this.lastBypassTick = lastBypassTick;
            return;
        }
        if (n % 0.05 == 0.0) {
            ++this.threshold;
        }
        if (this.threshold++ <= 1) {
            return;
        }
        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ychange:").append(decimalFormat.format(n))), false);
    }
    
    private static boolean lambda$null$53(final Material material) {
        return !MaterialList.BAD_VELOCITY.contains(material) && !MaterialList.STAIRS.contains(material);
    }
    
    public FlyF() {
        super(CheckType.FLYF, "F", "Fly", CheckVersion.RELEASE);
        this.lastYChange = null;
        this.threshold = 0;
        this.lastBypassTick = -10;
        this.whitelistedValues = Arrays.asList(0.15523200451660202, 0.23052736891296366);
        this.violations = -5.0;
    }
    
    private static boolean lambda$null$54(final Material material) {
        return material == Material.AIR;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.isFlying() && !playerLocation2.getOnGround() && !playerLocation.getOnGround() && playerData.getTotalTicks() - 10 > this.lastBypassTick && playerData.getTeleportTicks() > playerData.getPingTicks() && playerData.getSteerTicks() > playerData.getPingTicks() && playerData.isSpawnedIn()) {
            final double abs = Math.abs(playerLocation.getY() - playerLocation2.getY());
            if (this.whitelistedValues.contains(abs)) {
                return;
            }
            if (player.getNoDamageTicks() <= 13) {
                return;
            }
            if (player.getMaximumNoDamageTicks() <= 13) {
                return;
            }
            if (this.lastYChange != null && abs > 0.0) {
                if (abs == this.lastYChange && (abs < 0.098 || abs > 0.09800001)) {
                    this.run(this::lambda$handle$55);
                }
                else {
                    this.violations -= Math.min(this.violations + 5.0, 0.01);
                    this.threshold = 0;
                }
            }
            this.lastYChange = abs;
        }
    }
}
