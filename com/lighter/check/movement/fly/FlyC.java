package com.lighter.check.movement.fly;

import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import com.lighter.util.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;

public class FlyC extends PacketCheck
{
    private int lastBypassTick;
    private int threshold;
    private Double lastY;
    
    private void lambda$handle$45() {
        this.threshold = 0;
        this.violations -= Math.min(this.violations + 2.0, 0.01);
    }
    
    private static boolean lambda$null$43(final Material material) {
        return material == Material.AIR;
    }
    
    private void lambda$handle$44(final Cuboid cuboid, final World world, final Cuboid cuboid2, final PlayerData playerData, final DecimalFormat decimalFormat, final double n, final int lastBypassTick) {
        if (cuboid.checkBlocks(world, FlyC::lambda$null$42) && cuboid2.checkBlocks(world, FlyC::lambda$null$43)) {
            if (this.threshold++ > 2) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("y:").append(decimalFormat.format(n))), false);
            }
        }
        else {
            this.threshold = 0;
            this.violations -= Math.min(this.violations + 2.0, 0.01);
            this.lastBypassTick = lastBypassTick;
        }
    }
    
    public FlyC() {
        super(CheckType.FLYC, "C", "Fly", CheckVersion.RELEASE);
        this.lastY = null;
        this.threshold = 0;
        this.lastBypassTick = -10;
        this.violations = -2.0;
    }
    
    private static boolean lambda$null$42(final Material material) {
        return !MaterialList.BAD_VELOCITY.contains(material);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            final PacketPlayInFlying packetPlayInFlying = (PacketPlayInFlying)packet;
            if (this.lastY != null) {
                final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                final double n2 = packetPlayInFlying.g() ? packetPlayInFlying.b() : this.lastY;
                if (this.lastY == n2 && player.getVehicle() == null && !packetPlayInFlying.f() && !player.isFlying() && player.getGameMode() != GameMode.CREATIVE && playerData.getTeleportTicks() > playerData.getPingTicks() && playerData.getTotalTicks() - 10 > this.lastBypassTick && playerData.getVelocityTicks() > playerData.getPingTicks() * 2 && playerData.isSpawnedIn() && !playerData.hasFast()) {
                    this.run(this::lambda$handle$44);
                }
                else {
                    this.run(this::lambda$handle$45);
                }
            }
            if (packetPlayInFlying.g()) {
                this.lastY = packetPlayInFlying.b();
            }
        }
    }
}
