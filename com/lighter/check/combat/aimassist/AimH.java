package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimH extends AimCheck
{
    private float suspiciousYaw;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getLastAttackTicks() < 100) {
            final float n2 = (float)MathUtil.getDistanceBetweenAngles(playerLocation2.getYaw(), playerLocation.getYaw());
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            if (n2 > 1.0f && Math.round(n2) == n2 && n2 % 1.5f != 0.0f) {
                if (n2 == this.suspiciousYaw) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(n2)).append("suspicous:").append(decimalFormat.format(this.suspiciousYaw))), false);
                }
                this.suspiciousYaw = (float)Math.round(n2);
            }
            else {
                this.suspiciousYaw = 0.0f;
            }
        }
    }
    
    public AimH() {
        super(CheckType.AIM_ASSISTH, "H", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
