package com.lighter.check.combat.killaura;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import java.text.*;
import com.lighter.data.manager.*;

public class KillAuraH extends PacketCheck
{
    double lastyawDiff;
    int vl;
    
    public KillAuraH() {
        super(CheckType.KILL_AURAH, "H", "Kill Aura", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            final DecimalFormat decimalFormat = new DecimalFormat("#.###");
            final double lastyawDiff = playerData.getLocation().getYaw() - playerData.getLastLastLocation().getYaw();
            final double lastyawDiff2 = this.lastyawDiff;
            this.lastyawDiff = lastyawDiff;
            final int n2 = (int)(Math.IEEEremainder(lastyawDiff, lastyawDiff2) * Math.hypot(lastyawDiff, lastyawDiff2) / 3.141592653589793);
            if ((n2 < -2 && n2 > -7) || (n2 < -10 && n2 > -15 && Math.abs(lastyawDiff) > 0.0)) {
                if (++this.vl > 3) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("yawDiff:").append(decimalFormat.format(lastyawDiff)).append(",offset:").append(n2)), false);
                }
            }
            else {
                this.vl = 0;
            }
        }
    }
}
