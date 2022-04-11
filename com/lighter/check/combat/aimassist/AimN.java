package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimN extends PacketCheck
{
    double lastYaw;
    double pitchDiff;
    double lastPitch;
    double yawDiff;
    double vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.pitchDiff = Math.abs(playerData.getLocation().getPitch() - playerData.getLastLastLocation().getPitch());
            this.yawDiff = Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw());
            if ((this.pitchDiff == this.lastPitch && this.pitchDiff != 0.0 && this.pitchDiff > 0.4) || (this.yawDiff == this.lastYaw && this.yawDiff != 0.0 && this.pitchDiff > 0.4)) {
                final double vl = this.vl + 1.0;
                this.vl = vl;
                if (vl > 3 + playerData.getPingTicks()) {
                    this.vl = 0.0;
                    AlertsManager.getInstance().handleViolation(playerData, this, "same last rotation", true);
                }
            }
            else {
                this.violations -= Math.min(this.violations + 1.0, 0.005);
                this.vl = 0.0;
            }
            this.lastYaw = this.yawDiff;
            this.lastPitch = this.pitchDiff;
        }
    }
    
    public AimN() {
        super(CheckType.AIM_ASSISTN, "N", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
