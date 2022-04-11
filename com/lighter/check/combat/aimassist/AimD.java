package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimD extends AimCheck
{
    double lastDiff;
    double previous;
    double last;
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getLastAttackTicks() < 100) {
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            final double min = Math.min(Math.atan2(playerLocation.getPitch(), playerLocation2.getPitch()), this.last);
            final double previous = Math.abs(playerLocation.getPitch() - playerLocation2.getPitch());
            final double gcd = MathUtil.gcd(16384.0, previous * Math.pow(2.0, 24.0), this.previous * Math.pow(2.0, 24.0));
            if (Math.abs(min - this.lastDiff) == 0.0 && Math.abs(playerLocation.getPitch() - playerLocation2.getPitch()) > 0.0f && gcd < 131072.0 && gcd > 0.0) {
                if (this.vl++ > 5) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("now:").append(decimalFormat.format(min)).append(",pchange:").append(decimalFormat.format(previous)).append(",gcd:").append(gcd)), false);
                }
            }
            else if (this.vl > 4) {
                --this.vl;
            }
            this.lastDiff = Math.min(Math.atan2(playerLocation.getPitch(), playerLocation2.getPitch()), this.last);
            this.last = Math.atan2(playerLocation.getPitch(), playerLocation2.getPitch());
            this.previous = previous;
        }
    }
    
    public AimD() {
        super(CheckType.AIM_ASSISTD, "D", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
