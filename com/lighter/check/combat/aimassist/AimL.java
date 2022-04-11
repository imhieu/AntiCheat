package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;

public class AimL extends AimCheck
{
    int vl;
    
    public AimL() {
        super(CheckType.AIM_ASSISTL, "L", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerLocation2.getYaw() == playerLocation2.getPitch() && ++this.vl > 2 && playerData.getPing() > 0) {
            AlertsManager.getInstance().handleViolation(playerData, this, "same pitch & yaw", true);
        }
        else {
            this.vl = 0;
        }
    }
}
