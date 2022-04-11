package com.lighter.check.movement.fly;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.*;

public class FlyM extends MovementCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        final double n2 = playerLocation.getY() - playerLocation2.getY();
        final World world = player.getWorld();
        if (n2 > 50.0 && playerData.isOnGround() && !playerData.isTeleporting() && !player.getAllowFlight()) {
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            if (++this.vl > 5) {
                this.vl = 0;
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(n2))), false);
                if (OptionsManager.getInstance().isRollback() && this.violations >= 1.0) {
                    playerData.cancelMove(player, playerLocation, world);
                }
            }
        }
    }
    
    public FlyM() {
        super(CheckType.FLYM, "M", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
