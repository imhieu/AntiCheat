package com.lighter.check.movement.fly;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import org.bukkit.*;
import org.bukkit.block.*;

public class FlyK extends MovementCheck
{
    private int vl;
    private double lastGround;
    
    public FlyK() {
        super(CheckType.FLYK, "K", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    private boolean isInWeb(final Player player) {
        for (double n = 0.3, n2 = -n; n2 <= n; n2 += n) {
            for (double n3 = -n; n3 <= n; n3 += n) {
                if (player.getLocation().clone().add(n3, 0.0, n2).getBlock().getType() == Material.WEB) {
                    return true;
                }
                if (player.getLocation().clone().add(n3, player.getEyeLocation().getY(), n2).getBlock().getType() == Material.WEB) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isInLiquid(final Player player) {
        for (double n = 0.3, n2 = -n; n2 <= n; n2 += n) {
            for (double n3 = -n; n3 <= n; n3 += n) {
                if (player.getLocation().clone().add(n3, 0.0, n2).getBlock().isLiquid()) {
                    return true;
                }
                if (player.getLocation().clone().add(n3, player.getEyeLocation().getY(), n2).getBlock().isLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getVelocityTicks() > 20 + playerData.getMaxPingTicks()) {
            this.run(this::lambda$handle$68);
        }
    }
    
    private void lambda$handle$68(final Player player, final PlayerData playerData) {
        if (this.isNearGround(player.getLocation())) {
            this.lastGround = playerData.getLocation().getY();
        }
        else {
            if (player.getGameMode() == GameMode.CREATIVE || playerData.isTeleporting() || player.getAllowFlight() || this.isClimbableBlock(player.getLocation().getBlock()) || this.isInWeb(player) || this.isInLiquid(player)) {
                this.vl = 0;
                return;
            }
            final double n = playerData.getLocation().getY() - this.lastGround;
            final double y = player.getVelocity().getY();
            if (n >= 1.3 && playerData.getLocation().getY() >= playerData.getLastLastLocation().getY() && y < -0.06 && player.getVehicle() == null) {
                if (++this.vl > 2) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("curY:").append(playerData.getLocation().getY())), false);
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
    
    private boolean isNearGround(final Location location) {
        for (double n = 0.3, n2 = -n; n2 <= n; n2 += n) {
            for (double n3 = -n; n3 <= n; n3 += n) {
                if (location.clone().add(n2, -0.5001, n3).getBlock().getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isClimbableBlock(final Block block) {
        return block.getType().toString().contains("LADDER") || block.getType().toString().contains("VINE");
    }
}
