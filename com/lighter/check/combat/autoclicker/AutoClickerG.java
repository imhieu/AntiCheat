package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerG extends PacketCheck
{
    private boolean attack;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.attack = false;
        }
        else if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig)packet;
            if ((packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.DROP_ITEM || packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.DROP_ALL_ITEMS) && this.attack && player.getGameMode() != GameMode.CREATIVE) {
                AlertsManager.getInstance().handleViolation(playerData, this, "drop item", false);
            }
        }
        else if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity)packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            this.attack = true;
        }
    }
    
    public AutoClickerG() {
        super(CheckType.AUTO_CLICKERG, "G", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
