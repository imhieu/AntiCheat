package com.lighter.check.impl;

import org.bukkit.scheduler.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import com.lighter.data.manager.*;

public class Check
{
    private int lastViolation;
    private final CheckType type;
    private final String friendlyName;
    private final int maxViolation;
    protected double minViolation;
    protected double violations;
    private final String subType;
    private final CheckVersion checkVersion;
    
    public void setLastViolation(final int lastViolation) {
        this.lastViolation = lastViolation;
    }
    
    public void decreaseVL(final double n) {
        this.violations -= Math.min(this.violations - this.minViolation, n);
    }
    
    public void setViolations(final double violations) {
        this.violations = violations;
    }
    
    public CheckVersion getCheckVersion() {
        return this.checkVersion;
    }
    
    public String getSubType() {
        return this.subType;
    }
    
    public CheckType getType() {
        return this.type;
    }
    
    public void run(final Runnable runnable) {
        new BukkitRunnable(this, runnable) {
            final Runnable val$runnable;
            final Check this$0;
            
            public void run() {
                this.val$runnable.run();
            }
        }.runTask((Plugin)Main.getPlugin());
    }
    
    public String getFriendlyName() {
        return this.friendlyName;
    }
    
    public int getMaxViolation() {
        return this.maxViolation;
    }
    
    public double getViolations() {
        return this.violations;
    }
    
    public Check(final CheckType type, final String subType, final String friendlyName, final CheckVersion checkVersion) {
        this.violations = 0.0;
        this.lastViolation = 0;
        this.type = type;
        this.subType = subType;
        this.friendlyName = friendlyName;
        this.checkVersion = checkVersion;
        this.maxViolation = VlManager.getInstance().vl_to_ban(type);
    }
    
    public int getLastViolation() {
        return this.lastViolation;
    }
    
    public enum CheckType
    {
        AIM_ASSISTC("AimAssist C", "Combat"), 
        AIM_ASSISTO("AimAssist O", "Combat"), 
        FLYB("Fly B", "Mouvement"), 
        AUTO_CLICKERQ("AutoClicker Q", "Combat"), 
        AUTO_CLICKERU("AutoClicker U", "Combat"), 
        KILL_AURAB("KillAura B", "Combat"), 
        AIM_ASSISTD("AimAssist D", "Combat"), 
        FLYA("Fly A", "Mouvement"), 
        FLYP("Fly P", "Mouvement"), 
        AUTO_CLICKERR("AutoClicker R", "Combat"), 
        TIMERE("Timer E", "Other"), 
        SPEEDA("Speed A", "Mouvement"), 
        TIMERB("Timer B", "Other"), 
        AIM_ASSISTW("AimAssist W", "Combat"), 
        AUTO_CLICKERV("AutoClicker V", "Combat"), 
        AIM_ASSISTS("AimAssist S", "Combat"), 
        AIM_ASSISTM("AimAssist M", "Combat"), 
        AIM_ASSISTR("AimAssist R", "Combat"), 
        AIM_ASSISTE("AimAssist E", "Combat"), 
        SPEEDF("Speed F", "Mouvement"), 
        TIMERD("Timer D", "Other"), 
        BADPACKETSH("PingSpoof B", "Other"), 
        KILL_AURAR("KillAura R", "Combat"), 
        KILL_AURAM("KillAura M", "Combat"), 
        FLYJ("Fly J", "Mouvement"), 
        BADPACKETSI("FastBreak A", "Other");
        
        private static final CheckType[] $VALUES;
        
        REACHD("Bad Range B", "Combat"), 
        AIM_ASSISTQ("AimAssist Q", "Combat"), 
        AUTO_CLICKERB("AutoClicker B", "Combat"), 
        PHASEA("Phase A", "Mouvement"), 
        REACHC("Bad Range A", "Combat"), 
        KILL_AURAK("KillAura K", "Combat"), 
        AIM_ASSISTJ("AimAssist J", "Combat"), 
        CLIENTB("Client B", "Other"), 
        FLYD("Fly D", "Mouvement"), 
        HITBOXESB("HitBoxes B", "Combat"), 
        VELOCITYC("Velocity C", "Mouvement"), 
        AIM_ASSISTF("AimAssist F", "Combat"), 
        VELOCITYA("Velocity A", "Mouvement"), 
        INVENTORYE("InvalidInventory E", "Mouvement"), 
        KILL_AURAC("KillAura C", "Combat"), 
        INVENTORYA("InvalidInventory A", "Mouvement"), 
        INVENTORYB("InvalidInventory B", "Mouvement"), 
        BADPACKETSC("ServerCrasher A", "Other"), 
        MISCD("Scaffold B", "Mouvement"), 
        AUTO_CLICKERL("AutoClicker L", "Combat"), 
        FLYM("Fly M", "Mouvement"), 
        MISCA("Criticals A", "Combat"), 
        SPEEDB("Speed B", "Mouvement"), 
        AUTOSNEAKB("AutoSneak B", "Mouvement"), 
        KILL_AURAQ("KillAura Q", "Combat"), 
        AUTO_CLICKERI("AutoClicker I", "Combat"), 
        AUTO_CLICKERN("AutoClicker N", "Combat"), 
        AUTO_CLICKERF("AutoClicker F", "Combat"), 
        AIM_ASSISTL("AimAssist L", "Combat"), 
        KILL_AURAA("KillAura A", "Combat"), 
        AUTO_CLICKERP("AutoClicker P", "Combat"), 
        AUTO_CLICKERS("AutoClicker S", "Combat"), 
        HITBOXESA("HitBoxes A", "Combat"), 
        AUTOSNEAKA("AutoSneak A", "Mouvement"), 
        KILL_AURAO("KillAura O", "Combat"), 
        FLYL("Fly L", "Mouvement"), 
        REACHB("Reach B", "Combat"), 
        AIM_ASSISTA("AimAssist A", "Combat"), 
        BADPACKETSB("InvalidDirection B", "Mouvement"), 
        INVENTORYD("InvalidInventory D", "Mouvement"), 
        KILL_AURAH("KillAura H", "Combat"), 
        TIMERC("Timer C", "Other"), 
        BADPACKETSG("ServerCrasher B", "Other"), 
        AIM_ASSISTU("AimAssist U", "Combat"), 
        FLYO("Fly O", "Mouvement"), 
        AUTO_CLICKERG("AutoClicker G", "Combat"), 
        AIM_ASSISTI("AimAssist I", "Combat"), 
        KILL_AURAG("KillAura G", "Combat"), 
        KILL_AURAN("KillAura N", "Combat"), 
        INVENTORYH("InvalidInventory H", "Mouvement"), 
        BADPACKETSM("ServerCrasher C", "Other"), 
        AUTO_CLICKERJ("AutoClicker J", "Combat"), 
        AIM_ASSISTH("AimAssist H", "Combat"), 
        AUTO_CLICKERK("AutoClicker K", "Combat"), 
        KILL_AURAE("KillAura E", "Combat"), 
        AUTO_CLICKERA("AutoClicker A", "Combat"), 
        REACHA("Reach A", "Combat"), 
        BADPACKETSF("InvalidInteract B", "Combat"), 
        FLYN("Fly N", "Mouvement"), 
        SPEEDD("Speed D", "Mouvement"), 
        KILL_AURAF("KillAura F", "Combat"), 
        FLYF("Fly F", "Mouvement"), 
        AUTO_CLICKERT("AutoClicker T", "Combat"), 
        SPEEDC("Speed C", "Mouvement"), 
        KILL_AURAP("KillAura P", "Combat"), 
        SPEEDI("Speed Dev", "Mouvement"), 
        CLIENTA("Client A", "Other"), 
        INVENTORYG("InvalidInventory G", "Mouvement"), 
        VELOCITYD("Velocity D", "Mouvement"), 
        AIM_ASSISTP("AimAssist P", "Combat"), 
        AIM_ASSISTX("AimAssist X", "Combat"), 
        FLYG("Fly G", "Mouvement"), 
        AUTOSNEAKC("AutoSneak C", "Mouvement");
        
        private final String name;
        
        FLYH("Fly H", "Mouvement"), 
        MISCC("Scaffold A", "Mouvement"), 
        AUTO_CLICKERO("AutoClicker O", "Combat"), 
        FLYE("Fly E", "Mouvement"), 
        MISCE("Scaffold C", "Mouvement"), 
        BADPACKETSL("InvalidDirection A", "Mouvement"), 
        JESUSA("Jesus A", "Mouvement"), 
        VELOCITYB("Velocity B", "Mouvement"), 
        KILL_AURAL("KillAura L", "Combat"), 
        INVENTORYF("InvalidInventory F", "Mouvement"), 
        SPEEDG("Speed G", "Mouvement"), 
        AUTO_CLICKERH("AutoClicker H", "Combat");
        
        private final String suffix;
        
        AIM_ASSISTV("AimAssist V", "Combat"), 
        AUTO_CLICKERC("AutoClicker C", "Combat"), 
        FLYI("Fly I", "Mouvement"), 
        BADPACKETSD("InvalidInteract A", "Combat"), 
        AIM_ASSISTT("AimAssist T", "Combat"), 
        BADPACKETSK("W-Tap A", "Combat"), 
        BADPACKETSA("Regen A", "Other"), 
        FLYK("Fly K", "Mouvement"), 
        AUTO_CLICKERM("AutoClicker M", "Combat"), 
        AIM_ASSISTK("AimAssist K", "Combat"), 
        INVENTORYI("InvalidInventory I", "Mouvement"), 
        SPEEDE("Speed E", "Mouvement"), 
        KILL_AURAD("KillAura D", "Combat"), 
        FLYC("Fly C", "Mouvement"), 
        KILL_AURAI("KillAura I", "Combat"), 
        MISCB("Tower A", "Mouvement"), 
        AIM_ASSISTN("AimAssist N", "Combat"), 
        MISCF("Scaffold D", "Mouvement"), 
        AUTO_CLICKERD("AutoClicker D", "Combat"), 
        AUTO_CLICKERE("AutoClicker E", "Combat"), 
        INVENTORYC("InvalidInventory C", "Mouvement"), 
        AIM_ASSISTB("AimAssist B", "Combat"), 
        BADPACKETSE("NoSlow A", "Combat"), 
        KILL_AURAJ("KillAura J", "Combat"), 
        AIM_ASSISTG("AimAssist G", "Combat"), 
        TIMERA("Timer A", "Other");
        
        public String getSuffix() {
            return this.suffix;
        }
        
        static {
            $VALUES = new CheckType[] { CheckType.AIM_ASSISTA, CheckType.AIM_ASSISTB, CheckType.AIM_ASSISTC, CheckType.AIM_ASSISTD, CheckType.AIM_ASSISTE, CheckType.AIM_ASSISTF, CheckType.AIM_ASSISTG, CheckType.AIM_ASSISTH, CheckType.AIM_ASSISTI, CheckType.AIM_ASSISTJ, CheckType.AIM_ASSISTK, CheckType.AIM_ASSISTL, CheckType.AIM_ASSISTM, CheckType.AIM_ASSISTN, CheckType.AIM_ASSISTO, CheckType.AIM_ASSISTP, CheckType.AIM_ASSISTQ, CheckType.AIM_ASSISTR, CheckType.AIM_ASSISTS, CheckType.AIM_ASSISTT, CheckType.AIM_ASSISTU, CheckType.AIM_ASSISTV, CheckType.AIM_ASSISTW, CheckType.AIM_ASSISTX, CheckType.AUTO_CLICKERA, CheckType.AUTO_CLICKERB, CheckType.AUTO_CLICKERC, CheckType.AUTO_CLICKERD, CheckType.AUTO_CLICKERE, CheckType.AUTO_CLICKERF, CheckType.AUTO_CLICKERG, CheckType.AUTO_CLICKERH, CheckType.AUTO_CLICKERI, CheckType.AUTO_CLICKERJ, CheckType.AUTO_CLICKERK, CheckType.AUTO_CLICKERL, CheckType.AUTO_CLICKERM, CheckType.AUTO_CLICKERN, CheckType.AUTO_CLICKERO, CheckType.AUTO_CLICKERP, CheckType.AUTO_CLICKERQ, CheckType.AUTO_CLICKERR, CheckType.AUTO_CLICKERS, CheckType.AUTO_CLICKERT, CheckType.AUTO_CLICKERU, CheckType.AUTO_CLICKERV, CheckType.KILL_AURAA, CheckType.KILL_AURAB, CheckType.KILL_AURAC, CheckType.KILL_AURAD, CheckType.KILL_AURAE, CheckType.KILL_AURAF, CheckType.KILL_AURAG, CheckType.KILL_AURAH, CheckType.KILL_AURAI, CheckType.KILL_AURAJ, CheckType.KILL_AURAK, CheckType.KILL_AURAL, CheckType.KILL_AURAM, CheckType.KILL_AURAN, CheckType.KILL_AURAO, CheckType.KILL_AURAP, CheckType.KILL_AURAQ, CheckType.KILL_AURAR, CheckType.HITBOXESA, CheckType.HITBOXESB, CheckType.BADPACKETSD, CheckType.BADPACKETSF, CheckType.BADPACKETSE, CheckType.REACHA, CheckType.REACHB, CheckType.REACHC, CheckType.REACHD, CheckType.MISCA, CheckType.BADPACKETSK, CheckType.FLYA, CheckType.FLYB, CheckType.FLYC, CheckType.FLYD, CheckType.FLYE, CheckType.FLYF, CheckType.FLYG, CheckType.FLYH, CheckType.FLYI, CheckType.FLYJ, CheckType.FLYK, CheckType.FLYL, CheckType.FLYM, CheckType.FLYN, CheckType.FLYO, CheckType.FLYP, CheckType.JESUSA, CheckType.INVENTORYA, CheckType.INVENTORYB, CheckType.INVENTORYC, CheckType.INVENTORYD, CheckType.INVENTORYE, CheckType.INVENTORYF, CheckType.INVENTORYG, CheckType.INVENTORYH, CheckType.INVENTORYI, CheckType.CLIENTA, CheckType.CLIENTB, CheckType.PHASEA, CheckType.SPEEDA, CheckType.SPEEDB, CheckType.SPEEDC, CheckType.SPEEDD, CheckType.SPEEDE, CheckType.SPEEDF, CheckType.SPEEDG, CheckType.SPEEDI, CheckType.TIMERA, CheckType.TIMERB, CheckType.TIMERC, CheckType.TIMERD, CheckType.TIMERE, CheckType.VELOCITYA, CheckType.VELOCITYB, CheckType.VELOCITYC, CheckType.VELOCITYD, CheckType.MISCB, CheckType.MISCC, CheckType.MISCD, CheckType.MISCE, CheckType.MISCF, CheckType.BADPACKETSL, CheckType.BADPACKETSB, CheckType.AUTOSNEAKA, CheckType.AUTOSNEAKB, CheckType.AUTOSNEAKC, CheckType.BADPACKETSH, CheckType.BADPACKETSA, CheckType.BADPACKETSC, CheckType.BADPACKETSG, CheckType.BADPACKETSM, CheckType.BADPACKETSI };
        }
        
        private CheckType(final String name, final String suffix) {
            this.name = name;
            this.suffix = suffix;
        }
        
        public String getName() {
            return this.name;
        }
    }
    
    public enum CheckVersion
    {
        EXPERIMENTAL, 
        RELEASE;
        
        private static final CheckVersion[] $VALUES;
        
        static {
            $VALUES = new CheckVersion[] { CheckVersion.RELEASE, CheckVersion.EXPERIMENTAL };
        }
    }
}
