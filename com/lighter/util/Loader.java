package com.lighter.util;

import com.lighter.check.impl.*;
import java.util.*;
import com.lighter.check.combat.aimassist.*;
import com.lighter.check.combat.autoclicker.*;
import com.lighter.check.other.badpackets.*;
import com.lighter.check.movement.fly.*;
import com.lighter.check.movement.jesus.*;
import com.lighter.check.combat.inventory.*;
import com.lighter.check.combat.killaura.*;
import com.lighter.check.combat.hitboxes.*;
import com.lighter.check.movement.misc.*;
import com.lighter.check.movement.scaffold.*;
import com.lighter.check.movement.phase.*;
import com.lighter.check.other.payload.*;
import com.lighter.check.combat.reach.*;
import com.lighter.check.movement.speed.*;
import com.lighter.check.other.timer.*;
import com.lighter.check.movement.velocity.*;
import com.lighter.check.movement.autosneak.*;

public class Loader
{
    private List<Class<? extends Check>> checkClasses;
    
    public List<Check> loadChecks() {
        final ArrayList<Object> list = (ArrayList<Object>)new ArrayList<Check>();
        for (final Class<? extends Check> clazz : this.getCheckClasses()) {
            try {
                list.add(clazz.newInstance());
            }
            catch (Throwable t) {}
        }
        return (List<Check>)list;
    }
    
    public Loader() {
        this.checkClasses = null;
    }
    
    public void setCheckClasses(final List<Class<? extends Check>> checkClasses) {
        this.checkClasses = checkClasses;
    }
    
    private List<Class<? extends Check>> getCheckClasses() {
        if (this.checkClasses == null) {
            (this.checkClasses = new ArrayList<Class<? extends Check>>()).add(AimA.class);
            this.checkClasses.add(AimB.class);
            this.checkClasses.add(AimC.class);
            this.checkClasses.add(AimD.class);
            this.checkClasses.add(AimE.class);
            this.checkClasses.add(AimF.class);
            this.checkClasses.add(AimG.class);
            this.checkClasses.add(AimH.class);
            this.checkClasses.add(AimI.class);
            this.checkClasses.add(AimJ.class);
            this.checkClasses.add(AimK.class);
            this.checkClasses.add(AimL.class);
            this.checkClasses.add(AimL.class);
            this.checkClasses.add(AimM.class);
            this.checkClasses.add(AimN.class);
            this.checkClasses.add(AimO.class);
            this.checkClasses.add(AimP.class);
            this.checkClasses.add(AimQ.class);
            this.checkClasses.add(AimR.class);
            this.checkClasses.add(AimS.class);
            this.checkClasses.add(AimT.class);
            this.checkClasses.add(AimU.class);
            this.checkClasses.add(AimV.class);
            this.checkClasses.add(AimW.class);
            this.checkClasses.add(AimX.class);
            this.checkClasses.add(AutoClickerA.class);
            this.checkClasses.add(AutoClickerB.class);
            this.checkClasses.add(AutoClickerC.class);
            this.checkClasses.add(AutoClickerD.class);
            this.checkClasses.add(AutoClickerE.class);
            this.checkClasses.add(AutoClickerF.class);
            this.checkClasses.add(AutoClickerG.class);
            this.checkClasses.add(AutoClickerH.class);
            this.checkClasses.add(AutoClickerI.class);
            this.checkClasses.add(AutoClickerJ.class);
            this.checkClasses.add(AutoClickerK.class);
            this.checkClasses.add(AutoClickerL.class);
            this.checkClasses.add(AutoClickerM.class);
            this.checkClasses.add(AutoClickerN.class);
            this.checkClasses.add(AutoClickerO.class);
            this.checkClasses.add(AutoClickerP.class);
            this.checkClasses.add(AutoClickerQ.class);
            this.checkClasses.add(AutoClickerR.class);
            this.checkClasses.add(AutoClickerS.class);
            this.checkClasses.add(AutoClickerT.class);
            this.checkClasses.add(AutoClickerU.class);
            this.checkClasses.add(AutoClickerV.class);
            this.checkClasses.add(BadPacketsA.class);
            this.checkClasses.add(BadPacketsB.class);
            this.checkClasses.add(BadPacketsC.class);
            this.checkClasses.add(BadPacketsD.class);
            this.checkClasses.add(BadPacketsE.class);
            this.checkClasses.add(BadPacketsF.class);
            this.checkClasses.add(BadPacketsG.class);
            this.checkClasses.add(BadPacketsH.class);
            this.checkClasses.add(BadPacketsL.class);
            this.checkClasses.add(BadPacketsI.class);
            this.checkClasses.add(BadPacketsK.class);
            this.checkClasses.add(BadPacketsL.class);
            this.checkClasses.add(BadPacketsM.class);
            this.checkClasses.add(FlyA.class);
            this.checkClasses.add(FlyB.class);
            this.checkClasses.add(FlyC.class);
            this.checkClasses.add(FlyD.class);
            this.checkClasses.add(FlyE.class);
            this.checkClasses.add(FlyF.class);
            this.checkClasses.add(FlyG.class);
            this.checkClasses.add(FlyH.class);
            this.checkClasses.add(FlyI.class);
            this.checkClasses.add(FlyJ.class);
            this.checkClasses.add(FlyK.class);
            this.checkClasses.add(FlyL.class);
            this.checkClasses.add(FlyM.class);
            this.checkClasses.add(FlyN.class);
            this.checkClasses.add(FlyO.class);
            this.checkClasses.add(FlyP.class);
            this.checkClasses.add(JesusA.class);
            this.checkClasses.add(InventoryA.class);
            this.checkClasses.add(InventoryB.class);
            this.checkClasses.add(InventoryC.class);
            this.checkClasses.add(InventoryD.class);
            this.checkClasses.add(InventoryE.class);
            this.checkClasses.add(InventoryF.class);
            this.checkClasses.add(InventoryG.class);
            this.checkClasses.add(InventoryH.class);
            this.checkClasses.add(InventoryI.class);
            this.checkClasses.add(KillAuraA.class);
            this.checkClasses.add(KillAuraB.class);
            this.checkClasses.add(KillAuraC.class);
            this.checkClasses.add(KillAuraD.class);
            this.checkClasses.add(KillAuraE.class);
            this.checkClasses.add(KillAuraF.class);
            this.checkClasses.add(KillAuraG.class);
            this.checkClasses.add(KillAuraH.class);
            this.checkClasses.add(KillAuraI.class);
            this.checkClasses.add(KillAuraJ.class);
            this.checkClasses.add(KillAuraK.class);
            this.checkClasses.add(KillAuraL.class);
            this.checkClasses.add(KillAuraM.class);
            this.checkClasses.add(KillAuraN.class);
            this.checkClasses.add(KillAuraO.class);
            this.checkClasses.add(KillAuraP.class);
            this.checkClasses.add(KillAuraQ.class);
            this.checkClasses.add(KillAuraR.class);
            this.checkClasses.add(HitBoxesA.class);
            this.checkClasses.add(HitBoxesB.class);
            this.checkClasses.add(MiscA.class);
            this.checkClasses.add(MiscB.class);
            this.checkClasses.add(ScaffoldA.class);
            this.checkClasses.add(ScaffoldB.class);
            this.checkClasses.add(ScaffoldC.class);
            this.checkClasses.add(ScaffoldD.class);
            this.checkClasses.add(PhaseA.class);
            this.checkClasses.add(CustomPayloadA.class);
            this.checkClasses.add(ReachA.class);
            this.checkClasses.add(ReachB.class);
            this.checkClasses.add(ReachC.class);
            this.checkClasses.add(ReachD.class);
            this.checkClasses.add(SpeedA.class);
            this.checkClasses.add(SpeedB.class);
            this.checkClasses.add(SpeedC.class);
            this.checkClasses.add(SpeedD.class);
            this.checkClasses.add(SpeedE.class);
            this.checkClasses.add(SpeedF.class);
            this.checkClasses.add(SpeedG.class);
            this.checkClasses.add(SpeedI.class);
            this.checkClasses.add(TimerA.class);
            this.checkClasses.add(TimerB.class);
            this.checkClasses.add(TimerC.class);
            this.checkClasses.add(TimerD.class);
            this.checkClasses.add(TimerE.class);
            this.checkClasses.add(VelocityA.class);
            this.checkClasses.add(VelocityB.class);
            this.checkClasses.add(VelocityC.class);
            this.checkClasses.add(VelocityD.class);
            this.checkClasses.add(AutoSneakA.class);
            this.checkClasses.add(AutoSneakB.class);
            this.checkClasses.add(AutoSneakC.class);
        }
        return this.checkClasses;
    }
}
