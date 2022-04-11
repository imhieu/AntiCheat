package com.lighter.check.combat.aimassist;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AimS extends AimCheck
{
    private double streak;
    private double oldPitch;
    private double vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float distanceBetweenAngles = this.getDistanceBetweenAngles(playerLocation2.getPitch(), playerLocation.getPitch());
        final float distanceBetweenAngles2 = this.getDistanceBetweenAngles(playerLocation2.getYaw(), playerLocation.getYaw());
        final double n2 = this.oldPitch - distanceBetweenAngles;
        if (distanceBetweenAngles2 >= 2.0f) {
            if (distanceBetweenAngles + n2 == 0.0 && distanceBetweenAngles < 0.07 && distanceBetweenAngles > 0.0f) {
                ++this.vl;
                this.streak = Math.max(0.0, this.streak - 0.25);
                if (this.streak > 1.0) {
                    final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(distanceBetweenAngles2)).append(",pdiff:").append(decimalFormat.format(distanceBetweenAngles))), false);
                    this.streak = 0.0;
                }
            }
            else {
                this.vl = Math.max(0.0, this.vl - 0.5);
                this.streak = Math.max(0.0, this.streak - 0.25);
            }
        }
        this.oldPitch = distanceBetweenAngles;
    }
    
    public AimS() {
        super(CheckType.AIM_ASSISTS, "S", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    private float getDistanceBetweenAngles(final float n, final float n2) {
        float n3 = Math.abs(n - n2) % 360.0f;
        if (n3 > 180.0f) {
            n3 = 360.0f - n3;
        }
        return n3;
    }
}
