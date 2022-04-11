package com.lighter.check.movement.autosneak;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class AutoSneakC extends PacketCheck
{
    public AutoSneakC() {
        super(CheckType.AUTOSNEAKC, "C", "AutoSneak", CheckVersion.RELEASE);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (playerData.isInventoryOpen() && packet instanceof PacketPlayInWindowClick && ((PacketPlayInWindowClick)packet).a() == 0 && player.isSneaking() && playerData.getSneaking()) {
            AlertsManager.getInstance().handleViolation(playerData, this, "sneak with open inv", true);
        }
    }
}
