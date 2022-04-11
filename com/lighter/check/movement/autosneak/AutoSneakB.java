package com.lighter.check.movement.autosneak;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.google.common.util.concurrent.*;
import org.bukkit.potion.*;
import com.lighter.util.*;
import org.bukkit.block.*;
import com.lighter.data.manager.*;
import java.text.*;
import com.lighter.check.impl.*;
import java.util.*;

public class AutoSneakB extends MovementCheck
{
    public List<Material> blocked;
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (player.isSneaking() && playerData.getSneaking() && !player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() / 2 && playerData.isSpawnedIn() && !playerData.hasLag()) {
            final double hypot = MathUtil.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            final double n2 = playerLocation2.getY() - playerLocation.getY();
            final AtomicDouble atomicDouble = new AtomicDouble(0.2);
            atomicDouble.addAndGet(BukkitUtils.getPotionLevel(player, PotionEffectType.SPEED) * 0.05);
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals((Object)Material.ICE)) {
                atomicDouble.addAndGet(0.2);
            }
            if (hypot > atomicDouble.get() && n2 == 0.0) {
                if (++this.vl > 10) {
                    this.vl = 5;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("distance:").append(new DecimalFormat("#.###").format(hypot))), true);
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
    
    public AutoSneakB() {
        super(CheckType.AUTOSNEAKB, "B", "AutoSneak", CheckVersion.RELEASE);
        (this.blocked = new ArrayList<Material>()).add(Material.REDSTONE_COMPARATOR);
        this.blocked.add(Material.REDSTONE_WIRE);
        this.blocked.add(Material.AIR);
        this.blocked.add(Material.SNOW_BLOCK);
        this.blocked.add(Material.SNOW);
    }
}
