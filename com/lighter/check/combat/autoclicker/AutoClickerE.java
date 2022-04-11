package com.lighter.check.combat.autoclicker;

import java.util.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;
import java.util.concurrent.*;

public class AutoClickerE extends PacketCheck
{
    private int lastTicks;
    private int done;
    private boolean digging;
    private int ticks;
    private boolean abortedDigging;
    private boolean swung;
    private int vl;
    private final Queue<Integer> averageTicks;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.swung && !this.digging && playerData.getLastAttackTicks() < 2400) {
                if (this.ticks < 7) {
                    this.averageTicks.add(this.ticks);
                    if (this.averageTicks.size() > 50) {
                        this.averageTicks.poll();
                    }
                }
                if (this.averageTicks.size() > 40) {
                    final double average = MathUtil.average(this.averageTicks);
                    if (average < 2.5) {
                        if (this.ticks > 3 && this.ticks < 20 && this.lastTicks < 20) {
                            this.violations -= Math.min(this.violations, 0.25);
                            this.done = 0;
                        }
                        else if (this.done++ > 600.0 / (average * 1.5) && ++this.vl > 1) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("average:").append(average)), false);
                            this.done = 0;
                            this.vl = 0;
                        }
                    }
                    else {
                        this.done = 0;
                    }
                }
                this.lastTicks = this.ticks;
                this.ticks = 0;
            }
            if (this.abortedDigging) {
                this.digging = false;
                this.abortedDigging = false;
            }
            this.swung = false;
            ++this.ticks;
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            this.swung = true;
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.digging = true;
                this.abortedDigging = false;
            }
            else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                this.abortedDigging = true;
            }
        }
    }
    
    public AutoClickerE() {
        super(CheckType.AUTO_CLICKERE, "E", "Auto Clicker", CheckVersion.EXPERIMENTAL);
        this.violations = -2.0;
        this.lastTicks = 0;
        this.averageTicks = new ConcurrentLinkedQueue<Integer>();
        this.done = 0;
        this.ticks = 0;
    }
}
