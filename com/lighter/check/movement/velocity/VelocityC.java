package com.lighter.check.movement.velocity;

import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import com.lighter.data.*;

public class VelocityC extends PacketCheck
{
    private double vl;
    
    public VelocityC() {
        super(CheckType.VELOCITYC, "C", "Velocity", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            final PlayerLocation lastLastLocation = playerData.getLastLastLocation();
            final PlayerLocation location = playerData.getLocation();
            if (playerData.getVerticalVelocityTicks() > playerData.getMoveTicks() - 1 && playerData.getLastVelY2() > 0.0 && playerData.getVerticalVelocityTicks() > playerData.getMaxPingTicks() && (!player.isInsideVehicle() || !(player.getVehicle() instanceof Boat))) {
                final double n2 = location.getY() - lastLastLocation.getY();
                if (lastLastLocation.getOnGround() && location.getOnGround() && n2 == 0.0) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl > 7.0) {
                        this.vl = 0.0;
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("vticks:").append(playerData.getVerticalVelocityTicks())), false);
                    }
                }
                else {
                    this.vl = 0.0;
                }
                playerData.setLastVelY2(0.0);
            }
        }
    }
}
