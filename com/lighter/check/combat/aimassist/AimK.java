package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimK extends PacketCheck
{
    double vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw()) != 0.0f && Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw()) == Math.abs(playerData.getLocation().getPitch() - playerData.getLastLastLocation().getPitch())) {
                if (this.vl < 10.0) {
                    ++this.vl;
                }
                if (this.vl > 4.0) {
                    AlertsManager.getInstance().handleViolation(playerData, this, "rounded diff values", true);
                }
            }
            else {
                if (this.vl > 0.0) {
                    this.vl -= 0.2;
                }
                this.violations -= Math.min(this.violations + 5.0, 0.005);
            }
        }
    }
    
    public AimK() {
        super(CheckType.AIM_ASSISTK, "K", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -5.0;
    }
}
