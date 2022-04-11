package com.lighter.check.impl;

import org.bukkit.entity.*;
import com.lighter.data.*;
import org.bukkit.event.*;

public abstract class EventCheck extends Check
{
    public abstract void handle(final Player p0, final PlayerData p1, final Event p2, final long p3);
    
    public EventCheck(final CheckType checkType, final String s, final String s2, final CheckVersion checkVersion) {
        super(checkType, s, s2, checkVersion);
    }
}
