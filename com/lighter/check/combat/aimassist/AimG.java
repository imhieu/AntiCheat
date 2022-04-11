package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimG extends AimCheck
{
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getLastAttackTicks() < 100) {
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            final float abs = Math.abs(playerLocation.getYaw() - playerLocation2.getYaw());
            final float abs2 = Math.abs(playerLocation2.getPitch() - playerLocation.getPitch());
            if (abs >= 1.0f && abs % 0.1f == 0.0f) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(abs)).append(",pdiff:").append(decimalFormat.format(abs2))), false);
            }
            if (abs2 >= 1.0f && abs2 % 0.1f == 0.0f) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(abs)).append(",pdiff:").append(decimalFormat.format(abs2))), false);
            }
        }
    }
    
    public AimG() {
        super(CheckType.AIM_ASSISTG, "G", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
