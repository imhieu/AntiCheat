package com.lighter.check.impl;

import org.bukkit.entity.*;
import com.lighter.data.*;
import net.minecraft.server.v1_8_R3.*;

public abstract class PacketCheck extends Check
{
    public PacketCheck(final CheckType checkType, final String s, final String s2, final CheckVersion checkVersion) {
        super(checkType, s, s2, checkVersion);
    }
    
    public abstract void handle(final Player p0, final PlayerData p1, final Packet p2, final long p3);
}
