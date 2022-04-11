package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;

public class AimA extends AimCheck
{
    public AimA() {
        super(CheckType.AIM_ASSISTA, "A", "Aim Assist", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float abs = Math.abs(playerLocation.getYaw() - playerLocation2.getYaw());
        if (abs >= 1.0f && abs % 0.1f == 0.0f) {
            if (abs % 1.0f == 0.0f) {
                ++this.violations;
            }
            if (abs % 10.0f == 0.0f) {
                ++this.violations;
            }
            if (abs % 30.0f == 0.0f) {
                ++this.violations;
            }
            AlertsManager.getInstance().handleViolation(playerData, this, "Aimbot Yaw", true);
        }
        final float abs2 = Math.abs(playerLocation.getPitch() - playerLocation2.getPitch());
        if (abs2 >= 1.0f && abs2 % 0.1f == 0.0f) {
            if (abs2 % 1.0f == 0.0f) {
                ++this.violations;
            }
            if (abs2 % 10.0f == 0.0f) {
                ++this.violations;
            }
            if (abs2 % 30.0f == 0.0f) {
                ++this.violations;
            }
            AlertsManager.getInstance().handleViolation(playerData, this, "Aimbot Pitch", true);
        }
    }
}
