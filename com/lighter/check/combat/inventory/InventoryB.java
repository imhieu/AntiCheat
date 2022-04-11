package com.lighter.check.combat.inventory;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class InventoryB extends PacketCheck
{
    private int ticks;
    
    public InventoryB() {
        super(CheckType.INVENTORYB, "B", "InvalidInventory", CheckVersion.RELEASE);
        this.ticks = 0;
        this.violations = -4.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInClientCommand && ((PacketPlayInClientCommand)packet).a().equals((Object)PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT)) {
            this.ticks = 0;
        }
        else if (packet instanceof PacketPlayInFlying && !playerData.hasLag() && !playerData.hasFast()) {
            if (this.ticks++ > 10) {
                playerData.setInventoryOpen(false);
            }
            else if (this.ticks >= 4 && playerData.isInventoryOpen() && player.isSprinting() && playerData.isOnGround()) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("ticks:").append(this.ticks)), false);
            }
        }
    }
}
