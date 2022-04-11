package com.lighter.check.movement.fly;

import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.lighter.util.*;
import org.bukkit.potion.*;

public class FlyO extends MovementCheck
{
    private int lastBypassTick;
    private int threshold;
    private Double lastYDiff;
    
    private void lambda$handle$71(final Cuboid cuboid, final World world, final Cuboid cuboid2, final double n, final double n2, final PlayerLocation playerLocation, final boolean b, final PlayerData playerData, final int lastBypassTick) {
        if (cuboid.checkBlocks(world, FlyO::lambda$null$69) && cuboid2.checkBlocks(world, FlyO::lambda$null$70)) {
            if (++this.threshold > 2) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.format("D: %s D2: %s P: %s V: %s", n, n2, playerLocation.getY() % 1.0, b), ((b && !playerData.hasLag() && !playerData.hasFast()) ? 1.0 : 0.1) * this.threshold, false);
            }
        }
        else {
            this.decreaseVL(0.1);
            this.lastBypassTick = lastBypassTick;
            this.threshold = 0;
        }
    }
    
    private static boolean lambda$null$69(final Material material) {
        return !MaterialList.BAD_VELOCITY.contains(material) && !MaterialList.INVALID_SHAPE.contains(material);
    }
    
    public FlyO() {
        super(CheckType.FLYO, "O", "Fly", CheckVersion.RELEASE);
        this.lastYDiff = null;
        this.violations = -2.5;
    }
    
    private static boolean lambda$null$70(final Material material) {
        return material == Material.AIR;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!playerLocation.getOnGround() && !player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL)) {
            final double n2 = playerLocation2.getY() - playerLocation.getY();
            if (this.lastYDiff != null && playerData.getTotalTicks() - 40 > this.lastBypassTick && !playerData.isTeleporting() && !player.getAllowFlight() && playerData.isSpawnedIn() && !player.getGameMode().equals((Object)GameMode.CREATIVE) && playerData.getVelocityTicks() > (2 + playerData.getMaxPingTicks()) * 2 && Math.abs(n2 / 0.9800000190734863 + 0.08) > 1.0E-11 && Math.abs(n2 + 0.9800000190734863) > 1.0E-11 && Math.abs(n2 - 0.019999999105930755) > 1.0E-9 && Math.abs(n2 - 0.0030162615090425504) > 1.0E-9 && Math.abs(n2 + 0.15233518685055714) > 1.0E-11 && Math.abs(n2 + 0.07242780368044421) > 1.0E-11) {
                final boolean b = playerLocation.getX() != playerLocation2.getX() && playerLocation.getZ() != playerLocation2.getZ();
                double n3 = (this.lastYDiff - 0.08) * 0.9800000190734863;
                if ((playerLocation2.getOnGround() && n2 < 0.0 && n3 < n2 && MathUtil.onGround(playerLocation2.getY())) || (playerLocation.distanceXZSquared(playerLocation2) < 0.0025 && player.hasPotionEffect(PotionEffectType.JUMP))) {
                    n3 = n2;
                }
                else if (!player.hasPotionEffect(PotionEffectType.JUMP) && Math.abs(n3) < 0.005) {
                    n3 = 0.0;
                }
                final double abs = Math.abs(n3 - n2);
                if (abs > 1.0E-7) {
                    final World world = player.getWorld();
                    final Cuboid add = new Cuboid(playerData.getLocation()).add(new Cuboid(-0.5, 0.5, -1.0, 1.5, -0.5, 0.5));
                    final double n4 = 0.29999;
                    final Cuboid add2 = new Cuboid(playerData.getLocation()).move(0.0, 2.0, 0.0).add(new Cuboid(-n4, n4, -0.5, 0.5, -n4, n4));
                    final int totalTicks = playerData.getTotalTicks();
                    if (abs == 0.6103654679953531 || abs == 0.8603654679953531 || abs == 0.4116000080108642 || abs == 0.8434175188903723) {
                        return;
                    }
                    this.run(this::lambda$handle$71);
                }
                else {
                    this.decreaseVL(0.025);
                    this.threshold = 0;
                }
            }
        }
        this.lastYDiff = ((!playerLocation2.getOnGround() || !playerLocation.getOnGround()) ? Double.valueOf(playerLocation2.getY() - playerLocation.getY()) : null);
    }
}
