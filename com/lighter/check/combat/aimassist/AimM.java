package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimM extends AimCheck
{
    int vl;
    double lastYawAbs;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        this.lastYawAbs = Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw());
        if (playerLocation2.getYaw() == Math.round(playerLocation2.getYaw()) || (playerLocation2.getPitch() != -90.0f && playerLocation2.getPitch() != 90.0f && playerLocation2.getPitch() == Math.round(playerLocation2.getPitch()))) {
            if (++this.vl > 1) {
                this.vl = 0;
                AlertsManager.getInstance().handleViolation(playerData, this, "rounded rotation value", true);
            }
        }
        else {
            this.vl = 0;
            this.violations -= Math.min(this.violations + 10.0, 0.005);
        }
    }
    
    public AimM() {
        super(CheckType.AIM_ASSISTM, "M", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -10.0;
    }
}
