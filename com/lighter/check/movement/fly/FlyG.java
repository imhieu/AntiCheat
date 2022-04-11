package com.lighter.check.movement.fly;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.util.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import com.lighter.data.manager.*;

public class FlyG extends PacketCheck
{
    private int vl;
    
    public FlyG() {
        super(CheckType.FLYG, "G", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }
    
    private boolean isOnGround(final Player player) {
        return ReflectionUtil.getCollidingBlocks(player, ReflectionUtil.modifyBoundingBox(ReflectionUtil.getBoundingBox(player), 0.0, -0.1, 0.0, 0.0, 0.0, 0.0)).size() > 0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInFlying && !player.getAllowFlight() && !playerData.hasLag() && player.getGameMode() != GameMode.CREATIVE) {
            this.run(this::lambda$handle$2);
        }
    }
    
    private void lambda$handle$2(final PacketPlayInFlying packetPlayInFlying, final Player player, final PlayerData playerData) {
        if (packetPlayInFlying.f() && !this.isOnGround(player)) {
            if (++this.vl > 5) {
                this.vl = 0;
                AlertsManager.getInstance().handleViolation(playerData, this, "groundspoofing", false);
            }
        }
        else {
            this.vl = 0;
        }
    }
}
