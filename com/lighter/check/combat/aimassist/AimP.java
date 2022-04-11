package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimP extends AimCheck
{
    private float lastYawRate;
    private float lastPitchRate;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float lastPitchRate = (float)MathUtil.getDistanceBetweenAngles(playerLocation2.getPitch(), playerLocation.getPitch());
        final float lastYawRate = (float)MathUtil.getDistanceBetweenAngles(playerLocation2.getYaw(), playerLocation.getYaw());
        final float abs = Math.abs(this.lastPitchRate - lastPitchRate);
        final float abs2 = Math.abs(this.lastYawRate - lastYawRate);
        final float abs3 = Math.abs(abs - lastPitchRate);
        final float abs4 = Math.abs(abs2 - lastYawRate);
        final DecimalFormat decimalFormat = new DecimalFormat("#.###");
        if (lastPitchRate < 0.009 && lastPitchRate > 0.001 && abs > 1.0 && abs2 > 1.0 && lastYawRate > 3.0 && this.lastYawRate > 1.5 && (abs3 > 1.0f || abs4 > 1.0f)) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("pdiff:").append(decimalFormat.format(lastPitchRate)).append(",ydiff:").append(decimalFormat.format(lastYawRate))), false);
        }
        this.lastPitchRate = lastPitchRate;
        this.lastYawRate = lastYawRate;
    }
    
    public AimP() {
        super(CheckType.AIM_ASSISTP, "P", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
