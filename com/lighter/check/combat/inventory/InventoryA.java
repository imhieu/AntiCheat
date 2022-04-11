package com.lighter.check.combat.inventory;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class InventoryA extends PacketCheck
{
    private int stage;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.stage = 0;
        }
        else if (packet instanceof PacketPlayInBlockPlace) {
            if (this.stage == 1) {
                this.stage = 2;
            }
        }
        else if (packet instanceof PacketPlayInHeldItemSlot) {
            if (this.stage == 2) {
                AlertsManager.getInstance().handleViolation(playerData, this, "HeldItemSlot", false);
            }
            this.stage = 1;
        }
    }
    
    public InventoryA() {
        super(CheckType.INVENTORYA, "A", "InvalidInventory", CheckVersion.RELEASE);
        this.stage = 0;
    }
}
