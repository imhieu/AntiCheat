package com.lighter.check.other.badpackets;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class BadPacketsG extends PacketCheck
{
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying && ((PacketPlayInFlying)packet).d() == -8.0E307) {
            AlertsManager.getInstance().handleViolation(playerData, this, "A", false);
            playerData.fuckOff("PacketPlayInFlying");
        }
    }
    
    public BadPacketsG() {
        super(CheckType.BADPACKETSG, "B", "ServerCrasher", CheckVersion.RELEASE);
    }
}
