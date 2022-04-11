package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class AutoClickerH extends PacketCheck
{
    int vl;
    private boolean dig;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.dig = false;
            this.vl = 0;
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
            if (packetPlayInBlockDig.c() != PacketPlayInBlockDig.EnumPlayerDigType.DROP_ITEM && packetPlayInBlockDig.c() != PacketPlayInBlockDig.EnumPlayerDigType.DROP_ALL_ITEMS) {
                this.dig = true;
            }
        }
        else if (packet instanceof PacketPlayInUseEntity && this.dig && player.getGameMode() != GameMode.CREATIVE && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            if (++this.vl > 2) {
                this.vl = 0;
                AlertsManager.getInstance().handleViolation(playerData, this, "attack & dig", false);
            }
        }
        else {
            this.vl = 0;
        }
    }
    
    public AutoClickerH() {
        super(CheckType.AUTO_CLICKERH, "H", "Auto Clicker", CheckVersion.RELEASE);
        this.dig = false;
        this.violations = -1.0;
    }
}
