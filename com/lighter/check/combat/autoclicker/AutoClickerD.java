package com.lighter.check.combat.autoclicker;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.check.impl.*;

public class AutoClickerD extends PacketCheck
{
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
    }
    
    public AutoClickerD() {
        super(CheckType.AUTO_CLICKERD, "D", "Auto Clicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
