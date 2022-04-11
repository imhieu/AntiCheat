package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;
import java.util.*;

public class AutoClickerR extends PacketCheck
{
    List<Boolean> longs;
    long lastClick;
    int ticks;
    boolean place;
    boolean bypass;
    List<Boolean> shorts;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.place || playerData.isDigging() || player.getGameMode() == GameMode.CREATIVE || this.bypass) {
                this.place = false;
                this.bypass = false;
                this.longs.clear();
                return;
            }
            ++this.ticks;
            if (this.ticks > 3) {
                return;
            }
            if (this.ticks >= 3) {
                this.longs.add(false);
                this.shorts.add(false);
            }
            else {
                this.shorts.add(true);
                this.longs.add(true);
            }
            if (this.longs.size() > 275) {
                if (!this.longs.contains(false)) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("longs:").append(this.longs.size())), false);
                }
                this.longs.clear();
            }
            if (this.shorts.size() <= 100) {
                this.shorts.clear();
                return;
            }
            if (!this.longs.contains(false)) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("shorts:").append(this.shorts.size())), false);
            }
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.place = true;
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            this.bypass = true;
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            this.ticks = 0;
            if (System.currentTimeMillis() - this.lastClick > 250L) {
                if (!this.longs.isEmpty()) {
                    this.longs.remove(this.longs.size() - 1);
                    if (this.longs.contains(false)) {
                        this.longs.clear();
                    }
                }
                if (!this.shorts.isEmpty()) {
                    this.shorts.remove(this.shorts.size() - 1);
                    if (this.shorts.contains(false)) {
                        this.shorts.clear();
                    }
                }
            }
            this.lastClick = System.currentTimeMillis();
        }
    }
    
    public AutoClickerR() {
        super(CheckType.AUTO_CLICKERR, "R", "Auto Clicker", CheckVersion.RELEASE);
        this.longs = new ArrayList<Boolean>();
        this.shorts = new ArrayList<Boolean>();
        this.violations = -1.0;
    }
}
