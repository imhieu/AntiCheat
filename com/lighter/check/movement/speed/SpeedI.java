package com.lighter.check.movement.speed;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;

public class SpeedI extends MovementCheck
{
    int vl;
    
    public SpeedI() {
        super(CheckType.SPEEDI, "Dev", "Speed", CheckVersion.EXPERIMENTAL);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() && playerData.isSpawnedIn()) {
            if (playerData.getDeltaY() == -playerData.getLastDeltaY() && playerData.getDeltaY() != 0.0) {
                if (this.vl++ > 1) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, "repetitive vertical motions", false);
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
}
