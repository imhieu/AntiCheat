package com.lighter.check.movement.fly;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import java.text.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class FlyB extends MovementCheck
{
    private int lastBypassTick;
    
    private static boolean lambda$null$31(final Material material) {
        return !MaterialList.INVALID_SHAPE.contains(material);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerLocation.getOnGround() && playerLocation2.getOnGround() && playerLocation.getY() != playerLocation2.getY() && !MathUtil.onGround(playerLocation.getY()) && !MathUtil.onGround(playerLocation2.getY()) && playerData.isSpawnedIn() && playerData.getTotalTicks() - 10 > this.lastBypassTick) {
            this.run(this::lambda$handle$32);
        }
        else {
            this.violations -= Math.min(this.violations + 2.0, 0.05);
        }
    }
    
    private void lambda$handle$32(final Cuboid cuboid, final World world, final PlayerData playerData, final DecimalFormat decimalFormat, final double n, final int lastBypassTick) {
        if (cuboid.checkBlocks(world, FlyB::lambda$null$31)) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(n))), false);
        }
        else {
            this.violations -= Math.min(this.violations + 2.0, 0.05);
            this.lastBypassTick = lastBypassTick;
        }
    }
    
    public FlyB() {
        super(CheckType.FLYB, "B", "Fly", CheckVersion.RELEASE);
        this.lastBypassTick = -10;
        this.violations = -2.0;
    }
}
