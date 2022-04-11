package com.lighter.check.movement.fly;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import java.text.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.*;

public class FlyL extends MovementCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final double n2 = playerLocation.getY() - playerLocation2.getY();
        if (playerLocation.getOnGround() && Math.abs(n2) <= 0.05 && !player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() / 2 && playerData.isSpawnedIn()) {
            this.run(this::lambda$handle$34);
            return;
        }
        this.vl = 0;
        this.decreaseVL(0.005);
    }
    
    private void lambda$handle$34(final Cuboid cuboid, final World world, final PlayerData playerData, final DecimalFormat decimalFormat, final double n, final Player player, final PlayerLocation playerLocation) {
        if (cuboid.checkBlocks(world, FlyL::lambda$null$33)) {
            if (++this.vl > 4) {
                this.vl = 2;
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(n))), false);
                if (OptionsManager.getInstance().isRollback() && this.violations >= 1.0) {
                    playerData.cancelMove(player, playerLocation, world);
                }
            }
        }
        else {
            this.vl = 0;
        }
    }
    
    public FlyL() {
        super(CheckType.FLYL, "L", "Fly", CheckVersion.RELEASE);
        this.violations = -2.0;
    }
    
    private static boolean lambda$null$33(final Material material) {
        return material == Material.AIR;
    }
}
