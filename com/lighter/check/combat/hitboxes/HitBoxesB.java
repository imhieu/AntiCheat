package com.lighter.check.combat.hitboxes;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;

public class HitBoxesB extends PacketCheck
{
    public HitBoxesB() {
        super(CheckType.HITBOXESB, "B", "HitBoxes", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInUseEntity && player.getGameMode().equals((Object)GameMode.SURVIVAL)) {
            final PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity)packet;
            if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
                final Entity a = ((PacketPlayInUseEntity)packet).a(playerData.getEntityPlayer().getWorld());
                final Vec3D b = packetPlayInUseEntity.b();
                if (a instanceof EntityPlayer && (Math.abs(b.a) > 0.4000000059604645 || Math.abs(b.b) > 1.91 || Math.abs(b.c) > 0.4000000059604645)) {
                    AlertsManager.getInstance().handleViolation(playerData, this, "interact hitboxes", false);
                }
            }
        }
    }
}
