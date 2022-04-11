package com.lighter.check.combat.reach;

import com.lighter.check.impl.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.event.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import org.bukkit.event.block.*;

public class ReachC extends EventCheck implements Listener
{
    public ReachC() {
        super(CheckType.REACHC, "A", "Bad Range", CheckVersion.EXPERIMENTAL);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)Main.getPlugin());
        this.violations = -1.0;
    }
    
    private double calcDistance(final PlayerLocation playerLocation, final Location location) {
        return Math.abs(playerLocation.getX() - location.getX()) + Math.abs(playerLocation.getZ() - location.getZ());
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Event event, final long n) {
        if (event instanceof BlockPlaceEvent && player.getGameMode() != GameMode.CREATIVE) {
            if (!((BlockPlaceEvent)event).getBlock().getType().isSolid()) {
                return;
            }
            final double calcDistance = this.calcDistance(playerData.getLocation(), ((BlockPlaceEvent)event).getBlock().getLocation());
            if (calcDistance >= 7.5) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("place range: ").append(calcDistance)), true);
            }
        }
        else if (event instanceof BlockBreakEvent && player.getGameMode() != GameMode.CREATIVE) {
            if (!((BlockBreakEvent)event).getBlock().getType().isSolid()) {
                return;
            }
            final double calcDistance2 = this.calcDistance(playerData.getLastLastLocation(), ((BlockBreakEvent)event).getBlock().getLocation());
            if (calcDistance2 >= 5.0) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("break range: ").append(calcDistance2)), true);
            }
        }
    }
}
