package com.lighter.check.combat.autoclicker;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import java.util.*;
import org.bukkit.*;
import com.lighter.data.manager.*;

public class AutoClickerQ extends PacketCheck
{
    int animations;
    int lastAnimation;
    boolean place;
    int movePackets;
    List<Integer> swingList;
    long lastArmAnimation;
    
    public AutoClickerQ() {
        super(CheckType.AUTO_CLICKERQ, "Q", "Auto Clicker", CheckVersion.RELEASE);
        this.swingList = new ArrayList<Integer>();
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            ++this.movePackets;
            if (this.animations <= 0) {
                return;
            }
            if (this.movePackets < 20) {
                return;
            }
            if (System.currentTimeMillis() - this.lastArmAnimation > 200L) {
                return;
            }
            this.animations = 0;
            this.movePackets = 0;
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            this.place = true;
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            if (this.place || playerData.isDigging()) {
                this.animations = 0;
                this.movePackets = 0;
                this.swingList.clear();
                this.place = false;
                return;
            }
            ++this.animations;
            if (this.lastAnimation > this.animations) {
                this.swingList.add(this.lastAnimation);
                if (this.swingList.size() == 5) {
                    final int[] array = new int[5];
                    for (int i = 0; i < 5; ++i) {
                        final int intValue = this.swingList.get(i);
                        array[i] = ((i == 0) ? (intValue - 1) : intValue);
                    }
                    int n2 = 1;
                    for (int j = 0; j < 4; ++j) {
                        n2 += array[j] - array[j + 1];
                    }
                    final int abs = Math.abs(n2);
                    final double orElse = Arrays.stream(array).average().orElse(0.0);
                    if (abs == 1 && orElse > 8.0 && Math.round(orElse) == orElse && player.getGameMode() != GameMode.CREATIVE) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("average:").append(orElse)), false);
                    }
                    this.swingList.clear();
                }
            }
            this.lastArmAnimation = System.currentTimeMillis();
            this.lastAnimation = this.animations;
        }
    }
}
