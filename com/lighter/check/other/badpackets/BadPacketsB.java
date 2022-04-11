package com.lighter.check.other.badpackets;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;

public class BadPacketsB extends PacketCheck
{
    private boolean placed;
    
    public BadPacketsB() {
        super(CheckType.BADPACKETSB, "B", "InvalidDirection", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.placed) {
                if (n - playerData.getLastFlying() > 40L) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("time:").append(n - playerData.getLastFlying())), false);
                }
                else {
                    this.violations -= Math.min(this.violations + 1.0, 0.05);
                }
                this.placed = false;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace && player.getGameMode() == GameMode.SURVIVAL) {
            if (n - playerData.getLastFlying() < 5L) {
                this.placed = true;
            }
            else {
                this.decreaseVL(0.1);
            }
        }
    }
}
