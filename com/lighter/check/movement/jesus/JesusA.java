package com.lighter.check.movement.jesus;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import org.bukkit.*;

public class JesusA extends MovementCheck
{
    private int vl;
    
    private void lambda$handle$1(final Player player, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final PlayerData playerData) {
        final Material type = player.getWorld().getBlockAt(player.getLocation().subtract(0.0, 1.0, 0.0)).getType();
        boolean b = false;
        boolean b2 = false;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                final Material type2 = player.getWorld().getBlockAt(player.getLocation().add((double)i, -0.01, (double)j)).getType();
                if (type2 == Material.WATER_LILY) {
                    b = true;
                }
                else if (type2.equals((Object)Material.CARPET)) {
                    b = true;
                }
            }
        }
        for (int k = -2; k < 2; ++k) {
            for (int l = -1; l < 2; ++l) {
                for (int n = -2; n < 2; ++n) {
                    if (player.getWorld().getBlockAt(player.getLocation().add((double)k, (double)l, (double)n)).getType().isSolid()) {
                        b2 = true;
                    }
                }
            }
        }
        if ((type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.LAVA || type == Material.STATIONARY_LAVA) && !player.isFlying() && !b2 && !b && !player.getWorld().getBlockAt(player.getLocation().add(0.0, 1.0, 0.0)).isLiquid() && (String.valueOf(Math.abs(playerLocation.getY() - playerLocation2.getY())).contains("00000000") || playerLocation2.getY() == playerLocation.getY())) {
            if (++this.vl > 5) {
                this.vl = 2;
                AlertsManager.getInstance().handleViolation(playerData, this, "floating above", false);
            }
        }
        else {
            this.vl = 0;
        }
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
        if (!player.getAllowFlight() && player.getGameMode() != GameMode.CREATIVE) {
            this.run(this::lambda$handle$1);
        }
    }
    
    public JesusA() {
        super(CheckType.JESUSA, "A", "Jesus", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
