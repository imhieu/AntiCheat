package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerC extends PacketCheck
{
    private long lastFlying;
    private int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long lastFlying) {
        if (packet instanceof PacketPlayInArmAnimation) {
            final long abs = Math.abs(lastFlying - this.lastFlying);
            if (abs < 5L && !playerData.hasLag()) {
                if (this.vl++ > 10) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("diff:").append(abs)), false);
                }
            }
            else {
                this.vl = 0;
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.lastFlying = lastFlying;
        }
    }
    
    public AutoClickerC() {
        super(CheckType.AUTO_CLICKERC, "C", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -2.0;
    }
}
