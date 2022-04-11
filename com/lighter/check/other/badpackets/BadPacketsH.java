package com.lighter.check.other.badpackets;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class BadPacketsH extends PacketCheck
{
    private Integer lastKeepAlive;
    
    public BadPacketsH() {
        super(CheckType.BADPACKETSH, "B", "PingSpoof", CheckVersion.RELEASE);
        this.lastKeepAlive = null;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInKeepAlive) {
            final PacketPlayInKeepAlive packetPlayInKeepAlive = (PacketPlayInKeepAlive)packet;
            if (this.lastKeepAlive != null && this.lastKeepAlive > packetPlayInKeepAlive.a() && playerData.getTotalTicks() > 100 && !playerData.hasLag()) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("spoof:").append(this.lastKeepAlive - packetPlayInKeepAlive.a())), false);
            }
            this.lastKeepAlive = packetPlayInKeepAlive.a();
        }
    }
}
