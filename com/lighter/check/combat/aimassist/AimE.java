package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimE extends AimCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float abs = Math.abs(playerLocation.getYaw() - playerLocation2.getYaw());
        final float abs2 = Math.abs(playerLocation.getPitch() - playerLocation2.getPitch());
        if (playerData.getLastAttackTicks() < 3 && abs > 0.0f && abs < 0.8 && abs2 > 0.279 && abs2 < 0.28090858) {
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            if (++this.vl > 2) {
                this.vl = 0;
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(abs)).append(",pdiff:").append(decimalFormat.format(abs2))), false);
            }
        }
        else {
            this.decreaseVL(1.0E-4);
            this.vl = 0;
        }
    }
    
    public AimE() {
        super(CheckType.AIM_ASSISTE, "E", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
