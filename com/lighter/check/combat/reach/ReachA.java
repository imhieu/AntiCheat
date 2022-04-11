package com.lighter.check.combat.reach;

import java.text.*;
import java.util.function.*;
import com.lighter.data.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class ReachA extends ReachCheck
{
    private static final ThreadLocal<DecimalFormat> REACH_FORMAT;
    private int vl;
    
    static {
        REACH_FORMAT = ThreadLocal.withInitial((Supplier<? extends DecimalFormat>)ReachA::lambda$static$74);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final ReachData reachData, final long n) {
        final double reach = reachData.getReach();
        final DecimalFormat decimalFormat = ReachA.REACH_FORMAT.get();
        if (player.isInsideVehicle() && player.getVehicle() instanceof Boat) {
            return;
        }
        if (reach > 3.075) {
            if (++this.vl > 2) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("range: ").append(decimalFormat.format(reach))), true);
            }
        }
        else {
            this.violations -= Math.min(this.violations + 4.0, 0.01);
            this.vl = 0;
        }
    }
    
    private static DecimalFormat lambda$static$74() {
        return new DecimalFormat("#.##");
    }
    
    public ReachA() {
        super(CheckType.REACHA, "A", "Reach", CheckVersion.RELEASE);
        this.violations = -4.0;
    }
}
