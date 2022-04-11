package com.lighter.check.other.badpackets;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class BadPacketsL extends PacketCheck
{
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying && !playerData.isTeleporting()) {
            final float e = ((PacketPlayInFlying)packet).e();
            if (Math.abs(e) > 90.0f) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("invalid pitch:").append(e)), false);
            }
        }
    }
    
    public BadPacketsL() {
        super(CheckType.BADPACKETSL, "A", "InvalidDirection", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
