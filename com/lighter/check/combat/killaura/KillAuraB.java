package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import java.text.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class KillAuraB extends PacketCheck
{
    private float yawSpeed;
    private Float lastYaw;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInLook) {
            final PacketPlayInFlying.PacketPlayInLook packetPlayInLook = (PacketPlayInFlying.PacketPlayInLook)packet;
            if (this.lastYaw != null && n - playerData.getLastPosition() > 3500L) {
                final float abs = Math.abs(this.lastYaw - packetPlayInLook.d());
                final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                if (this.yawSpeed < 45.0f && playerData.getTeleportTicks() > 5) {
                    if (abs > 345.0f && abs < 375.0f) {
                        if (Math.abs(Math.abs(360.0f - (abs + Math.abs(180.0f - Math.abs(packetPlayInLook.d() % 180.0f - this.lastYaw % 180.0f))))) == 0.0) {
                            AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ychange:").append(decimalFormat.format(abs))), false);
                        }
                    }
                    else if (abs > 172.5 && abs < 187.5 && Math.abs(Math.abs(180.0f - (abs + Math.abs(90.0f - Math.abs(packetPlayInLook.d() % 90.0f - this.lastYaw % 90.0f))))) == 0.0) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ychange:").append(decimalFormat.format(abs))), false);
                    }
                    this.violations -= Math.min(this.violations + 2.0, 0.1);
                }
                this.yawSpeed *= 3.0f;
                this.yawSpeed += abs;
                this.yawSpeed /= 4.0f;
            }
            this.lastYaw = packetPlayInLook.d();
        }
    }
    
    public KillAuraB() {
        super(CheckType.KILL_AURAB, "B", "Kill Aura", CheckVersion.EXPERIMENTAL);
        this.lastYaw = null;
        this.yawSpeed = 360.0f;
        this.violations = -2.0;
    }
}
