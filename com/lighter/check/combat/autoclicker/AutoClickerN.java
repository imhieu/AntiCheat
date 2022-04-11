package com.lighter.check.combat.autoclicker;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerN extends PacketCheck
{
    int stage;
    int vl;
    int movements;
    
    public AutoClickerN() {
        super(CheckType.AUTO_CLICKERN, "N", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (this.stage == 0) {
            if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
        }
        else if (this.stage == 1) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (this.stage == 2) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                if (++this.vl >= 5) {
                    try {
                        if (this.movements > 10 && player.getGameMode() != GameMode.CREATIVE) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("move:").append(this.movements)), false);
                        }
                        this.movements = 0;
                        this.vl = 0;
                    }
                    finally {
                        this.movements = 0;
                        this.vl = 0;
                    }
                }
                this.stage = 0;
            }
            else if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
            else {
                this.movements = 0;
                this.vl = 0;
                this.stage = 0;
            }
        }
        else if (this.stage == 3) {
            if (packet instanceof PacketPlayInFlying) {
                ++this.stage;
            }
            else {
                this.movements = 0;
                this.vl = 0;
                this.stage = 0;
            }
        }
        else if (this.stage == 4) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                ++this.movements;
                this.stage = 0;
            }
            else {
                this.movements = 0;
                this.vl = 0;
                this.stage = 0;
            }
        }
    }
}
