package com.lighter.check.combat.autoclicker;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class AutoClickerS extends PacketCheck
{
    private int stage;
    private boolean other;
    
    public AutoClickerS() {
        super(CheckType.AUTO_CLICKERS, "S", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInArmAnimation) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                this.check0(packet, playerData);
            }
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                this.check0(packet, playerData);
            }
        }
        else if (packet instanceof PacketPlayInFlying && player.getGameMode() != GameMode.CREATIVE) {
            this.check0(packet, playerData);
        }
    }
    
    void check0(final Packet packet, final PlayerData playerData) {
        if (this.stage == 0) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                ++this.stage;
            }
        }
        else if (this.stage == 1) {
            if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (this.stage == 2) {
            if (packet instanceof PacketPlayInFlying) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (this.stage == 3) {
            if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (this.stage == 4) {
            if (packet instanceof PacketPlayInFlying) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (this.stage == 5) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(this.stage)), false);
                this.stage = 0;
            }
            else if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
            else if (packet instanceof PacketPlayInFlying) {
                this.other = true;
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (this.stage == 6) {
            if (!this.other) {
                if (packet instanceof PacketPlayInFlying) {
                    ++this.stage;
                }
                else {
                    this.stage = 0;
                }
            }
            else {
                if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(this.stage)), false);
                    this.other = false;
                }
                this.stage = 0;
            }
        }
        else if (this.stage == 7) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(this.stage)), false);
            }
            else {
                this.stage = 0;
            }
        }
    }
}
