package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimF extends AimCheck
{
    private float suspiciousYaw;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getLastAttackTicks() >= 10) {
            final float n2 = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw()) % 180.0f;
            if (n2 > 1.0f && Math.round(n2) == n2) {
                final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                if (n2 == this.suspiciousYaw) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("toy:").append(decimalFormat.format(playerLocation2.getYaw())).append(",fromy:").append(decimalFormat.format(playerLocation.getYaw()))), false);
                }
                this.suspiciousYaw = (float)Math.round(n2);
            }
            else {
                this.suspiciousYaw = 0.0f;
                this.violations -= Math.min(this.violations + 10.0, 0.1);
            }
        }
    }
    
    public AimF() {
        super(CheckType.AIM_ASSISTF, "F", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -10.0;
    }
}
