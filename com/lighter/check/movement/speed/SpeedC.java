package com.lighter.check.movement.speed;

import org.bukkit.entity.*;
import java.util.function.*;
import org.bukkit.potion.*;
import com.google.common.util.concurrent.*;
import com.lighter.data.*;
import java.text.*;
import org.bukkit.block.*;
import org.bukkit.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import com.lighter.util.*;
import com.google.common.collect.*;
import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.inventory.*;

public class SpeedC extends MovementCheck
{
    private int bypassFallbackTicks;
    private int lastGroundTick;
    private int lastBlockAboveTick;
    private int lastBypassTick;
    private int sprintTicks;
    private int lastFastAirTick;
    private int lastAirTick;
    
    private int getPotionLevel(final Player player) {
        return player.getActivePotionEffects().stream().filter((Predicate<? super Object>)SpeedC::lambda$getPotionLevel$49).map((Function<? super Object, ? extends Integer>)PotionEffect::getAmplifier).findAny().orElse(-1) + 1;
    }
    
    private static boolean lambda$getPotionLevel$49(final PotionEffect potionEffect) {
        return potionEffect.getType().getId() == PotionEffectType.SPEED.getId();
    }
    
    private static boolean lambda$hasEnchantment$50(final int n, final Integer n2) {
        return n == n2;
    }
    
    private void lambda$handle$48(final int n, final Cuboid cuboid, final World world, final int n2, final Cuboid cuboid2, final AtomicDouble atomicDouble, final boolean b, final PlayerLocation playerLocation, final Player player, final double n3, final PlayerData playerData, final DecimalFormat decimalFormat, final PlayerLocation playerLocation2) {
        int n4 = (n - 20L < this.lastBlockAboveTick) ? 1 : 0;
        if (n4 == 0 && !cuboid.checkBlocks(world, SpeedC::lambda$null$46)) {
            n4 = 1;
            this.lastBlockAboveTick = n2;
        }
        int n5 = (n - 60L < this.lastBypassTick) ? 1 : 0;
        if (n5 == 0 && !cuboid2.checkBlocks(world, SpeedC::lambda$null$47)) {
            n5 = 1;
            this.lastBypassTick = n2;
        }
        if (n4 != 0) {
            atomicDouble.addAndGet(0.2 * (b ? 2 : 1));
        }
        if (n5 != 0) {
            atomicDouble.addAndGet((playerLocation.getOnGround() ? 0.2 : 0.3) * (b ? 2 : 1));
            this.bypassFallbackTicks = 60;
        }
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals((Object)Material.ICE)) {
            atomicDouble.addAndGet(0.09);
        }
        if (n3 > atomicDouble.get()) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("distance:").append(decimalFormat.format(n3))), false);
            if (OptionsManager.getInstance().isRollback() && this.violations >= 1.0) {
                playerData.cancelMove(player, playerLocation2, world);
            }
        }
        else {
            this.violations -= Math.min(this.violations + 5.0, 0.02);
        }
    }
    
    private static boolean lambda$null$47(final Material material) {
        return !MaterialList.ICE.contains(material) && !MaterialList.SLABS.contains(material) && !MaterialList.STAIRS.contains(material) && material != Material.getMaterial(165);
    }
    
    private static boolean lambda$null$46(final Material material) {
        return material == Material.AIR;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if ((playerLocation2.getX() != playerLocation.getX() || playerLocation2.getZ() != playerLocation.getZ()) && !playerData.isTeleporting() && !player.getAllowFlight()) {
            final double hypot = MathUtil.hypot(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ());
            if (this.hasEnchantment(player, 8) || this.hasEnchantment(player, 9)) {
                return;
            }
            final AtomicDouble atomicDouble = new AtomicDouble(0.0);
            if (playerLocation2.getOnGround()) {
                if (this.bypassFallbackTicks > 0) {
                    this.bypassFallbackTicks -= 10;
                }
                this.lastGroundTick = playerData.getTotalTicks();
                this.sprintTicks = ((playerData.getSprinting() != null && !playerData.getSprinting()) ? (++this.sprintTicks) : 0);
                final double distanceBetweenAngles360 = MathUtil.getDistanceBetweenAngles360(Math.toDegrees(-Math.atan2(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ())), playerLocation2.getYaw());
                final boolean b = distanceBetweenAngles360 < 5.0 && distanceBetweenAngles360 < 90.0;
                atomicDouble.addAndGet(this.getPotionLevel(player) * 0.0573);
                atomicDouble.addAndGet((this.sprintTicks <= 6) ? (b ? 0.281 : 0.2865) : (b ? 0.217 : 0.2325));
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
                atomicDouble.addAndGet(this.getPotionLevel(player) * (b2 ? 0.0375 : 0.0175));
                if (player.getWalkSpeed() > 0.2) {
                    atomicDouble.addAndGet(atomicDouble.get() * (player.getWalkSpeed() - 0.2) * 2.0);
                }
            }
            if (playerData.getHorizontalSpeedTicks() < (playerData.getPingTicks() + 10) * 2) {
                return;
            }
            if (playerData.hasLag()) {
                atomicDouble.set(atomicDouble.get() * 2.0);
            }
            if (hypot > atomicDouble.get()) {
                final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                final World world = player.getWorld();
                final PlayerLocation location = playerData.getLocation();
                this.run(this::lambda$handle$48);
            }
            else {
                this.violations -= Math.min(this.violations + 5.0, 0.02);
            }
        }
    }
    
    public SpeedC() {
        super(CheckType.SPEEDC, "C", "Speed", CheckVersion.RELEASE);
        this.lastBypassTick = -50;
        this.lastBlockAboveTick = -20;
        this.violations = -5.0;
    }
    
    private boolean hasEnchantment(final Player player, final int n) {
        final Set concurrentHashSet = Sets.newConcurrentHashSet();
        for (final ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack != null) {
                concurrentHashSet.addAll(itemStack.getEnchantments().keySet());
            }
        }
        return concurrentHashSet.stream().map(Enchantment::getId).anyMatch(SpeedC::lambda$hasEnchantment$50);
    }
}
