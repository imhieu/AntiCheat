package com.lighter.check.combat.inventory;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class InventoryD extends PacketCheck
{
    private int stage;
    
    public InventoryD() {
        super(CheckType.INVENTORYD, "D", "InvalidInventory", CheckVersion.RELEASE);
        this.stage = 0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (this.stage == 0) {
            if (packet instanceof PacketPlayInClientCommand && ((PacketPlayInClientCommand)packet).a() == PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
                ++this.stage;
            }
        }
        else if (this.stage == 1) {
            if (packet instanceof PacketPlayInFlying.PacketPlayInLook) {
                ++this.stage;
            }
            else {
                this.stage = 0;
            }
        }
        else if (this.stage == 2) {
            if (packet instanceof PacketPlayInFlying.PacketPlayInLook) {
                AlertsManager.getInstance().handleViolation(playerData, this, "yaw or pitch change", false);
            }
            this.stage = 0;
        }
    }
}
