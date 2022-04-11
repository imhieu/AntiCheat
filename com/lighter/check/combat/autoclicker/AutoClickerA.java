package com.lighter.check.combat.autoclicker;

import java.util.*;
import com.lighter.check.impl.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerA extends PacketCheck
{
    private final Queue<Integer> intervals;
    private boolean place;
    private int ticks;
    private boolean swung;
    
    public AutoClickerA() {
        super(CheckType.AUTO_CLICKERA, "A", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -2.0;
        this.ticks = 0;
        this.intervals = new ConcurrentLinkedQueue<Integer>();
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.swung && playerData.isSwingDigging() && !this.place && player.getGameMode() == GameMode.SURVIVAL && playerData.getLastAttackTicks() < 2400) {
                if (this.ticks < 8) {
                    this.intervals.add(this.ticks);
                    if (this.intervals.size() >= 40) {
                        final double deviation = MathUtil.deviation(this.intervals);
                        this.violations += (0.325 - deviation) * 2.0 + 0.675;
                        if (this.violations < -5.0) {
                            this.violations = -5.0;
                        }
                        if (deviation < 0.325) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("deviation:").append(deviation)), false);
                        }
                        this.intervals.clear();
                    }
                }
                this.ticks = 0;
            }
            this.place = false;
            this.swung = false;
            ++this.ticks;
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            this.swung = true;
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.place = true;
        }
    }
}
