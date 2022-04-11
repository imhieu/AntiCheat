package com.lighter.check.other.timer;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.*;

public class TimerD extends PacketCheck
{
    private long lastPacket;
    private int count;
    
    public TimerD() {
        super(CheckType.TIMERD, "D", "Timer", CheckVersion.EXPERIMENTAL);
        this.lastPacket = 0L;
        this.count = 0;
        this.violations = -4.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long lastPacket) {
        if (packet instanceof PacketPlayInFlying) {
            if (playerData.getTeleportTicks() > playerData.getPingTicks()) {
                final long n = lastPacket - this.lastPacket;
                final PlayerLocation location = playerData.getLocation();
                final PlayerLocation lastLastLocation = playerData.getLastLastLocation();
                if (n > 100L && location.distanceXZSquared(lastLastLocation) > 0.0) {
                    if (this.count++ > 7) {
                        this.count = 0;
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("delay:").append(n)), false);
                    }
                }
                else {
                    this.count = 0;
                    this.violations -= Math.min(this.violations + 4.0, 0.1);
                }
                this.lastPacket = lastPacket;
            }
        }
        else if (packet instanceof PacketPlayOutPosition) {
            this.count = 0;
        }
    }
}
