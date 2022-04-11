package com.lighter.check.combat.killaura;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class KillAuraG extends PacketCheck
{
    private int invalidTicks;
    private int lastTicks;
    private int ticks;
    private int totalTicks;
    
    public KillAuraG() {
        super(CheckType.KILL_AURAG, "G", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            ++this.ticks;
        }
        else if (packet instanceof PacketPlayInUseEntity) {
            ((PacketPlayInUseEntity)packet).a();
            if (this.ticks <= 8) {
                if (this.lastTicks == this.ticks) {
                    ++this.invalidTicks;
                }
                if (this.totalTicks++ >= 25) {
                    if (this.invalidTicks > 22) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append(String.format("%s/%s", this.invalidTicks, this.totalTicks)).append(1.0).append((this.invalidTicks - 22) / 6.0)), false);
                    }
                    else {
                        this.violations -= Math.min(this.violations + 1.0, 1.0);
                    }
                    this.totalTicks = 0;
                    this.invalidTicks = 0;
                }
                this.lastTicks = this.ticks;
            }
            this.ticks = 0;
        }
    }
}
