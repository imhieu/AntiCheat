package com.lighter.check.impl;

import org.bukkit.entity.*;
import com.lighter.data.*;

public abstract class MovementCheck extends Check
{
    public MovementCheck(final CheckType checkType, final String s, final String s2, final CheckVersion checkVersion) {
        super(checkType, s, s2, checkVersion);
    }
    
    public abstract void handle(final Player p0, final PlayerData p1, final PlayerLocation p2, final PlayerLocation p3, final long p4);
}
