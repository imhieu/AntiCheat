package com.lighter.check.movement.scaffold;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import java.util.*;
import com.lighter.data.manager.*;

public class ScaffoldA extends PacketCheck
{
    private Integer lastSlot;
    
    public ScaffoldA() {
        super(CheckType.MISCC, "A", "Scaffold", CheckVersion.RELEASE);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInHeldItemSlot) {
            final PacketPlayInHeldItemSlot packetPlayInHeldItemSlot = (PacketPlayInHeldItemSlot)packet;
            if (this.lastSlot != null && Objects.equals(packetPlayInHeldItemSlot.a(), this.lastSlot) && playerData.getTotalTicks() > 200) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("lastSlot:").append(this.lastSlot)), false);
            }
            else {
                this.decreaseVL(0.25);
            }
            this.lastSlot = packetPlayInHeldItemSlot.a();
        }
    }
}
