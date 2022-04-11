package com.lighter.check.combat.inventory;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;

public class InventoryC extends PacketCheck
{
    public InventoryC() {
        super(CheckType.INVENTORYC, "C", "InvalidInventory", CheckVersion.RELEASE);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
    }
}
