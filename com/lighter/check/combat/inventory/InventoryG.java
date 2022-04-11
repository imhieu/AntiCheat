package com.lighter.check.combat.inventory;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class InventoryG extends PacketCheck
{
    int vl;
    
    public InventoryG() {
        super(CheckType.INVENTORYG, "G", "InvalidInventory", CheckVersion.EXPERIMENTAL);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (playerData.isInventoryOpen() && packet instanceof PacketPlayInArmAnimation) {
            if (++this.vl > 2 && !playerData.hasLag()) {
                this.vl = 0;
                AlertsManager.getInstance().handleViolation(playerData, this, "swing & open inv", false);
            }
        }
        else if (!playerData.isInventoryOpen() && this.vl > 0) {
            this.vl = 0;
        }
    }
}
