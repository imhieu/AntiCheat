package com.lighter.check.movement.scaffold;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class ScaffoldB extends PacketCheck
{
    private int looks;
    private double vl;
    private int stage;
    
    public ScaffoldB() {
        super(CheckType.MISCD, "B", "Scaffold", CheckVersion.RELEASE);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInLook) {
            if (this.stage == 0) {
                ++this.stage;
            }
            else if (this.stage == 4) {
                final double vl = this.vl + 1.75;
                this.vl = vl;
                if (vl > 3.5) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(this.stage)), false);
                }
                this.stage = 0;
            }
            else {
                this.looks = 0;
                this.stage = 0;
                this.vl -= 0.2;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            if (this.stage == 1) {
                ++this.stage;
            }
            else {
                this.looks = 0;
                this.stage = 0;
            }
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            if (this.stage == 2) {
                ++this.stage;
            }
            else {
                this.looks = 0;
                this.stage = 0;
                this.vl -= 0.2;
            }
        }
        else if (packet instanceof PacketPlayInFlying.PacketPlayInPosition) {
            if (this.stage == 3) {
                if (++this.looks == 3) {
                    this.stage = 4;
                    this.looks = 0;
                }
            }
            else {
                this.looks = 0;
                this.stage = 0;
            }
        }
    }
}
