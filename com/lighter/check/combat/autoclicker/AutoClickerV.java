package com.lighter.check.combat.autoclicker;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerV extends PacketCheck
{
    private int vl;
    private double lastCps;
    private double avgClickSpeed;
    private int ticks;
    
    public AutoClickerV() {
        super(CheckType.AUTO_CLICKERV, "V", "Auto Clicker", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInArmAnimation) {
            final int ticks = this.ticks;
            this.ticks = 0;
            if (playerData.isDigging() || playerData.isSwingDigging() || ticks > 5) {
                return;
            }
            this.avgClickSpeed = (this.avgClickSpeed * 19.0 + ticks * 50.0) / 20.0;
            final double lastCps = 1000.0 / this.avgClickSpeed;
            final double lastCps2 = this.lastCps;
            this.lastCps = lastCps;
            if (MathUtil.isRoughlyEqual(lastCps, lastCps2, 2.5E-4) && lastCps > 2.6 && !playerData.hasLag()) {
                if (this.vl++ > 12) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("cps: ").append(lastCps)), true);
                }
            }
            else {
                this.vl = 0;
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            ++this.ticks;
        }
    }
}
