package com.lighter.check.movement.speed;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.util.*;
import com.google.common.util.concurrent.*;
import org.bukkit.potion.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class SpeedF extends MovementCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() / 2 && playerData.isSpawnedIn() && (player.getLocation().getBlock().getType().equals((Object)Material.WATER) || player.getLocation().getBlock().getType().equals((Object)Material.STATIONARY_WATER))) {
            final double hypot = MathUtil.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            final double n2 = playerLocation2.getY() - playerLocation.getY();
            final AtomicDouble atomicDouble = new AtomicDouble(0.1);
            if (player.hasPotionEffect(PotionEffectType.SPEED)) {
                atomicDouble.addAndGet(0.02);
            }
            if (n2 == 0.0 && hypot > atomicDouble.get()) {
                if (++this.vl >= 25) {
                    AlertsManager.getInstance().handleViolation(playerData, this, "fast water", false);
                    this.vl = 0;
                }
            }
            else {
                this.vl = 0;
            }
        }
        else {
            this.vl = 0;
        }
    }
    
    public SpeedF() {
        super(CheckType.SPEEDF, "F", "Speed", CheckVersion.EXPERIMENTAL);
        this.violations = 1.0;
    }
}
