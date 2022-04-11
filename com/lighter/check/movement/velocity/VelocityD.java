package com.lighter.check.movement.velocity;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import com.lighter.data.*;

public class VelocityD extends PacketCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            final PlayerLocation lastLastLocation = playerData.getLastLastLocation();
            final PlayerLocation location = playerData.getLocation();
            if (playerData.getVerticalVelocityTicks() > playerData.getMoveTicks() - 1 && playerData.getLastVelY3() > 0.0 && playerData.getVerticalVelocityTicks() > playerData.getMaxPingTicks() && (!player.isInsideVehicle() || !(player.getVehicle() instanceof Boat))) {
                if (location.getY() - lastLastLocation.getY() > 0.0) {
                    this.decreaseVL(1.0);
                    this.vl = 0;
                }
                else if (++this.vl > 2 + playerData.getPingTicks()) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("vticks:").append(playerData.getVerticalVelocityTicks())), false);
                }
                playerData.setLastVelY3(0.0);
            }
        }
    }
    
    public VelocityD() {
        super(CheckType.VELOCITYD, "D", "Velocity", CheckVersion.RELEASE);
        this.violations = -2.0;
    }
}
