package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerK extends PacketCheck
{
    private Long lastFlying;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.lastFlying != null) {
                if (n - this.lastFlying > 40L && n - this.lastFlying < 800L) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("time:").append((n - this.lastFlying) * 2L)), false);
                }
                else {
                    this.violations -= Math.min(this.violations + 2.0, 0.1);
                }
                this.lastFlying = null;
            }
        }
        else if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            final long lastFlying = playerData.getLastFlying();
            if (n - lastFlying < 10L) {
                this.lastFlying = lastFlying;
            }
            else {
                this.violations -= Math.min(this.violations + 2.0, 0.1);
            }
        }
    }
    
    public AutoClickerK() {
        super(CheckType.AUTO_CLICKERK, "K", "Auto Clicker", CheckVersion.RELEASE);
        this.lastFlying = null;
        this.violations = -2.0;
    }
}
