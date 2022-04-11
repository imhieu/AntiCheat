package com.lighter.check.combat.autoclicker;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.util.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerU extends PacketCheck
{
    private int ticks;
    private double lastDelay;
    private int lastTicks;
    private int vl;
    
    public AutoClickerU() {
        super(CheckType.AUTO_CLICKERU, "U", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInArmAnimation) {
            final int ticks = this.ticks;
            this.ticks = 0;
            final int lastTicks = this.lastTicks;
            this.lastTicks = ticks;
            final double lastDelay = Math.abs(ticks - lastTicks);
            final double lastDelay2 = this.lastDelay;
            this.lastDelay = lastDelay;
            try {
                final double n2 = Math.IEEEremainder((double)MathUtil.lcd((long)lastDelay, (long)lastDelay2), lastDelay2) / 3.141592653589793;
                if (Double.isNaN(n2)) {
                    if (this.vl++ > 2) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("remainder :").append(n2)), false);
                    }
                }
                else {
                    this.vl -= (int)((this.vl > 0) ? 0.5 : 0.0);
                }
            }
            catch (Exception ex) {}
        }
        else if (packet instanceof PacketPlayInFlying) {
            ++this.ticks;
        }
    }
}
