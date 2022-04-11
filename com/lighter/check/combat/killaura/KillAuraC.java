package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class KillAuraC extends PacketCheck
{
    private boolean sent;
    private boolean interact;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.interact = false;
            this.sent = false;
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.sent = true;
        }
        else if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) {
            if (this.sent && !this.interact) {
                AlertsManager.getInstance().handleViolation(playerData, this, "place & interact", false);
                this.sent = false;
            }
            this.interact = true;
        }
    }
    
    public KillAuraC() {
        super(CheckType.KILL_AURAC, "C", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
