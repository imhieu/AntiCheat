package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimX extends AimCheck
{
    float yawDelta;
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getLastAttackTicks() < 15) {
            final float abs = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw());
            final float abs2 = Math.abs(playerLocation2.getPitch() - playerLocation.getPitch());
            if (abs > 2.0f && abs2 < 0.01 && abs % 0.2f == 0.0f) {
                final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("pchange:").append(decimalFormat.format(abs2)).append(",ychange:").append(decimalFormat.format(abs))), false);
            }
            final float n2 = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw()) % 180.0f;
            this.yawDelta = playerLocation2.getYaw() - playerLocation.getYaw();
            if (n2 > 1.0f && Math.round(n2) == n2 && !playerData.hasLag()) {
                if (n2 == this.yawDelta) {
                    if (++this.vl > playerData.getPingTicks()) {
                        AlertsManager.getInstance().handleViolation(playerData, this, "Aimbot Pattern", true);
                    }
                    this.yawDelta = (float)Math.round(n2);
                }
                else {
                    this.vl = 0;
                }
            }
            else {
                this.yawDelta = 0.0f;
            }
            if (abs >= 1.0f && abs % 0.1f == 0.0f) {
                AlertsManager.getInstance().handleViolation(playerData, this, "Aimbot Aura 1", true);
            }
            if (abs2 >= 1.0f && abs2 % 0.1f == 0.0f) {
                AlertsManager.getInstance().handleViolation(playerData, this, "Aimbot Aura 2", true);
            }
        }
    }
    
    public AimX() {
        super(CheckType.AIM_ASSISTX, "X", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
}
