package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;

public class AimR extends AimCheck
{
    private int vl;
    
    public AimR() {
        super(CheckType.AIM_ASSISTR, "R", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float abs = Math.abs(playerLocation.getYaw() - playerLocation2.getYaw());
        final float abs2 = Math.abs(playerLocation.getPitch() - playerLocation2.getPitch());
        final DecimalFormat decimalFormat = new DecimalFormat("#.###");
        if (playerData.getLastAttackTicks() < 3 && abs > 0.0f && abs < 0.8 && abs2 > 0.279 && abs2 < 0.28090858 && ++this.vl > 2) {
            this.vl = 0;
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(abs)).append(",pdiff:").append(decimalFormat.format(abs2))), false);
        }
        else {
            this.decreaseVL(1.0E-4);
            this.vl = 0;
        }
    }
}
