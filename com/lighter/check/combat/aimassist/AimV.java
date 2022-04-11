package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;

public class AimV extends AimCheck
{
    private int buffer;
    
    public AimV() {
        super(CheckType.AIM_ASSISTV, "V", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float abs = Math.abs(playerLocation2.getPitch() - playerLocation.getPitch());
        if (abs >= 1.0f && abs % 0.1f == 0.0f) {
            if (abs % 1.0f == 0.0f || abs % 10.0f == 0.0f || abs % 30.0f == 0.0f) {
                ++this.buffer;
            }
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("buffer:").append(decimalFormat.format(this.buffer)).append(",pchange:").append(decimalFormat.format(abs))), false);
        }
    }
}
