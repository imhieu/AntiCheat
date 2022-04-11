package com.lighter.check.other.badpackets;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class BadPacketsF extends PacketCheck
{
    private Long lastFlying;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.lastFlying != null) {
                if (n - this.lastFlying > 40L && player.getGameMode() == GameMode.SURVIVAL) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("time:").append(n - this.lastFlying)), false);
                }
                this.lastFlying = null;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            final long lastFlying = playerData.getLastFlying();
            if (n - lastFlying < 10L) {
                this.lastFlying = lastFlying;
            }
            else {
                this.violations -= Math.min(this.violations + 1.0, 0.025);
            }
        }
    }
    
    public BadPacketsF() {
        super(CheckType.BADPACKETSF, "B", "InvalidInteract", CheckVersion.RELEASE);
        this.lastFlying = null;
        this.violations = -1.0;
    }
}
