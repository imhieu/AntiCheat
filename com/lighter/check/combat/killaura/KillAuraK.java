package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class KillAuraK extends PacketCheck
{
    boolean sent;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
        else if (packet instanceof PacketPlayInUseEntity) {
            this.sent = true;
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            final PacketPlayInBlockPlace packetPlayInBlockPlace = (PacketPlayInBlockPlace)packet;
            if (packetPlayInBlockPlace.getFace() != 255 && this.sent) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("face:").append(packetPlayInBlockPlace.getFace())), false);
            }
        }
    }
    
    public KillAuraK() {
        super(CheckType.KILL_AURAK, "K", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
