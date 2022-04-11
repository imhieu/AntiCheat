package com.lighter.check.other.timer;

import java.util.concurrent.*;
import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class TimerA extends PacketCheck
{
    private Long lastPacket;
    private long offset;
    private static final long CHECK_TIME_LINEAR;
    
    public static long fromNanos(final long n) {
        return TimeUnit.NANOSECONDS.toMillis(n);
    }
    
    static {
        CHECK_TIME_LINEAR = toNanos(45L);
    }
    
    public static long toNanos(final long n) {
        return TimeUnit.MILLISECONDS.toNanos(n);
    }
    
    public TimerA() {
        super(CheckType.TIMERA, "A", "Timer", CheckVersion.RELEASE);
        this.lastPacket = null;
        this.offset = -100L;
        this.violations = -5.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (playerData.getLocation(playerData.getPingTicks() + 1) == null) {
                return;
            }
            final long nanoTime = System.nanoTime();
            if (this.lastPacket != null) {
                this.offset += toNanos(50L) - (nanoTime - this.lastPacket);
                if (this.offset <= TimerA.CHECK_TIME_LINEAR) {
                    this.violations -= Math.min(this.violations + 5.0, 0.005);
                }
                else {
                    if (playerData.getTotalTicks() > 100 && playerData.getTeleportTicks() > playerData.getMaxPingTicks() * 2 && playerData.isSpawnedIn() && (playerData.getSteerTicks() == 0 || playerData.getSteerTicks() > playerData.getPingTicks())) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("offset:").append(fromNanos(this.offset))), false);
                    }
                    this.offset = 0L;
                }
            }
            this.lastPacket = nanoTime;
        }
        else if (packet instanceof PacketPlayOutPosition) {
            this.offset = -100L;
        }
    }
}
