package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class KillAuraJ extends PacketCheck
{
    private int vl;
    private double lastRange;
    private double lastDiff;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInUseEntity) {
            final PlayerData lastAttacked = playerData.getLastAttacked();
            if (lastAttacked != null && playerData.getLastAttackTicks() < 100) {
                final double distance = player.getLocation().clone().toVector().setY(0.0).distance(lastAttacked.getPlayer().getLocation().clone().toVector().setY(0.0));
                if (this.lastRange == 0.0) {
                    this.lastRange = distance;
                }
                if (distance <= this.lastRange) {
                    final double lastDiff = 6.283185307179586 * player.getLocation().clone().toVector().setY(0.0).distance(lastAttacked.getPlayer().getLocation().clone().toVector().setY(0.0)) / (distance * Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw()));
                    final double lastDiff2 = this.lastDiff;
                    this.lastDiff = lastDiff;
                    final double abs = Math.abs(lastDiff - lastDiff2);
                    if (abs > 0.0 && abs < 0.2) {
                        if (this.vl++ > 4) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("rotation:").append(abs)), false);
                        }
                    }
                    else {
                        this.vl = 0;
                    }
                }
            }
            else {
                this.lastRange = 0.0;
            }
        }
    }
    
    public KillAuraJ() {
        super(CheckType.KILL_AURAJ, "J", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
