package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class KillAuraA extends PacketCheck
{
    private boolean attack;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.attack) {
                if (n - playerData.getLastFlying() > 40L) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("time:").append(n)), false);
                }
                else {
                    this.decreaseVL(0.05);
                }
                this.attack = false;
            }
        }
        else if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            if (n - playerData.getLastFlying() < 5L) {
                this.attack = true;
            }
            else {
                this.decreaseVL(0.1);
            }
        }
    }
    
    public KillAuraA() {
        super(CheckType.KILL_AURAA, "A", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
