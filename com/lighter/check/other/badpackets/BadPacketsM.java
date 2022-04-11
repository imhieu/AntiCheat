package com.lighter.check.other.badpackets;

import java.util.*;
import com.lighter.data.*;
import com.lighter.check.impl.*;
import com.google.common.cache.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class BadPacketsM extends PacketCheck
{
    int swings;
    long lastSwitch;
    Cache<UUID, Integer> limit;
    int look;
    int vl;
    int switches;
    int use;
    int places;
    
    public void handle(final PlayerData playerData, final String s) {
        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("packet: ").append(s)), true);
    }
    
    private boolean checkLimit(final PlayerData playerData, final Integer n) {
        if (n != null && n >= 2 && this.places >= 4) {
            if (++this.vl > 1) {
                playerData.fuckOff("PacketPlayInBlockPlace");
            }
            this.handle(playerData, "PacketPlayInBlockPlace");
            return true;
        }
        return false;
    }
    
    public BadPacketsM() {
        super(CheckType.BADPACKETSM, "C", "ServerCrasher", CheckVersion.RELEASE);
        this.limit = (Cache<UUID, Integer>)CacheBuilder.newBuilder().concurrencyLevel(2).initialCapacity(10).expireAfterWrite(1L, TimeUnit.SECONDS).build();
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long lastSwitch) {
        if (OptionsManager.getInstance().isPacketEnabled()) {
            if (packet instanceof PacketPlayInFlying) {
                this.places = 0;
                this.swings = 0;
                this.look = 0;
                this.use = 0;
            }
            else if (packet instanceof PacketPlayInArmAnimation) {
                if (this.swings++ > OptionsManager.getInstance().getPacketPerSecondLog()) {
                    this.handle(playerData, "PacketPlayInArmAnimation");
                }
                if (this.swings > OptionsManager.getInstance().getPacketPerSecondBan()) {
                    playerData.fuckOff("PacketPlayInArmAnimation");
                }
            }
            else if (packet instanceof PacketPlayInBlockPlace) {
                if (this.places++ > OptionsManager.getInstance().getPacketPerSecondLog()) {
                    this.handle(playerData, "PacketPlayInBlockPlace");
                }
                if (this.places > OptionsManager.getInstance().getPacketPerSecondBan()) {
                    playerData.fuckOff("PacketPlayInBlockPlace");
                }
                final Integer n = (Integer)this.limit.getIfPresent((Object)player.getUniqueId());
                if (this.checkLimit(playerData, n)) {
                    return;
                }
                this.limit.put((Object)player.getUniqueId(), (Object)((n == null) ? 0 : (n + 1)));
            }
            else if (packet instanceof PacketPlayInHeldItemSlot) {
                if (lastSwitch - this.lastSwitch > 100L) {
                    this.switches = 0;
                    this.lastSwitch = lastSwitch;
                }
                if (this.switches++ > OptionsManager.getInstance().getPacketPerSecondLog()) {
                    this.handle(playerData, "PacketPlayInHeldItemSlot");
                }
                if (this.switches++ > OptionsManager.getInstance().getPacketPerSecondBan()) {
                    playerData.fuckOff("PacketPlayInHeldItemSlot");
                }
            }
            else if (packet instanceof PacketPlayInEntityAction) {
                if (this.look++ > OptionsManager.getInstance().getPacketPerSecondLog()) {
                    this.handle(playerData, "PacketPlayInEntityAction");
                }
                if (this.look == OptionsManager.getInstance().getPacketPerSecondBan()) {
                    playerData.fuckOff("PacketPlayInEntityAction");
                }
            }
            else if (packet instanceof PacketPlayInUseEntity) {
                if (this.use++ > OptionsManager.getInstance().getPacketPerSecondLog()) {
                    this.handle(playerData, "PacketPlayInUseEntity");
                }
                if (this.use == OptionsManager.getInstance().getPacketPerSecondBan()) {
                    playerData.fuckOff("PacketPlayInUseEntity");
                }
            }
        }
    }
}
