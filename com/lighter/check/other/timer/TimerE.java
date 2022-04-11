package com.lighter.check.other.timer;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class TimerE extends PacketCheck
{
    private int streak;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (((PacketPlayInFlying)packet).g()) {
                this.streak = 0;
            }
            else if (++this.streak > 20) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("streak:").append(this.streak)), false);
            }
        }
        else if (packet instanceof PacketPlayInSteerVehicle) {
            this.streak = 0;
        }
    }
    
    public TimerE() {
        super(CheckType.TIMERE, "E", "Timer", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
