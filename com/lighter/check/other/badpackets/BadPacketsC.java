package com.lighter.check.other.badpackets;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class BadPacketsC extends PacketCheck
{
    private int threshold;
    
    public BadPacketsC() {
        super(CheckType.BADPACKETSC, "A", "ServerCrasher", CheckVersion.RELEASE);
        this.threshold = 0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInCustomPayload) {
            final String a = ((PacketPlayInCustomPayload)packet).a();
            if ((a.equals("MC|BOpen") || a.equals("MC|BEdit")) && (this.threshold += 2) > 4) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("payload channel:").append(a)), false);
                if (this.violations >= 3.0) {
                    playerData.fuckOff("PacketPlayInCustomPayload");
                }
            }
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.threshold -= Math.min(this.threshold, 1);
        }
    }
}
