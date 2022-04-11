package com.lighter.check.movement.speed;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;

public class SpeedB extends MovementCheck
{
    private boolean ladder;
    private int lastLadderCheck;
    private int threshold;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (playerLocation2.getY() > playerLocation.getY() && !playerLocation2.getOnGround() && !playerLocation.getOnGround() && !player.getAllowFlight() && player.getGameMode().equals((Object)GameMode.SURVIVAL)) {
            if (this.ladder) {
                if (playerLocation2.getY() - playerLocation.getY() > 0.118) {
                    if (this.threshold++ > 2) {
                        this.threshold = 0;
                        this.run(this::lambda$handle$1);
                    }
                }
                else {
                    this.threshold = 0;
                }
            }
            else if (this.lastLadderCheck++ > 9) {
                this.lastLadderCheck = 0;
                this.run(this::lambda$handle$2);
            }
        }
        else {
            this.ladder = false;
        }
    }
    
    private void lambda$handle$2(final World world, final PlayerLocation playerLocation) {
        final Material type = world.getBlockAt((int)Math.floor(playerLocation.getX()), (int)Math.floor(playerLocation.getY() + 1.0), (int)Math.floor(playerLocation.getZ())).getType();
        if (type == Material.VINE || type == Material.LADDER) {
            this.ladder = true;
        }
    }
    
    public SpeedB() {
        super(CheckType.SPEEDB, "B", "Speed", CheckVersion.RELEASE);
        this.lastLadderCheck = 0;
        this.ladder = false;
        this.threshold = 0;
    }
    
    private void lambda$handle$1(final World world, final PlayerLocation playerLocation, final PlayerData playerData) {
        final Material type = world.getBlockAt((int)Math.floor(playerLocation.getX()), (int)Math.floor(playerLocation.getY() + 1.0), (int)Math.floor(playerLocation.getZ())).getType();
        if (type != Material.VINE && type != Material.LADDER) {
            this.ladder = false;
        }
        else {
            AlertsManager.getInstance().handleViolation(playerData, this, "fast ladder", false);
        }
    }
}
