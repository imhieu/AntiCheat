package com.lighter.check.movement.fly;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import java.text.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;

public class FlyJ extends MovementCheck
{
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() / 2 && playerData.isSpawnedIn()) {
            final double n2 = playerLocation2.getY() - playerLocation.getY();
            final double n3 = 0.9;
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            if (n2 > n3) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("step:").append(decimalFormat.format(n2))), false);
                if (OptionsManager.getInstance().isRollback()) {
                    playerData.cancelMove(player, playerLocation, player.getWorld());
                }
            }
        }
    }
    
    public FlyJ() {
        super(CheckType.FLYJ, "J", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
