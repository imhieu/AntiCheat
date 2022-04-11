package com.lighter.util;

import org.bukkit.entity.*;
import com.lighter.data.manager.*;

public class API
{
    public static int[] getCps(final Player player) {
        return PlayerManager.getInstance().getPlayer(player).getCps();
    }
    
    public static double[] getReach(final Player player) {
        return PlayerManager.getInstance().getPlayer(player).getReach();
    }
}
