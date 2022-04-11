package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import java.util.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;

public class AimU extends AimCheck
{
    private int streak;
    private final Deque<Float> pitchSamples;
    
    public AimU() {
        super(CheckType.AIM_ASSISTU, "U", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.pitchSamples = new LinkedList<Float>();
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final float abs = Math.abs(playerLocation2.getYaw() - playerLocation.getYaw());
        final float abs2 = Math.abs(playerLocation2.getPitch() - playerLocation.getPitch());
        if (abs > 1.0f && abs2 > 0.0) {
            this.pitchSamples.add(abs2);
            if (this.pitchSamples.size() == 120) {
                if (this.pitchSamples.size() - this.pitchSamples.stream().distinct().count() <= 9) {
                    if (++this.streak >= 10) {
                        this.streak = 0;
                        final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(abs)).append(",pdiff:").append(decimalFormat.format(abs2))), false);
                    }
                }
                else {
                    this.streak = 0;
                }
                this.pitchSamples.clear();
            }
        }
    }
}
