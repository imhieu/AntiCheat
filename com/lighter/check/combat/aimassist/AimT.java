package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;

public class AimT extends AimCheck
{
    private int buffer;
    
    public AimT() {
        super(CheckType.AIM_ASSISTT, "T", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float abs = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw());
        if (abs >= 1.0f && abs % 0.1f == 0.0f) {
            if (abs % 1.0f == 0.0f || abs % 10.0f == 0.0f || abs % 30.0f == 0.0f) {
                ++this.buffer;
            }
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("buffer:").append(decimalFormat.format(this.buffer)).append(",ychange:").append(decimalFormat.format(abs))), false);
        }
    }
}
