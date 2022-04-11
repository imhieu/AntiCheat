package com.lighter.check.combat.killaura;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class KillAuraL extends PacketCheck
{
    boolean sent;
    
    public KillAuraL() {
        super(CheckType.KILL_AURAL, "L", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
        else if (packet instanceof PacketPlayInEntityAction) {
            final PacketPlayInEntityAction packetPlayInEntityAction = (PacketPlayInEntityAction)packet;
            if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING || packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SPRINTING || packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING || packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SNEAKING) {
                this.sent = true;
            }
        }
        else if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK && this.sent) {
            AlertsManager.getInstance().handleViolation(playerData, this, "sprint or sneak", false);
        }
    }
}
