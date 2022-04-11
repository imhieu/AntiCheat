package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import java.text.*;
import com.lighter.data.manager.*;

public class AimO extends AimCheck
{
    private double vl;
    
    public AimO() {
        super(CheckType.AIM_ASSISTO, "O", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getLastAttackTicks() < 100) {
            final float n2 = (float)MathUtil.getDistanceBetweenAngles(playerLocation2.getYaw(), playerLocation.getYaw());
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            if (playerLocation.getPitch() == playerLocation2.getPitch() && n2 >= 3.0f && playerLocation.getPitch() != 90.0f && playerLocation2.getPitch() != 90.0f) {
                final double vl = this.vl + 0.9;
                this.vl = vl;
                if (vl >= 6.3) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff: ").append(decimalFormat.format(n2))), false);
                }
            }
            else {
                this.vl -= 1.6;
            }
        }
    }
}
