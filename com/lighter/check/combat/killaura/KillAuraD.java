package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import java.text.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import org.bukkit.*;

public class KillAuraD extends PacketCheck
{
    int vl;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            if (playerData.getLastAttacked() != null && !playerData.hasLag()) {
                final Player player2 = playerData.getLastAttacked().getPlayer();
                final double n2 = Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw());
                final double distanceBetweenAngles360 = MathUtil.getDistanceBetweenAngles360(player.getLocation().getYaw(), this.getDirection(player.getLocation(), player2.getLocation()));
                if (distanceBetweenAngles360 < 0.7 && n2 > 2.0) {
                    if (this.vl++ > 5) {
                        this.vl = 0;
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("angle:").append(decimalFormat.format(distanceBetweenAngles360)).append(",rotation:").append(n2)), false);
                    }
                }
                else {
                    this.vl = 0;
                }
            }
        }
    }
    
    public KillAuraD() {
        super(CheckType.KILL_AURAD, "D", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    private double getDirection(final Location location, final Location location2) {
        if (location == null || location2 == null) {
            return 0.0;
        }
        return (float)(Math.atan2(location2.getZ() - location.getZ(), location2.getX() - location.getX()) * 180.0 / 3.141592653589793 - 90.0);
    }
}
