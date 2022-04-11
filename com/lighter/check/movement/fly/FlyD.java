package com.lighter.check.movement.fly;

import com.lighter.check.impl.*;
import com.lighter.util.*;
import org.bukkit.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;

public class FlyD extends PacketCheck
{
    private boolean jumping;
    private int lastBypassTick;
    private Double lastY;
    private int jumped;
    
    public FlyD() {
        super(CheckType.FLYD, "D", "Fly", CheckVersion.RELEASE);
        this.jumped = 0;
        this.jumping = false;
        this.lastY = null;
        this.lastBypassTick = -10;
        this.violations = -2.0;
    }
    
    private static boolean lambda$null$0(final Material material) {
        return !MaterialList.BAD_VELOCITY.contains(material);
    }
    
    private void lambda$handle$2(final Cuboid cuboid, final World world, final Cuboid cuboid2, final PlayerData playerData, final int lastBypassTick) {
        if (cuboid.checkBlocks(world, FlyD::lambda$null$0) && cuboid2.checkBlocks(world, FlyD::lambda$null$1)) {
            AlertsManager.getInstance().handleViolation(playerData, this, "jumped", false);
        }
        else {
            this.jumped = 0;
            this.violations -= Math.min(this.violations + 2.0, 0.001);
            this.lastBypassTick = lastBypassTick;
        }
    }
    
    private static boolean lambda$null$1(final Material material) {
        return material == Material.AIR;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            final PacketPlayInFlying packetPlayInFlying = (PacketPlayInFlying)packet;
            if (!player.isFlying() && !packetPlayInFlying.f() && playerData.getVelocityTicks() > playerData.getPingTicks() * 2 && playerData.getTeleportTicks() > playerData.getPingTicks() && !player.isFlying() && playerData.getTotalTicks() - 10 > this.lastBypassTick) {
                if (this.lastY != null) {
                    if (this.jumping && packetPlayInFlying.b() < this.lastY) {
                        if (this.jumped++ > 1) {
                            this.run(this::lambda$handle$2);
                        }
                        this.jumping = false;
                    }
                    else if (packetPlayInFlying.b() > this.lastY) {
                        this.jumping = true;
                    }
                }
            }
            else if (playerData.getLocation().getY() % 0.5 == 0.0 || (playerData.getLocation().getY() - 0.41999998688697815) % 1.0 > 1.0E-15) {
                this.jumped = 0;
                this.jumping = false;
            }
            this.violations -= Math.min(this.violations + 5.0, 0.025);
            if (packetPlayInFlying.h()) {
                this.lastY = packetPlayInFlying.b();
            }
        }
    }
}
