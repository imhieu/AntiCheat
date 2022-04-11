package com.lighter.check.combat.killaura;

import java.util.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import java.util.concurrent.*;

public class KillAuraP extends PacketCheck
{
    private final Queue<Double> pitchList;
    private Float lastPitch;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            final PacketPlayInFlying packetPlayInFlying = (PacketPlayInFlying)packet;
            if (packetPlayInFlying.h()) {
                if (this.lastPitch != null && playerData.getLastAttackTicks() <= 3 && playerData.getLastAttacked() != null) {
                    this.pitchList.add((double)Math.abs(packetPlayInFlying.e() - this.lastPitch));
                    if (this.pitchList.size() >= 20) {
                        final double deviation = MathUtil.deviation(this.pitchList);
                        final double average = MathUtil.average(this.pitchList);
                        if ((average > 17.5 && deviation > 15.0) || (average > 22.5 && deviation > 12.5)) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("average:").append(average).append(",deviation:").append(deviation)), false);
                        }
                        else {
                            this.decreaseVL(0.025);
                        }
                        this.pitchList.clear();
                    }
                }
                this.lastPitch = packetPlayInFlying.e();
            }
        }
    }
    
    public KillAuraP() {
        super(CheckType.KILL_AURAP, "P", "Kill Aura", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
        this.pitchList = new ConcurrentLinkedQueue<Double>();
        this.lastPitch = null;
    }
}
