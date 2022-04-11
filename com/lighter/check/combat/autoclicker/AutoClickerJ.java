package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerJ extends PacketCheck
{
    boolean place;
    int acTypeAClicks;
    int done;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInArmAnimation && !playerData.isDigging() && !this.place) {
            ++this.acTypeAClicks;
        }
        else if (packet instanceof PacketPlayInFlying) {
            if (++this.done >= 20) {
                if (!playerData.hasLag() && this.acTypeAClicks >= OptionsManager.getInstance().getMaxCps() && player.getGameMode() != GameMode.CREATIVE) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("cps: ").append(this.acTypeAClicks)), true);
                }
                playerData.cps1[4] = playerData.cps1[3];
                playerData.cps1[3] = playerData.cps1[2];
                playerData.cps1[2] = playerData.cps1[1];
                playerData.cps1[1] = playerData.cps1[0];
                playerData.cps1[0] = this.acTypeAClicks;
                this.acTypeAClicks = 0;
                this.done = 0;
                if (this.place) {
                    this.place = false;
                }
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.place = true;
        }
    }
    
    public AutoClickerJ() {
        super(CheckType.AUTO_CLICKERJ, "J", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
