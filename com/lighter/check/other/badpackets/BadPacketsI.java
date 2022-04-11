package com.lighter.check.other.badpackets;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class BadPacketsI extends PacketCheck
{
    private int stage;
    private int ticks;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.stage == 2) {
                this.violations -= Math.min(this.violations + 1.0, 0.01);
            }
            if (this.stage == 1) {
                ++this.ticks;
                this.stage = 2;
            }
            else {
                this.stage = 0;
            }
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK) {
                this.stage = 1;
                this.violations -= Math.min(this.violations + 1.0, 1.0E-4);
            }
            else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                if (this.stage == 2 && this.ticks != 1) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ticks:").append(this.ticks)), false);
                }
                this.stage = 0;
                this.ticks = 0;
            }
        }
    }
    
    public BadPacketsI() {
        super(CheckType.BADPACKETSI, "A", "FastBreak", CheckVersion.RELEASE);
        this.violations = -1.0;
        this.stage = 0;
        this.ticks = 0;
    }
}
