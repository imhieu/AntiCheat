package com.lighter.check.combat.reach;

import java.text.*;
import com.lighter.data.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import java.util.function.*;

public class ReachD extends ReachCheck
{
    private int vl;
    private static final ThreadLocal<DecimalFormat> REACH_FORMAT;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final ReachData reachData, final long n) {
        final double reach = reachData.getReach();
        final DecimalFormat decimalFormat = ReachD.REACH_FORMAT.get();
        if ((player.isInsideVehicle() && player.getVehicle() instanceof Boat) || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.ICE) {
            return;
        }
        if (reach > 3.079) {
            if (++this.vl > 2) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("range: ").append(decimalFormat.format(reach))), true);
            }
        }
        else {
            this.vl = 0;
        }
    }
    
    static {
        REACH_FORMAT = ThreadLocal.withInitial((Supplier<? extends DecimalFormat>)ReachD::lambda$static$51);
    }
    
    private static DecimalFormat lambda$static$51() {
        return new DecimalFormat("#.##");
    }
    
    public ReachD() {
        super(CheckType.REACHD, "B", "Bad Range", CheckVersion.EXPERIMENTAL);
    }
}
