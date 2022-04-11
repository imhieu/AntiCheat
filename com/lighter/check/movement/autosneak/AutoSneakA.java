package com.lighter.check.movement.autosneak;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;

public class AutoSneakA extends MovementCheck
{
    int vl;
    
    public AutoSneakA() {
        super(CheckType.AUTOSNEAKA, "A", "AutoSneak", CheckVersion.RELEASE);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() / 2 && playerData.isSpawnedIn()) {
            if (player.isSneaking() && player.isSprinting() && playerData.getSneaking() && playerData.getSprinting()) {
                if (++this.vl > 10) {
                    this.vl = 5;
                    AlertsManager.getInstance().handleViolation(playerData, this, "sneak & sprint", true);
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
}
