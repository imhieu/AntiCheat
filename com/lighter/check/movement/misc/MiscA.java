package com.lighter.check.movement.misc;

import com.lighter.check.impl.*;
import org.bukkit.entity.*;
import com.lighter.data.*;

public class MiscA extends MovementCheck
{
    public MiscA() {
        super(CheckType.MISCA, "A", "Criticals", CheckVersion.RELEASE);
        this.violations = -6.0;
    }
    
    @Override
    public void handle(final Player player, final PlayerData playerData, final PlayerLocation playerLocation, final PlayerLocation playerLocation2, final long n) {
    }
}
