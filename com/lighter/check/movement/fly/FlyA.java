package com.lighter.check.movement.fly;

import java.text.*;
import com.lighter.data.*;
import org.bukkit.entity.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.*;
import com.lighter.util.*;

public class FlyA extends MovementCheck
{
    private int lastBypassTick;
    
    private void lambda$handle$1(final Cuboid cuboid, final World world, final PlayerData playerData, final DecimalFormat decimalFormat, final PlayerLocation playerLocation, final Player player, final PlayerLocation playerLocation2, final int lastBypassTick) {
        if (cuboid.checkBlocks(world, FlyA::lambda$null$0)) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("toy:").append(decimalFormat.format(playerLocation.getY() % 1.0))), false);
            if (OptionsManager.getInstance().isRollback() && this.violations >= 1.0) {
                playerData.cancelMove(player, playerLocation2, world);
            }
        }
        else {
            this.violations -= Math.min(this.violations + 2.0, 0.05);
            this.lastBypassTick = lastBypassTick;
        }
    }
    
    public FlyA() {
        super(CheckType.FLYA, "A", "Fly", CheckVersion.RELEASE);
        this.lastBypassTick = -10;
        this.violations = -2.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.isFlying() && playerData.getTeleportTicks() > 1) {
            if (playerLocation2.getY() % 0.5 == 0.0 && !playerLocation2.getOnGround() && playerLocation.getY() < playerLocation2.getY() && playerData.getTotalTicks() - 10 > this.lastBypassTick) {
                this.run(this::lambda$handle$1);
            }
            else {
                this.violations -= Math.min(this.violations + 2.0, 0.025);
            }
        }
    }
    
    private static boolean lambda$null$0(final Material material) {
        return !MaterialList.INVALID_SHAPE.contains(material);
    }
}
