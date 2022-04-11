package com.lighter.check.movement.speed;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.google.common.util.concurrent.*;
import org.bukkit.potion.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class SpeedD extends MovementCheck
{
    int rewi;
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isDigging() && !playerData.isTeleporting() && playerData.getVelocityTicks() > 20 + playerData.getMaxPingTicks() && playerData.isSpawnedIn() && player.getLocation().getBlock().getType().equals((Object)Material.WEB)) {
            final double hypot = MathUtil.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            final double n2 = playerLocation2.getY() - playerLocation.getY();
            final AtomicDouble atomicDouble = new AtomicDouble(0.05);
            atomicDouble.addAndGet(BukkitUtils.getPotionLevel(player, PotionEffectType.SPEED) * 0.005);
            if (playerData.hasLag()) {
                atomicDouble.addAndGet(0.05);
            }
            if (n2 == -0.003920000134705504) {
                if (++this.rewi > 4) {
                    this.rewi = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, "fast web", false);
                }
            }
            else {
                this.rewi = 0;
            }
            if (n2 == 0.0 && hypot > atomicDouble.get()) {
                if (++this.vl > 4) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, "fast web", false);
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
    
    public SpeedD() {
        super(CheckType.SPEEDD, "D", "Speed", CheckVersion.RELEASE);
        this.violations = 1.0;
    }
}
