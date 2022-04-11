package com.lighter.check.combat.hitboxes;

import com.lighter.check.impl.*;
import java.text.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import com.lighter.data.*;

public class HitBoxesA extends ReachCheck
{
    int vl;
    
    public HitBoxesA() {
        super(CheckType.HITBOXESA, "A", "HitBoxes", CheckVersion.RELEASE);
        this.violations = -2.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final ReachData reachData, final long n) {
        final DistanceData distanceData = reachData.getDistanceData();
        final double n2 = distanceData.getX() / distanceData.getDist() * reachData.getExtra();
        final double n3 = distanceData.getZ() / distanceData.getDist() * reachData.getExtra();
        final DecimalFormat decimalFormat = new DecimalFormat("#.###");
        if (!playerData.hasLag() && Math.max(Math.abs(n2), Math.abs(n3)) > 0.55) {
            if ((!player.isInsideVehicle() || !(player.getVehicle() instanceof Boat)) && ++this.vl > 2 + playerData.getPingTicks() / 3) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("x:").append(decimalFormat.format(n2)).append(",z:").append(decimalFormat.format(n3)).append(",e:").append(decimalFormat.format(reachData.getExtra()))), false);
            }
        }
        else {
            this.violations -= Math.min(this.violations + 2.0, 0.1);
            this.vl = 0;
        }
    }
}
