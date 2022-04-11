package com.lighter.check.combat.killaura;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;

public class KillAuraR extends PacketCheck
{
    public KillAuraR() {
        super(CheckType.KILL_AURAR, "R", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
    }
}
