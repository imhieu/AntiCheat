package com.lighter.check.combat.autoclicker;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import com.lighter.data.manager.*;

public class AutoClickerM extends PacketCheck
{
    int passed;
    int stage;
    double vl;
    int movements;
    int failed;
    
    public AutoClickerM() {
        super(CheckType.AUTO_CLICKERM, "M", "Auto Clicker", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.stage == 2) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
            ++this.movements;
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            if (this.stage == 0 || this.stage == 1) {
                ++this.stage;
            }
            else {
                this.stage = 1;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            if (this.stage == 3) {
                ++this.failed;
            }
            else {
                ++this.passed;
            }
            if (this.movements >= 200 && this.failed + this.passed > 60) {
                final double n2 = (this.passed == 0) ? -1.0 : (this.failed / this.passed);
                if (n2 > 2.5) {
                    final double vl = this.vl + (1.0 + (n2 - 2.0) * 0.75);
                    this.vl = vl;
                    if (vl >= 4.0 && player.getGameMode() != GameMode.CREATIVE) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("rat:").append(n2)), false);
                    }
                }
                else {
                    this.vl -= 2.0;
                }
                this.movements = 0;
                this.passed = 0;
                this.failed = 0;
            }
        }
    }
}
