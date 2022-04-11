package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimC extends AimCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getLastAttackTicks() < 20) {
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            final float abs = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw());
            final float abs2 = Math.abs(playerLocation2.getPitch() - playerLocation.getPitch());
            if (abs > 0.0f && abs < 0.01 && abs2 > 0.2) {
                if (++this.vl > 2) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(abs)).append(",pdiff:").append(decimalFormat.format(abs2))), false);
                }
            }
            else {
                this.violations -= Math.min(this.violations + 2.5, 0.05);
                this.vl = 0;
            }
        }
    }
    
    public AimC() {
        super(CheckType.AIM_ASSISTC, "C", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -2.5;
    }
}
