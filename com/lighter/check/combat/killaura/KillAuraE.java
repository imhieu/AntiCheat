package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class KillAuraE extends PacketCheck
{
    private int stage;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        final int n2 = this.stage % 6;
        if (n2 == 0) {
            if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (n2 == 1) {
            if (packet instanceof PacketPlayInUseEntity) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (n2 == 2) {
            if (packet instanceof PacketPlayInEntityAction) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (n2 == 3) {
            if (packet instanceof PacketPlayInFlying) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (n2 == 4) {
            if (packet instanceof PacketPlayInEntityAction) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (n2 == 5) {
            if (packet instanceof PacketPlayInFlying) {
                if (++this.stage >= 30) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(n2)), false);
                }
            }
            else {
                this.stage = 0;
            }
        }
    }
    
    public KillAuraE() {
        super(CheckType.KILL_AURAE, "E", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
