package com.lighter.check.combat.inventory;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.check.impl.*;

public class InventoryE extends PacketCheck
{
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
    }
    
    public InventoryE() {
        super(CheckType.INVENTORYE, "E", "InvalidInventory", CheckVersion.RELEASE);
    }
}
