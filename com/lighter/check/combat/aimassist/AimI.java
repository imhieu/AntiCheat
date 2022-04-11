package com.lighter.check.combat.aimassist;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.util.*;
import java.text.*;
import com.lighter.data.manager.*;

public class AimI extends PacketCheck
{
    int vl;
    double previous;
    
    public AimI() {
        super(CheckType.AIM_ASSISTI, "I", "Aim Assist", CheckVersion.EXPERIMENTAL);
        this.violations = -5.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (playerData.getLastAttackTicks() < 50 && packet instanceof PacketPlayInFlying) {
            final double previous = Math.abs(playerData.getLocation().getPitch() - playerData.getLastLastLocation().getPitch());
            final double n2 = Math.abs(playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw());
            final double gcd = MathUtil.gcd(16384.0, previous * Math.pow(2.0, 24.0), this.previous * Math.pow(2.0, 24.0));
            if (Math.atan2(previous, n2) < 0.008 && Math.atan2(previous, n2) > 0.0 && gcd < 131072.0) {
                if (this.vl++ > 6) {
                    this.vl = 2;
                    final DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ydiff:").append(decimalFormat.format(n2)).append(",pdiff:").append(decimalFormat.format(previous)).append(",gcd:").append(gcd)), false);
                }
            }
            else {
                this.violations -= Math.min(this.violations + 5.0, 0.01);
                if (this.vl > 4) {
                    --this.vl;
                }
            }
            this.previous = previous;
        }
    }
}
