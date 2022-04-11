package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;
import java.util.*;

public class AutoClickerO extends PacketCheck
{
    Deque<Integer> recentCounts;
    int vl;
    BlockPosition lastBlock;
    int flyingCount;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                if (this.lastBlock != null && this.lastBlock.equals((Object)packetPlayInBlockDig.a())) {
                    this.recentCounts.addLast(this.flyingCount);
                    if (this.recentCounts.size() == 20) {
                        double n2 = 0.0;
                        final Iterator<Integer> iterator = this.recentCounts.iterator();
                        while (iterator.hasNext()) {
                            n2 += iterator.next();
                        }
                        final double n3 = n2 / this.recentCounts.size();
                        double n4 = 0.0;
                        final Iterator<Integer> iterator2 = this.recentCounts.iterator();
                        while (iterator2.hasNext()) {
                            n4 += Math.pow(iterator2.next() - n3, 2.0);
                        }
                        final double sqrt = Math.sqrt(n4 / this.recentCounts.size());
                        if (sqrt < 0.45 && ++this.vl >= 3.0) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("std:").append(sqrt)), false);
                        }
                        else {
                            this.vl -= (int)0.5;
                        }
                        this.recentCounts.clear();
                    }
                }
                this.flyingCount = 0;
            }
            else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                this.lastBlock = packetPlayInBlockDig.a();
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            ++this.flyingCount;
        }
    }
    
    public AutoClickerO() {
        super(CheckType.AUTO_CLICKERO, "O", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
        this.recentCounts = new LinkedList<Integer>();
    }
}
