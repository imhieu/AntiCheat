package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerT extends PacketCheck
{
    private long lastSwing;
    private int ticks;
    private int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long lastSwing) {
        if (packet instanceof PacketPlayInArmAnimation) {
            final int ticks = this.ticks;
            this.ticks = 0;
            final long n = -this.lastSwing;
            this.lastSwing = lastSwing;
            final long n2 = lastSwing - n;
            if (ticks < 2 && n2 < 50.0 && !playerData.hasLag()) {
                if (this.vl++ > 2) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ticks:").append(this.ticks)), false);
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
    
    public AutoClickerT() {
        super(CheckType.AUTO_CLICKERT, "T", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
