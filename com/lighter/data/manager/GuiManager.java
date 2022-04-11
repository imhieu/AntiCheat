package com.lighter.data.manager;

import com.lighter.gui.check.other.*;
import com.lighter.gui.check.combat.*;
import com.lighter.gui.impl.*;
import com.lighter.gui.check.mouvement.*;
import com.lighter.gui.*;

public class GuiManager
{
    private Criticals critsGui;
    private Tower towerGui;
    private AutoClicker autoGui;
    private InvalidInventory invinvGui;
    private Management mainGui;
    private AimAssist aimGui;
    private Regen regenGui;
    private PingSpoof pingspoofGui;
    private Phase phaseGui;
    private HitBoxes hitBoxesGui;
    private InvalidDirection directionGui;
    private Mouvement mouvementGui;
    private InvalidInteract invintGui;
    private Speed speedGui;
    private NoSlow noslowGui;
    private WTap wTapGui;
    private static GuiManager instance;
    private Anticheat acGui;
    private Timer timerGui;
    private InvalidPos posGui;
    private Velocity velocityGui;
    private FastBreak breakGui;
    private Client clientGui;
    private Reach reachGui;
    private Jesus jesusGui;
    private Settings settingsGui;
    private ServerCrasher crasherGui;
    private Fly flyGui;
    private AutoSneak autoSneakGui;
    private Other otherGui;
    private KillAura kaGui;
    private Combat checkGui;
    private Scaffold scaffoldGui;
    
    public AutoSneak getAutoSneakGui() {
        return this.autoSneakGui;
    }
    
    public Combat getCheckGui() {
        return this.checkGui;
    }
    
    public WTap getwTapGui() {
        return this.wTapGui;
    }
    
    public Mouvement getMovGui() {
        return this.mouvementGui;
    }
    
    public InvalidInventory getInvInventoryGui() {
        return this.invinvGui;
    }
    
    public HitBoxes getHitBoxesGui() {
        return this.hitBoxesGui;
    }
    
    public KillAura getKaGui() {
        return this.kaGui;
    }
    
    public NoSlow getNoSlowGui() {
        return this.noslowGui;
    }
    
    public Speed getSpeedGui() {
        return this.speedGui;
    }
    
    public InvalidPos getPosGui() {
        return this.posGui;
    }
    
    public InvalidInteract getInvInteractGui() {
        return this.invintGui;
    }
    
    public AimAssist getAimGui() {
        return this.aimGui;
    }
    
    public Client getClientGui() {
        return this.clientGui;
    }
    
    public Settings getSettingGui() {
        return this.settingsGui;
    }
    
    public Regen getRegenGui() {
        return this.regenGui;
    }
    
    public Velocity getVelocityGui() {
        return this.velocityGui;
    }
    
    public Anticheat getacGui() {
        return this.acGui;
    }
    
    public PingSpoof getPingSpoofGui() {
        return this.pingspoofGui;
    }
    
    public Reach getReachGui() {
        return this.reachGui;
    }
    
    public Timer getTimerGui() {
        return this.timerGui;
    }
    
    public Criticals getCritsGui() {
        return this.critsGui;
    }
    
    public AutoClicker getAutoGui() {
        return this.autoGui;
    }
    
    public InvalidDirection getDirectionGui() {
        return this.directionGui;
    }
    
    public void enable() {
        this.mainGui = new Management();
        this.checkGui = new Combat();
        this.mouvementGui = new Mouvement();
        this.otherGui = new Other();
        this.acGui = new Anticheat();
        this.settingsGui = new Settings();
        this.aimGui = new AimAssist();
        this.autoGui = new AutoClicker();
        this.invintGui = new InvalidInteract();
        this.invinvGui = new InvalidInventory();
        this.noslowGui = new NoSlow();
        this.reachGui = new Reach();
        this.kaGui = new KillAura();
        this.critsGui = new Criticals();
        this.wTapGui = new WTap();
        this.hitBoxesGui = new HitBoxes();
        this.flyGui = new Fly();
        this.speedGui = new Speed();
        this.phaseGui = new Phase();
        this.velocityGui = new Velocity();
        this.scaffoldGui = new Scaffold();
        this.directionGui = new InvalidDirection();
        this.towerGui = new Tower();
        this.autoSneakGui = new AutoSneak();
        this.jesusGui = new Jesus();
        this.clientGui = new Client();
        this.breakGui = new FastBreak();
        this.posGui = new InvalidPos();
        this.pingspoofGui = new PingSpoof();
        this.regenGui = new Regen();
        this.crasherGui = new ServerCrasher();
        this.timerGui = new Timer();
    }
    
    public Tower getTowerGui() {
        return this.towerGui;
    }
    
    public Jesus getJesusGui() {
        return this.jesusGui;
    }
    
    public Phase getPhaseGui() {
        return this.phaseGui;
    }
    
    public Management getMainGui() {
        return this.mainGui;
    }
    
    public Other getOtherGui() {
        return this.otherGui;
    }
    
    public ServerCrasher getCraserGui() {
        return this.crasherGui;
    }
    
    public Fly getFlyGui() {
        return this.flyGui;
    }
    
    public FastBreak getBreakGui() {
        return this.breakGui;
    }
    
    public Scaffold getScaffoldGui() {
        return this.scaffoldGui;
    }
    
    public static GuiManager getInstance() {
        return (GuiManager.instance == null) ? (GuiManager.instance = new GuiManager()) : GuiManager.instance;
    }
    
    public Gui getGui(final Gui gui) {
        return gui;
    }
}
