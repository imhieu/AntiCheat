package com.lighter.check.other.badpackets;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class BadPacketsK extends PacketCheck
{
    private int stage;
    private int ticks;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.stage == 2) {
                ++this.ticks;
            }
        }
        else if (packet instanceof PacketPlayInUseEntity) {
            this.stage = 1;
        }
        else if (packet instanceof PacketPlayInEntityAction) {
            final PacketPlayInEntityAction packetPlayInEntityAction = (PacketPlayInEntityAction)packet;
            if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.START_SPRINTING) {
                this.stage = ((this.stage == 1) ? 2 : 0);
            }
            else if (packetPlayInEntityAction.b() == PacketPlayInEntityAction.EnumPlayerAction.STOP_SLEEPING) {
                if (this.stage == 2) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ticks:").append(this.ticks)), false);
                }
                this.ticks = 0;
                this.stage = 0;
            }
        }
    }
    
    public BadPacketsK() {
        super(CheckType.BADPACKETSK, "A", "W-Tap", CheckVersion.RELEASE);
        this.ticks = 0;
        this.stage = 0;
    }
}
