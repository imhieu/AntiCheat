package com.lighter.check.movement.speed;

import org.bukkit.entity.*;
import com.lighter.data.*;
import java.text.*;
import com.lighter.check.impl.*;
import com.lighter.data.manager.*;
import org.bukkit.*;
import com.lighter.util.*;

public class SpeedE extends MovementCheck
{
    private Double lastAngle;
    private int threshold;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (player.isSprinting() && playerLocation2.getOnGround() && playerLocation.getOnGround() && !playerData.hasLag() && !playerData.isTeleporting() && playerData.getVelocityTicks() > playerData.getPingTicks() && player.getGameMode().equals((Object)GameMode.SURVIVAL)) {
            final double degrees = Math.toDegrees(-Math.atan2(playerLocation2.getX() - playerLocation.getX(), playerLocation2.getZ() - playerLocation.getZ()));
            final double min = Math.min(MathUtil.getDistanceBetweenAngles360(degrees, playerLocation2.getYaw()), MathUtil.getDistanceBetweenAngles360(degrees, playerLocation.getYaw()));
            if (this.lastAngle != null) {
                final double distanceBetweenAngles360 = MathUtil.getDistanceBetweenAngles360(this.lastAngle, min);
                final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                if (min > 50.0) {
                    if (distanceBetweenAngles360 < 5.0) {
                        if (this.threshold++ > 20) {
                            this.run(this::lambda$handle$24);
                            this.threshold = 0;
                        }
                    }
                    else {
                        this.threshold = 0;
                    }
                }
                else {
                    this.threshold = 0;
                }
            }
            this.lastAngle = min;
            return;
        }
        this.lastAngle = null;
        this.threshold = 0;
    }
    
    public SpeedE() {
        super(CheckType.SPEEDE, "E", "Speed", CheckVersion.EXPERIMENTAL);
        this.lastAngle = null;
        this.threshold = 0;
        this.violations = -1.0;
    }
    
    private void lambda$handle$24(final Cuboid cuboid, final World world, final PlayerData playerData, final DecimalFormat decimalFormat, final double n, final double n2, final Player player, final PlayerLocation playerLocation) {
        if (cuboid.checkBlocks(world, SpeedE::lambda$null$23)) {
            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append(decimalFormat.format(n)).append(" - ").append(decimalFormat.format(n2))), false);
            if (OptionsManager.getInstance().isRollback()) {
                playerData.cancelMove(player, playerLocation, world);
            }
        }
        else {
            this.threshold = -20;
        }
    }
    
    private static boolean lambda$null$23(final Material material) {
        return !MaterialList.BAD_VELOCITY.contains(material);
    }
}
