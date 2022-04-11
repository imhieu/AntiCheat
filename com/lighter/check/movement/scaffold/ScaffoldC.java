package com.lighter.check.movement.scaffold;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class ScaffoldC extends PacketCheck
{
    private double vl;
    private long lastPlace;
    private boolean place;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.place) {
                final long n2 = System.currentTimeMillis() - this.lastPlace;
                if (n2 >= 25L) {
                    final double vl = this.vl + 1.0;
                    this.vl = vl;
                    if (vl >= 10.0) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("time:").append(n2)), false);
                    }
                }
                else {
                    this.vl -= 0.25;
                }
                this.place = false;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            if (System.currentTimeMillis() - playerData.getLastMovePacket().getTimestamp() <= 25.0) {
                this.lastPlace = System.currentTimeMillis();
                this.place = true;
            }
            else {
                this.vl -= 0.25;
            }
        }
    }
    
    public ScaffoldC() {
        super(CheckType.MISCE, "C", "Scaffold", CheckVersion.EXPERIMENTAL);
    }
}
