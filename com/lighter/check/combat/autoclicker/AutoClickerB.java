package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerB extends PacketCheck
{
    private int done;
    private boolean place;
    private int swings;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.done++ >= 19) {
                if (playerData.hasFast() || player.getGameMode() == GameMode.CREATIVE) {
                    this.swings = 0;
                    this.done = 0;
                }
                if (this.swings > OptionsManager.getInstance().getMaxCps()) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("cps: ").append(this.swings)), true);
                }
                else {
                    this.decreaseVL(0.1);
                }
                this.swings = 0;
                this.done = 0;
            }
            if (this.place) {
                this.place = false;
            }
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            if (!playerData.hasLag() && !playerData.hasFast() && playerData.isDigging() && !this.place) {
                ++this.swings;
            }
            if (this.place) {
                this.place = false;
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.place = true;
        }
    }
    
    public AutoClickerB() {
        super(CheckType.AUTO_CLICKERB, "B", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
