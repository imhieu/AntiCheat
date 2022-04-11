package com.lighter.check.movement.speed;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.data.manager.*;
import org.bukkit.*;

public class SpeedG extends MovementCheck
{
    double lastDist;
    boolean lastOnGround;
    int vl;
    
    public SpeedG() {
        super(CheckType.SPEEDG, "G", "Speed", CheckVersion.RELEASE);
        this.violations = 1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL) && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() && playerData.isSpawnedIn()) {
            final World world = player.getWorld();
            final double n2 = playerLocation2.getX() - playerLocation.getX();
            final double n3 = playerLocation2.getZ() - playerLocation.getZ();
            final double lastDist = n2 * n2 + n3 * n3;
            final double lastDist2 = this.lastDist;
            this.lastDist = lastDist;
            final boolean booleanValue = playerLocation2.getOnGround();
            final boolean lastOnGround = this.lastOnGround;
            this.lastOnGround = booleanValue;
            final double n4 = (lastDist - lastDist2 * 0.9100000262260437) * 130.0;
            if (!booleanValue && !lastOnGround && !playerLocation.getOnGround()) {
                if (n4 >= 1.0 && ++this.vl > 3 + (playerData.hasLag() ? 5 : 1)) {
                    this.vl = 0;
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("b-hop distance:").append(new DecimalFormat("#.###").format(n4))), false);
                    if (OptionsManager.getInstance().isRollback() && this.violations >= 2.0) {
                        playerData.cancelMove(player, playerLocation, world);
                    }
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
}
