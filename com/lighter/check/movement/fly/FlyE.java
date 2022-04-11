package com.lighter.check.movement.fly;

import org.bukkit.entity.*;
import java.util.function.*;
import com.lighter.util.*;
import java.text.*;
import com.lighter.data.*;
import org.bukkit.block.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.potion.*;
import org.bukkit.*;

public class FlyE extends MovementCheck
{
    private boolean ignoring;
    private int vl;
    
    private static boolean lambda$null$56(final Material material) {
        return !material.name().equals("SLIME_BLOCK") && !material.name().equals("PISTON_EXTENSION") && !material.name().contains("SHULKER_BOX");
    }
    
    private int getPotionLevel(final Player player) {
        return player.getActivePotionEffects().stream().filter((Predicate<? super Object>)FlyE::lambda$getPotionLevel$58).map((Function<? super Object, ? extends Integer>)PotionEffect::getAmplifier).findAny().orElse(-1) + 1;
    }
    
    private void lambda$handle$57(final Player player, final Cuboid cuboid, final World world, final PlayerData playerData, final DecimalFormat decimalFormat, final double n, final PlayerLocation playerLocation) {
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals((Object)Material.ICE) || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals((Object)Material.BED)) {
            if (++this.vl < 0) {
                this.vl = 0;
                return;
            }
            this.vl = 0;
        }
        if (cuboid.checkBlocks(world, FlyE::lambda$null$56)) {
            if (++this.vl > 5) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(n))), false);
                if (OptionsManager.getInstance().isRollback() && this.violations >= 1.0) {
                    playerData.cancelMove(player, playerLocation, world);
                }
            }
        }
        else {
            this.ignoring = true;
        }
    }
    
    private static boolean lambda$getPotionLevel$58(final PotionEffect potionEffect) {
        return potionEffect.getType().getId() == PotionEffectType.JUMP.getId();
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (player.isFlying() || player.getAllowFlight()) {
            return;
        }
        if (this.ignoring) {
            if (playerLocation2.getOnGround()) {
                this.ignoring = false;
            }
        }
        else if (playerLocation2.getY() > playerLocation.getY() && playerData.getVelocityTicks() > (playerData.getPingTicks() + 1) * 2 && !playerData.isTeleporting() && player.getGameMode() == GameMode.SURVIVAL && playerData.isSpawnedIn()) {
            final double n2 = playerLocation2.getY() - Math.max(0.0, playerLocation.getY());
            if (n2 > 100000.0 || this.violations > 300.0) {
                AlertsManager.getInstance().handleViolation(playerData, this, "hardFlying", true);
                playerData.fuckOff("Hard Flying");
            }
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            final double n3 = 0.41999998688699;
            final double max = Math.max(((boolean)playerLocation2.getOnGround()) ? 0.5 : n3, n3 + this.getPotionLevel(player) * 0.2);
            final double n4 = n2 - max;
            if (playerLocation2.getOnGround() && playerLocation.getOnGround() && (n4 == 0.0625 || n4 == 0.10000002384185791)) {
                return;
            }
            if (n2 > max && Math.abs(n2 - 0.5) > 1.0E-12) {
                this.run(this::lambda$handle$57);
            }
            else {
                this.violations -= Math.min(this.violations + 2.0, 0.025);
            }
        }
    }
    
    public FlyE() {
        super(CheckType.FLYE, "E", "Fly", CheckVersion.RELEASE);
        this.ignoring = false;
        this.violations = -2.0;
    }
}
