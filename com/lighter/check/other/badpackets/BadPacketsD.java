package com.lighter.check.other.badpackets;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;

public class BadPacketsD extends PacketCheck
{
    public BadPacketsD() {
        super(CheckType.BADPACKETSD, "A", "InvalidInteract", CheckVersion.RELEASE);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInUseEntity && SafeReflection.getAttackedEntity((PacketPlayInUseEntity)packet) == player.getEntityId()) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("entityID:").append(player.getEntityId())), false);
        }
    }
}
