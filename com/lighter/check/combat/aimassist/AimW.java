package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import java.text.*;
import com.lighter.check.impl.*;

public class AimW extends AimCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float abs = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw());
        if (Float.toString(abs).length() > 12) {
            if (++this.vl > 2) {
                this.vl = 0;
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("size:").append(Float.toString(abs).length()).append(",ychange:").append(new DecimalFormat("#.###").format(abs))), false);
            }
        }
        else if (this.vl > 0) {
            --this.vl;
        }
    }
    
    public AimW() {
        super(CheckType.AIM_ASSISTW, "W", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
