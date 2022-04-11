package com.lighter.check.combat.killaura;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class KillAuraO extends PacketCheck
{
    boolean sentAttack;
    boolean sentInteract;
    
    public KillAuraO() {
        super(CheckType.KILL_AURAO, "O", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.sentInteract = false;
            this.sentAttack = false;
        }
        else if (packet instanceof PacketPlayInUseEntity) {
            final PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity)packet;
            if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                this.sentAttack = true;
            }
            if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) {
                this.sentInteract = true;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace && (this.sentAttack & !this.sentInteract)) {
            AlertsManager.getInstance().handleViolation(playerData, this, "attack & interact", false);
        }
    }
}
