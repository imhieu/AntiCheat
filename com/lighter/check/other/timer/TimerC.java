package com.lighter.check.other.timer;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class TimerC extends PacketCheck
{
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying && playerData.getPackets() > 22) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("more packets:").append(playerData.getPackets())), false);
        }
    }
    
    public TimerC() {
        super(CheckType.TIMERC, "C", "Timer", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
