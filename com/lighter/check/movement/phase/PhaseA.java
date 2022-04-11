package com.lighter.check.movement.phase;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;

public class PhaseA extends PacketCheck
{
    private int stage;
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        final String simpleName = packet.getClass().getSimpleName();
        switch (simpleName) {
            case "PacketPlayInFlying": {
                if (this.stage == 0) {
                    ++this.stage;
                    break;
                }
                this.stage = 0;
                break;
            }
            case "PacketPlayInPosition": {
                if (this.stage >= 2) {
                    ++this.stage;
                    break;
                }
                break;
            }
            case "PacketPlayInEntityAction": {
                if (((PacketPlayInEntityAction)packet).b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING) {
                    if (this.stage == 1) {
                        ++this.stage;
                        break;
                    }
                    this.stage = 0;
                    break;
                }
                else {
                    if (((PacketPlayInEntityAction)packet).b() == PacketPlayInEntityAction.EnumPlayerAction.START_SNEAKING && this.stage >= 3) {
                        AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("stage:").append(this.stage)), false);
                        break;
                    }
                    break;
                }
                break;
            }
        }
    }
    
    public PhaseA() {
        super(CheckType.PHASEA, "A", "Phase", CheckVersion.RELEASE);
    }
}
