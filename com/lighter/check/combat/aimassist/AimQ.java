package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import java.util.*;
import com.lighter.data.manager.*;

public class AimQ extends AimCheck
{
    private float lastYawChange;
    
    public AimQ() {
        super(CheckType.AIM_ASSISTQ, "Q", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final DecimalFormat decimalFormat = new DecimalFormat("#.###");
        if (playerData.getLastAttackTicks() < 3) {
            final float abs = Math.abs(playerLocation.getYaw() - playerLocation2.getYaw());
            final int round;
            if (abs > 1.0f && (round = Math.round(abs)) == abs) {
                if (Objects.equals(abs, this.lastYawChange)) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(abs)).append(",lastYawChange:").append(decimalFormat.format(this.lastYawChange))), false);
                }
                this.lastYawChange = (float)round;
            }
            else {
                this.lastYawChange = 0.0f;
            }
        }
    }
}
