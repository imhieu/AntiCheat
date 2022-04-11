package com.lighter.check.movement.misc;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class MiscB extends PacketCheck
{
    private int stage;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.stage == 2 && !((PacketPlayInFlying)packet).g()) {
                this.stage = 3;
                return;
            }
            if (this.stage == 3 && playerData.getLocation().getY() % 1.0 == 0.41999998688697815) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(this.stage).append(",y:").append(playerData.getLocation().getY() % 1.0)), false);
            }
            this.stage = 0;
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            final PacketPlayInBlockPlace packetPlayInBlockPlace = (PacketPlayInBlockPlace)packet;
            if (packetPlayInBlockPlace.a().getX() != -1 && packetPlayInBlockPlace.a().getY() != -1 && packetPlayInBlockPlace.a().getZ() != -1) {
                this.stage = 1;
                this.violations -= Math.min(this.violations + 10.0, 0.25);
            }
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            this.stage = 2;
        }
    }
    
    public MiscB() {
        super(CheckType.MISCB, "A", "Tower", CheckVersion.EXPERIMENTAL);
        this.stage = 0;
        this.violations = -10.0;
    }
}
