package com.lighter.check.other.badpackets;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.google.common.util.concurrent.*;
import org.bukkit.potion.*;
import com.lighter.util.*;
import org.bukkit.block.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import java.text.*;
import com.lighter.check.impl.*;

public class BadPacketsE extends MovementCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (CheckManager.getInstance().enabled(this.getType()) && !player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() / 2 && playerData.isSpawnedIn()) {
            final double hypot = MathUtil.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            final AtomicDouble atomicDouble = new AtomicDouble(0.2);
            atomicDouble.addAndGet(BukkitUtils.getPotionLevel(player, PotionEffectType.SPEED) * 0.05);
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals((Object)Material.ICE)) {
                atomicDouble.addAndGet(0.2);
            }
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals((Object)Material.AIR)) {
                atomicDouble.addAndGet(0.1);
            }
            if (player.isBlocking() && hypot > atomicDouble.get()) {
                if (++this.vl > 20 + playerData.getPingTicks()) {
                    this.vl = 10;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("no-slowdown distance:").append(new DecimalFormat("#.###").format(hypot))), false);
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
    
    public BadPacketsE() {
        super(CheckType.BADPACKETSE, "A", "NoSlow", CheckVersion.RELEASE);
    }
}
