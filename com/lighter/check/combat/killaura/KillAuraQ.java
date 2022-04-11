package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class KillAuraQ extends PacketCheck
{
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
                switch (packetPlayInBlockDig.b()) {
                    case UP:
                    case NORTH:
                    case EAST:
                    case WEST: {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("face:").append(packetPlayInBlockDig.b().getName())), false);
                        break;
                    }
                }
            }
        }
    }
    
    public KillAuraQ() {
        super(CheckType.KILL_AURAQ, "Q", "Kill Aura", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
