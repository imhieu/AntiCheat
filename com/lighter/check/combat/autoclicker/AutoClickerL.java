package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerL extends PacketCheck
{
    int stage;
    int ticks;
    int vl;
    boolean first;
    boolean swing;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.stage == 2) {
                this.stage = 3;
            }
            this.swing = false;
            ++this.ticks;
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            if (this.stage == 1) {
                this.stage = 2;
            }
            else if (this.stage == 3 || this.stage == 0) {
                this.swing = true;
            }
        }
        else if (packet instanceof PacketPlayInBlockDig && this.ticks > 1) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                if (this.stage == 0) {
                    this.stage = 1;
                    this.first = this.swing;
                }
                else {
                    this.stage = 0;
                }
            }
            else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                if (this.stage == 3) {
                    if (this.swing && player.getGameMode() != GameMode.CREATIVE) {
                        if (++this.vl > 2) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(this.stage)), false);
                        }
                    }
                    else if (this.first) {
                        this.decreaseVL(0.2);
                        this.vl = 0;
                    }
                }
                this.stage = 0;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.ticks = 0;
        }
    }
    
    public AutoClickerL() {
        super(CheckType.AUTO_CLICKERL, "L", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -5.0;
    }
}
