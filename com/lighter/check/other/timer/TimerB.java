package com.lighter.check.other.timer;

import com.lighter.check.impl.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class TimerB extends PacketCheck
{
    private Long lastPacket;
    private long offset;
    private static final long CHECK_TIME_LINEAR;
    private long lastFlag;
    private int vl;
    
    static {
        CHECK_TIME_LINEAR = toNanos(45L);
    }
    
    public TimerB() {
        super(CheckType.TIMERB, "B", "Timer", CheckVersion.EXPERIMENTAL);
        this.violations = -20.0;
        this.lastPacket = null;
        this.offset = -100L;
    }
    
    public static long toNanos(final long n) {
        return TimeUnit.MILLISECONDS.toNanos(n);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            final long nanoTime = System.nanoTime();
            if (this.lastPacket != null) {
                this.offset += toNanos(50L) - (nanoTime - this.lastPacket);
                if (this.offset > TimerB.CHECK_TIME_LINEAR) {
                    if (fromNanos(nanoTime - this.lastFlag) > 1L && playerData.getPing() > playerData.getMaxPingTicks() && playerData.getTotalTicks() > 400 && !playerData.isTeleporting() && playerData.isSpawnedIn() && ++this.vl > 3) {
                        this.vl = 0;
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("offset:").append(fromNanos(this.offset))), false);
                    }
                    this.lastFlag = nanoTime;
                    this.offset = 0L;
                }
                else {
                    this.decreaseVL(0.0025);
                }
            }
            this.lastPacket = nanoTime;
        }
    }
    
    public static long fromNanos(final long n) {
        return TimeUnit.NANOSECONDS.toMillis(n);
    }
}
