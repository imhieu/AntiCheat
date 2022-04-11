package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AutoClickerF extends PacketCheck
{
    private int vl;
    private long lastSwing;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long lastSwing) {
        if (packet instanceof PacketPlayInArmAnimation && player.getGameMode() != GameMode.CREATIVE) {
            final long lastSwing2 = this.lastSwing;
            this.lastSwing = lastSwing;
            final long abs = Math.abs(lastSwing - lastSwing2);
            final double n = this.getGcd((long)(abs * Math.pow(2.0, 24.0)), (long)(abs * Math.pow(2.0, 24.0))) / Math.pow(2.0, 24.0);
            if (n > 0.0 && n < 5.0 && !playerData.isDigging() && !playerData.hasLag()) {
                if (++this.vl > 2) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("val:").append(n)), false);
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
    
    public AutoClickerF() {
        super(CheckType.AUTO_CLICKERF, "F", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    private long getGcd(final long n, final long n2) {
        return (n2 <= 16384.0) ? n : this.getGcd(n2, Math.abs(n - n2));
    }
}
