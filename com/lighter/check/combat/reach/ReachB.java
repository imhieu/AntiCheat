package com.lighter.check.combat.reach;

import java.text.*;
import com.lighter.check.impl.*;
import java.util.function.*;
import org.bukkit.*;
import com.lighter.util.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import net.minecraft.server.v1_8_R3.*;
import com.lighter.data.*;

public class ReachB extends PacketCheck
{
    int vl;
    static ThreadLocal<DecimalFormat> REACH_FORMAT;
    boolean sameTick;
    
    public ReachB() {
        super(CheckType.REACHB, "B", "Reach", CheckVersion.RELEASE);
        this.violations = -5.0;
    }
    
    static {
        ReachB.REACH_FORMAT = ThreadLocal.withInitial((Supplier<? extends DecimalFormat>)ReachB::lambda$static$30);
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final Packet packet, final long n) {
        if (packet instanceof PacketPlayInUseEntity && !player.getGameMode().equals((Object)GameMode.CREATIVE) && !playerData.hasLag() && !playerData.hasFast() && playerData.getLastAttacked() != null && !this.sameTick) {
            final PlayerLocation location = playerData.getLocation();
            final PlayerData lastAttacked = playerData.getLastAttacked();
            if (lastAttacked == null) {
                return;
            }
            final PlayerLocation location2 = lastAttacked.getLocation(lastAttacked.getPingTicks() + 1);
            if (location2 == null) {
                return;
            }
            if (System.currentTimeMillis() - location2.getTimestamp() - MathUtil.pingFormula(playerData.getPing()) * 50L >= 500L) {
                return;
            }
            final double hypot = Math.hypot(location.getX() - location2.getX(), location.getZ() - location2.getZ());
            if (hypot > 6.0) {
                return;
            }
            double n2 = 3.29;
            if (lastAttacked.getSprinting() == null || !lastAttacked.getSprinting() || MathUtil.getDistanceBetweenAngles(location.getYaw(), location2.getYaw()) < 90.0) {
                n2 += 0.5;
            }
            if (player.isInsideVehicle() && player.getVehicle() instanceof Boat) {
                n2 += 10.0;
            }
            if (hypot > n2) {
                if (++this.vl > 5) {
                    AlertsManager.getInstance().handleViolation(playerData, this, String.valueOf(new StringBuilder().append("range: ").append(ReachB.REACH_FORMAT.get().format(hypot))), true);
                }
            }
            else {
                this.violations -= Math.min(this.violations + 5.0, 0.1);
                this.vl = 0;
            }
            this.sameTick = true;
        }
        else if (packet instanceof PacketPlayInFlying) {
            this.sameTick = false;
        }
    }
    
    private static DecimalFormat lambda$static$30() {
        return new DecimalFormat("#.##");
    }
}
