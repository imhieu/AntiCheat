package com.lighter.check.combat.killaura;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;

public class KillAuraF extends PacketCheck
{
    private boolean attack;
    private boolean interactAt;
    private boolean interact;
    
    public KillAuraF() {
        super(CheckType.KILL_AURAF, "F", "Kill Aura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying) {
            this.interact = false;
            this.interactAt = false;
            this.attack = false;
        }
        else if (packet instanceof PacketPlayInUseEntity) {
            if (!this.attack && (this.interact || this.interactAt)) {
                AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append(this.interactAt ? "interact at " : "").append(this.interact ? "interact" : "")), false);
                this.interactAt = false;
                this.interact = false;
            }
            this.attack = true;
        }
    }
}
