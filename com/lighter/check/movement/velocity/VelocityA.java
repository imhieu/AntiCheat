package com.lighter.check.movement.velocity;

import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import com.lighter.data.*;

public class VelocityA extends PacketCheck
{
    private int ticks;
    
    public VelocityA() {
        super(CheckType.VELOCITYA, "A", "Velocity", CheckVersion.RELEASE);
        this.ticks = 0;
        this.violations = -2.5;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            final PlayerLocation lastLastLocation = playerData.getLastLastLocation();
            final PlayerLocation location = playerData.getLocation();
            if (playerData.getVerticalVelocityTicks() > playerData.getMoveTicks() && playerData.getLastVelY() > 0.0 && (!player.isInsideVehicle() || !(player.getVehicle() instanceof Boat))) {
                final double n2 = location.getY() - lastLastLocation.getY();
                if (n2 > 0.0) {
                    final double n3;
                    final double n4;
                    if ((n3 = Math.ceil(n2 * 8000.0) / 8000.0) < 0.41999998688697815 && playerData.getLastLastLocation().getOnGround() && lastLastLocation.getOnGround() && !location.getOnGround() && MathUtil.onGround(lastLastLocation.getY()) && !MathUtil.onGround(location.getY()) && (n4 = n3 / playerData.getLastVelY()) < 0.995) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("%").append(n4)), false);
                    }
                    playerData.setLastVelY(0.0);
                    this.ticks = 0;
                }
                else if (n2 == 0.0 && !playerData.hasLag(n - 100L) && !playerData.hasFast(n - 100L) && this.ticks++ > playerData.getMaxPingTicks() * 2 + 6) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("%").append(n2 / playerData.getLastVelY())), false);
                    playerData.setLastVelY(0.0);
                    this.ticks = 0;
                }
                else {
                    this.violations -= Math.min(this.violations + 2.5, 0.01);
                }
            }
        }
    }
}
