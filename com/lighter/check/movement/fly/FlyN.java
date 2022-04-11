package com.lighter.check.movement.fly;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.util.*;
import java.text.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;

public class FlyN extends MovementCheck
{
    boolean lastLastOnGround;
    double lastDistY;
    boolean lastOnGround;
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() / 2 && playerData.isSpawnedIn() && !playerData.hasLag() && playerData.getLastAttackTicks() > 100) {
            final World world = player.getWorld();
            final double lastDistY = playerLocation2.getY() - playerLocation.getY();
            final double lastDistY2 = this.lastDistY;
            this.lastDistY = lastDistY;
            final double n2 = (lastDistY2 - 0.08) * 0.9800000190734863;
            final boolean nearGround = this.isNearGround(player.getLocation());
            final boolean lastOnGround = this.lastOnGround;
            this.lastOnGround = nearGround;
            final boolean lastLastOnGround = this.lastLastOnGround;
            this.lastLastOnGround = lastOnGround;
            this.run(this::lambda$handle$27);
        }
    }
    
    public boolean isNearGround(final Location location) {
        for (double n = 0.3, n2 = -n; n2 <= n; n2 += n) {
            for (double n3 = -n; n3 <= n; n3 += n) {
                if (location.clone().add(n2, -0.5001, n3).getBlock().getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean lambda$null$26(final Material material) {
        return material == Material.AIR;
    }
    
    public boolean isEquals(final double n, final double n2) {
        return Math.abs(n - n2) < 0.001;
    }
    
    private static boolean lambda$null$25(final Material material) {
        return !MaterialList.BAD_VELOCITY.contains(material);
    }
    
    private void lambda$handle$27(final Cuboid cuboid, final World world, final Cuboid cuboid2, final PlayerLocation playerLocation, final boolean b, final boolean b2, final boolean b3, final double n, final double n2, final PlayerData playerData, final Player player, final PlayerLocation playerLocation2) {
        if (cuboid.checkBlocks(world, FlyN::lambda$null$25) && cuboid2.checkBlocks(world, FlyN::lambda$null$26)) {
            if (!playerLocation.getOnGround() && !b && !b2 && !b3 && Math.abs(n) >= 0.005) {
                final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                if (!this.isEquals(n2, n) && ++this.vl >= 3 + playerData.getPingTicks()) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("distance:").append(decimalFormat.format(n2)).append("predictDistance:").append(decimalFormat.format(Math.abs(n)))), false);
                    if (OptionsManager.getInstance().isRollback() && this.violations >= 2.0) {
                        playerData.cancelMove(player, playerLocation2, world);
                    }
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
    
    public FlyN() {
        super(CheckType.FLYN, "N", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
