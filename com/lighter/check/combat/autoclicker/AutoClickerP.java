package com.lighter.check.combat.autoclicker;

import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class AutoClickerP extends PacketCheck
{
    private int stage;
    private double vl;
    private boolean place;
    
    void check0(final Packet packet, final PlayerData playerData) {
        if (this.stage == 0) {
            if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.place = true;
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            if (this.place || playerData.isDigging()) {
                this.place = false;
                return;
            }
            if (((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                if (this.stage == 1) {
                    ++this.stage;
                }
                else {
                    this.stage = 0;
                }
            }
            else if (((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                if (this.stage == 2) {
                    final double vl = this.vl + 1.4;
                    this.vl = vl;
                    if (vl >= 15.0) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(this.stage)), false);
                    }
                }
                else {
                    this.stage = 0;
                    this.vl -= 0.25;
                }
            }
            else {
                this.stage = 0;
            }
        }
        else {
            this.stage = 0;
        }
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInArmAnimation) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                this.check0(packet, playerData);
            }
        }
        else if (packet instanceof PacketPlayInBlockDig && player.getGameMode() != GameMode.CREATIVE) {
            this.check0(packet, playerData);
        }
    }
    
    public AutoClickerP() {
        super(CheckType.AUTO_CLICKERP, "P", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
