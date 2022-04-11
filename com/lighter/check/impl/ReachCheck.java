package com.lighter.check.impl;

import org.bukkit.entity.*;
import com.lighter.data.*;

public abstract class ReachCheck extends Check
{
    public abstract void handle(final Player p0, final PlayerData p1, final ReachData p2, final long p3);
    
    public ReachCheck(final CheckType checkType, final String s, final String s2, final CheckVersion checkVersion) {
        super(checkType, s, s2, checkVersion);
    }
}
