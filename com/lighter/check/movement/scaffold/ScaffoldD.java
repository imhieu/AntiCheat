package com.lighter.check.movement.scaffold;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import org.bukkit.*;

public class ScaffoldD extends PacketCheck
{
    private Location lastLoc;
    int preVL;
    
    private void lambda$handle$52(final Player player, final PlayerData playerData, final PacketPlayInBlockPlace packetPlayInBlockPlace) {
        final Location lastLoc = this.lastLoc;
        this.lastLoc = player.getLocation();
        if (lastLoc != null && player.getLocation() != null) {
            final double distance = player.getLocation().toVector().clone().setY(0.0).distance(lastLoc.toVector().clone().setY(0.0));
            if ((playerData.getSneaking() != null && playerData.getSneaking()) || !this.isNearGround(player.getLocation()) || distance < 0.2) {
                return;
            }
            if (player.getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType().isSolid() && !player.getLocation().subtract(0.0, 2.0, 0.0).getBlock().getType().isSolid() && player.getLocation().getBlockY() > packetPlayInBlockPlace.a().getY() && this.preVL++ >= 2) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("y:").append(packetPlayInBlockPlace.a().getY())), false);
            }
        }
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (CheckManager.getInstance().enabled(this.getType()) && packet instanceof PacketPlayInBlockPlace) {
            this.run(this::lambda$handle$52);
        }
    }
    
    public boolean isNearGround(final Location location) {
        for (double n = 0.3, n2 = -n; n2 <= n; n2 += n) {
            for (double n3 = -n; n3 <= n; n3 += n) {
                if (location.clone().add(n2, -0.5001, n3).getBlock().getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public ScaffoldD() {
        super(CheckType.MISCF, "D", "Scaffold", CheckVersion.RELEASE);
        this.preVL = 0;
        this.violations = -1.0;
    }
}
