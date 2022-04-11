package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimJ extends PacketCheck
{
    boolean lastYaw;
    double lastPitchAbs;
    double lastYawAbs;
    boolean lastPitch;
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if ((playerData.getLocation().getYaw() == playerData.getLastLastLocation().getYaw() && !this.lastYaw && this.lastYawAbs > 1.0) || (playerData.getLocation().getPitch() == playerData.getLastLastLocation().getPitch() && !this.lastPitch && this.lastPitchAbs > 1.0)) {
                if (this.vl > 5) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, "special rotation behavior", true);
                }
            }
            else {
                this.violations -= Math.min(this.violations + 6.0, 0.05);
                this.vl = 0;
            }
            this.lastYaw = (playerData.getLocation().getYaw() == playerData.getLastLastLocation().getYaw());
            this.lastPitch = (playerData.getLocation().getPitch() == playerData.getLastLastLocation().getPitch());
            this.lastPitchAbs = Math.abs(playerData.getLocation().getPitch() - playerData.getLastLastLocation().getPitch());
            this.lastYawAbs = Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw());
        }
    }
    
    public AimJ() {
        super(CheckType.AIM_ASSISTJ, "J", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -6.0;
    }
}
