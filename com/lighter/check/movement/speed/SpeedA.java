package com.lighter.check.movement.speed;

import org.bukkit.*;
import com.google.common.util.concurrent.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.block.*;
import org.bukkit.potion.*;
import com.lighter.util.*;

public class SpeedA extends MovementCheck
{
    private int lastBlockAboveTick;
    private int bypassFallbackTicks;
    private int lastGroundTick;
    private int lastBypassTick;
    private int vl;
    private int sprintTicks;
    private int lastAirTick;
    private int lastFastAirTick;
    
    private static boolean lambda$null$40(final Material material) {
        return !MaterialList.ICE.contains(material) && !MaterialList.SLABS.contains(material) && !MaterialList.STAIRS.contains(material) && material != Material.SLIME_BLOCK;
    }
    
    private void lambda$handle$41(final int n, final Cuboid cuboid, final World world, final Cuboid cuboid2, final AtomicDouble atomicDouble, final PlayerLocation playerLocation, final Player player, final double n2, final PlayerData playerData, final DecimalFormat decimalFormat, final PlayerLocation playerLocation2) {
        int n3 = (n - 20 < this.lastBlockAboveTick) ? 1 : 0;
        if (n3 == 0 && !cuboid.checkBlocks(world, SpeedA::lambda$null$39)) {
            n3 = 1;
            this.lastBlockAboveTick = n;
        }
        int n4 = (n - 40 < this.lastBypassTick) ? 1 : 0;
        if (n4 == 0 && !cuboid2.checkBlocks(world, SpeedA::lambda$null$40)) {
            n4 = 1;
            this.lastBypassTick = n;
        }
        if (n3 != 0) {
            atomicDouble.addAndGet(0.2);
        }
        if (n4 != 0) {
            atomicDouble.addAndGet(((boolean)playerLocation.getOnGround()) ? 0.2 : 0.3);
            this.bypassFallbackTicks = 60;
        }
        if (player.getLocation().getBlock().getType().equals((Object)Material.WATER) || player.getLocation().getBlock().getType().equals((Object)Material.STATIONARY_WATER)) {
            atomicDouble.addAndGet(12.0);
        }
        if (n2 > atomicDouble.get()) {
            if (++this.vl > 5) {
                this.vl = 5;
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("distance:").append(decimalFormat.format(n2 - atomicDouble.get())).append(" in ").append(playerLocation.getOnGround() ? "ground" : "air")), false);
                if (OptionsManager.getInstance().isRollback() && this.violations >= 1.0) {
                    playerData.cancelMove(player, playerLocation2, world);
                }
            }
        }
        else {
            this.violations -= Math.min(this.violations + 5.0, 0.025);
        }
    }
    
    private static boolean lambda$null$39(final Material material) {
        return material == Material.AIR;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if ((playerLocation2.getX() != playerLocation.getX() || playerLocation2.getZ() != playerLocation.getZ()) && playerData.getTeleportTicks() > playerData.getMaxPingTicks() && playerData.getHorizontalSpeedTicks() > (playerData.getPingTicks() + 10) * 2 && !player.getAllowFlight() && !playerData.hasLag()) {
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals((Object)Material.ICE)) {
                if (++this.vl < 0) {
                    this.vl = 0;
                    return;
                }
                this.vl = 0;
            }
            final double hypot = MathUtil.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            final AtomicDouble atomicDouble = new AtomicDouble(0.0);
            if (playerLocation2.getOnGround()) {
                if (this.bypassFallbackTicks > 0) {
                    this.bypassFallbackTicks -= 10;
                }
                this.lastGroundTick = playerData.getTotalTicks();
                this.sprintTicks = ((playerData.getSprinting() != null && !playerData.getSprinting()) ? (++this.sprintTicks) : 0);
                final double distanceBetweenAngles360 = MathUtil.getDistanceBetweenAngles360(Math.toDegrees(-Math.atan2(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ())), playerLocation2.getYaw());
                final boolean b = distanceBetweenAngles360 < 5.0 && distanceBetweenAngles360 < 90.0;
                atomicDouble.addAndGet(BukkitUtils.getPotionLevel(player, PotionEffectType.SPEED) * 0.0573);
                atomicDouble.addAndGet((this.sprintTicks <= 1) ? (b ? 0.281 : 0.2865) : (b ? 0.217 : 0.2325));
                if (player.getWalkSpeed() > 0.2) {
                    atomicDouble.set(atomicDouble.get() * player.getWalkSpeed() / 0.2);
                }
                if (this.lastAirTick >= this.lastGroundTick - 10) {
                    atomicDouble.addAndGet((this.lastGroundTick - this.lastAirTick) * 0.125);
                }
            }
            else {
                if (this.bypassFallbackTicks > 0) {
                    atomicDouble.addAndGet(0.1);
                    --this.bypassFallbackTicks;
                }
                this.lastAirTick = playerData.getTotalTicks();
                boolean b2 = false;
                if (hypot > 0.36 && this.lastFastAirTick < this.lastGroundTick) {
                    this.lastFastAirTick = playerData.getTotalTicks();
                    atomicDouble.addAndGet(0.6125);
                    b2 = true;
                }
                else {
                    atomicDouble.addAndGet(0.36);
                }
                atomicDouble.addAndGet(BukkitUtils.getPotionLevel(player, PotionEffectType.SPEED) * (b2 ? 0.0375 : 0.0175));
                if (player.getWalkSpeed() > 0.2) {
                    atomicDouble.addAndGet(atomicDouble.get() * (player.getWalkSpeed() - 0.2) * 2.0);
                }
            }
            if (hypot > atomicDouble.get()) {
                final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                final World world = player.getWorld();
                final PlayerLocation location = playerData.getLocation();
                this.run(this::lambda$handle$41);
            }
            else {
                this.violations -= Math.min(this.violations + 5.0, 0.025);
            }
        }
    }
    
    public SpeedA() {
        super(CheckType.SPEEDA, "A", "Speed", CheckVersion.RELEASE);
        this.lastGroundTick = 0;
        this.lastFastAirTick = 0;
        this.lastAirTick = 0;
        this.lastBlockAboveTick = -20;
        this.lastBypassTick = -40;
        this.bypassFallbackTicks = 0;
        this.sprintTicks = 0;
        this.vl = 0;
        this.violations = -5.0;
    }
}
