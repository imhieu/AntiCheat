package com.lighter.check.combat.killaura;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;

public class KillAuraI extends AimCheck
{
    private int number;
    
    public KillAuraI() {
        super(CheckType.KILL_AURAI, "I", "KillAura", CheckVersion.RELEASE);
        this.number = 0;
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerData.getLastAttacked() != null && playerData.getLastAttackTicks() <= 6 && playerData.getTotalTicks() > 100) {
            final PlayerLocation location = playerData.getLocation();
            final PlayerLocation location2 = playerData.getLastAttacked().getLocation(playerData.getPingTicks());
            if (location2 != null) {
                final double distanceBetweenAngles = MathUtil.getDistanceBetweenAngles(location.getYaw(), MathUtil.getRotationFromPosition(location, location2)[0]);
                final double min;
                if ((min = Math.min(180.0 - distanceBetweenAngles, distanceBetweenAngles)) < 15.0) {
                    ++this.number;
                }
                else {
                    if (min > 30.0 && Math.abs(playerLocation.getYaw() - playerLocation2.getYaw()) > 30.0f && this.number > 6 && playerData.getLastAttackTicks() <= 3) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("angle:").append(min)), false);
                    }
                    else {
                        this.violations -= Math.min(this.violations + 1.0, 0.05);
                    }
                    this.number = 0;
                }
            }
        }
    }
}
