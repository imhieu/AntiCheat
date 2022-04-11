package com.lighter.check.movement.fly;

import com.lighter.util.*;
import java.text.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.*;

public class FlyI extends MovementCheck
{
    int vl;
    
    private static boolean lambda$null$72(final Material material) {
        return !MaterialList.BAD_VELOCITY.contains(material) && !MaterialList.INVALID_SHAPE.contains(material);
    }
    
    private void lambda$handle$73(final Cuboid cuboid, final World world, final PlayerData playerData, final DecimalFormat decimalFormat, final double n, final Player player, final PlayerLocation playerLocation) {
        if (cuboid.checkBlocks(world, FlyI::lambda$null$72)) {
            if (++this.vl > 4) {
                this.vl = 2;
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(n))), false);
                if (OptionsManager.getInstance().isRollback() && this.violations >= 1.0) {
                    playerData.cancelMove(player, playerLocation, world);
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
    
    public FlyI() {
        super(CheckType.FLYI, "I", "Fly", CheckVersion.RELEASE);
        this.violations = -2.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final double n2 = playerLocation.getY() - playerLocation2.getY();
        if (n2 > 0.079 && !player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() / 2 && playerData.isSpawnedIn() && Math.abs(playerLocation.getY() % 0.5 - 0.015555072702202466) > 1.0E-12) {
            this.run(this::lambda$handle$73);
            return;
        }
        this.vl = 0;
        this.decreaseVL(0.005);
    }
}
