package com.lighter.check.combat.inventory;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class InventoryH extends PacketCheck
{
    private boolean sent;
    
    public InventoryH() {
        super(CheckType.INVENTORYH, "H", "InvalidInventory", CheckVersion.EXPERIMENTAL);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
        else if (packet instanceof PacketPlayInWindowClick) {
            if (this.sent && !playerData.hasLag()) {
                AlertsManager.getInstance().handleViolation(playerData, this, "in:", false);
            }
        }
        else if (packet instanceof PacketPlayInClientCommand && ((PacketPlayInClientCommand)packet).a() == PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
            this.sent = true;
        }
    }
}
