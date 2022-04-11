package com.lighter.check.combat.inventory;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import net.minecraft.server.v1_8_R3.*;

public class InventoryF extends PacketCheck
{
    private boolean swing;
    private boolean click;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.swing && this.click && !playerData.hasLag()) {
                AlertsManager.getInstance().handleViolation(playerData, this, "swing & click", false);
            }
            this.swing = false;
            this.click = false;
        }
        else if (packet instanceof PacketPlayInArmAnimation) {
            this.swing = true;
        }
        else if (packet instanceof PacketPlayInWindowClick) {
            this.click = true;
        }
    }
    
    public InventoryF() {
        super(CheckType.INVENTORYF, "F", "InvalidInventory", CheckVersion.EXPERIMENTAL);
        this.swing = false;
        this.click = false;
    }
}
