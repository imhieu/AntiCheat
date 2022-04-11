package com.lighter.check.combat.killaura;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class KillAuraN extends PacketCheck
{
    boolean sent;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
        else if (packet instanceof PacketPlayInUseEntity) {
            this.sent = true;
        }
        else if (packet instanceof PacketPlayInClientCommand && ((PacketPlayInClientCommand)packet).a() == PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT && this.sent) {
            AlertsManager.getInstance().handleViolation(playerData, this, "use & open inv", false);
        }
    }
    
    public KillAuraN() {
        super(CheckType.KILL_AURAN, "N", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
}
