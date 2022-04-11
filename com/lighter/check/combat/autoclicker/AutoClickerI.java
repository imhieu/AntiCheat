package com.lighter.check.combat.autoclicker;

import java.util.*;
import com.lighter.check.impl.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.util.*;
import org.bukkit.*;
import com.lighter.data.manager.*;

public class AutoClickerI extends PacketCheck
{
    private final Queue<Integer> clickQueue;
    private Integer releaseTime;
    private int start;
    
    public AutoClickerI() {
        super(CheckType.AUTO_CLICKERI, "I", "Auto Clicker", CheckVersion.RELEASE);
        this.releaseTime = null;
        this.clickQueue = new ConcurrentLinkedQueue<Integer>();
        this.start = 0;
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (playerData.isDigging()) {
                ++this.start;
            }
            if (this.releaseTime != null) {
                ++this.releaseTime;
            }
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                this.releaseTime = 0;
            }
            else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK && this.releaseTime != null && this.releaseTime < 4 && this.releaseTime > 0) {
                this.clickQueue.add(this.releaseTime);
                if (this.clickQueue.size() > 10) {
                    final double n2 = MathUtil.variance(1, this.clickQueue) / this.start;
                    if (n2 > 0.2) {
                        if (player.getGameMode() != GameMode.CREATIVE) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("c/s:").append(n2)), false);
                        }
                    }
                    else {
                        this.violations -= Math.min(this.violations + 1.0, 0.05);
                    }
                    this.clickQueue.clear();
                    this.start = 0;
                }
                this.releaseTime = null;
            }
        }
    }
}
