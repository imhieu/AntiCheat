package com.lighter.main;

import org.bukkit.plugin.java.*;
import com.lighter.listener.*;
import com.lighter.gui.impl.*;
import com.lighter.util.*;
import org.bukkit.command.*;
import com.lighter.command.*;
import org.bukkit.plugin.*;
import java.util.*;
import com.lighter.check.impl.*;
import org.bukkit.event.*;
import com.lighter.data.manager.*;

public class Main extends JavaPlugin implements Listener
{
    public String Version;
    private DataListener dataListener;
    public String Discord;
    public static Main instance;
    private VlSettings vlSettings;
    public HashMap<UUID, String> lastCheck;
    private Loader typeLoader;
    
    private static boolean lllIIlIIIIII(final int n) {
        return n == 0;
    }
    
    public static Main getPlugin() {
        return Main.instance;
    }
    
    public void registerCommand() {
        this.getCommand("anticheat").setExecutor((CommandExecutor)new LighterCommand());
        this.getCommand("alerts").setExecutor((CommandExecutor)new AlertsCommand());
        this.getCommand("devalerts").setExecutor((CommandExecutor)new DevAlertsCommand());
        this.getCommand("logs").setExecutor((CommandExecutor)new LogsCommand());
        this.getCommand("perf").setExecutor((CommandExecutor)new PerfCommand());
        this.getCommand("acping").setExecutor((CommandExecutor)new PlayerCommand());
        this.getCommand("veltest").setExecutor((CommandExecutor)new VelocityCommand());
    }
    
    public void registerListeners() {
        this.dataListener = new DataListener();
        this.vlSettings = new VlSettings();
        this.getServer().getPluginManager().registerEvents((Listener)this.dataListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.vlSettings, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
    }
    
    public Loader getTypeLoader() {
        return this.typeLoader;
    }
    
    public void unregisterAnticheat() {
        PlayerManager.disable();
        OptionsManager.getInstance().disable();
        CheckManager.getInstance().disable();
        VlManager.getInstance().disable();
        PunishmentManager.getInstance().disable();
        this.typeLoader.setCheckClasses(null);
    }
    
    public void onDisable() {
        this.unregisterListeners();
        this.unregisterAnticheat();
    }
    
    public void unregisterListeners() {
        HandlerList.unregisterAll((Listener)this.dataListener);
        HandlerList.unregisterAll((Listener)this.vlSettings);
        HandlerList.unregisterAll((Plugin)getPlugin());
    }
    
    public void registerAnticheat() {
        this.typeLoader = new Loader();
        PlayerManager.enable(Main.instance = this);
        OptionsManager.getInstance().enable();
        CheckManager.getInstance().enable();
        VlManager.getInstance().enable();
        GuiManager.getInstance().enable();
        PunishmentManager.getInstance().enable();
    }
    
    public Main() {
        this.Version = "1.3";
        this.Discord = "discord.gg/TVWAQUe";
        this.lastCheck = new HashMap<UUID, String>();
    }
    
    public void onEnable() {
    }
    // invokedynamic(BrownieProtect_V2_\u30b4\u2262\u22a6\u1c2f\u1daf\u1d26\u2d7a\u2c12\u17c4\u1ec1\u2339\u309d\u2c51\u1aef\u2b2b\u1742\u2b70\u18a1\u1978\u1b04\u1bdc\u2b40\u22d9\u2b58\u2551\u1f7b\u2b40\u1aa4\u1701\u162c\u1b2c\u2eaa\u1cd5\u27a6\u1e7b\u258e\u27e5\u25dc\u2c9e\u1dd8\u1f0d\u179e\u2f00\u18aa\u16dc\u1dc3\u2c06\u2b60\u1fad\u2866\u1a1b\u1984\u2658\u2fa3\u3066\u23d4\u2489\u2085\u1dff\u2baf\u25f0\u191e\u2e35\u18a9\u1ef8\u2d86\u2f8a\u1de7\u24f8\u2f13\u2988\u26da\u14c3\u18ad\u159a\u2300:(Ljava/lang/Object;)V, this)
    // invokedynamic(BrownieProtect_V2_\u13e0\u15ef\u3014\u2054\u2a20\u2756\u172b\u1801\u14d7\u23bc\u1a99\u2595\u28bf\u1b91\u180f\u19f5\u29e8\u30bd\u1f1e\u1574\u2e41\u2002\u2a22\u2ebe\u201a\u17d2\u2360\u1e7c\u1921\u1710\u1a8a\u2a20\u2798\u173c\u1430\u2a2d\u13e0\u2264\u218b\u160d\u236c\u2d08\u28fd\u2551\u2b7d\u1ea8\u1c24\u281e\u2e67\u20c7\u207d\u2d55\u2107\u25a9\u2115\u1deb\u2b5b\u14ba\u30a1\u1fb4\u14ff\u1398\u2d9a\u2bef\u2181\u2989\u2823\u3004\u2afe\u28b3:(Ljava/lang/Object;)V, this)
    // invokedynamic(BrownieProtect_V2_\u1c2f\u1cb3\u1693\u2c00\u2beb\u1ee6\u190a\u24b7\u24e5\u2903\u29d2\u1ecf\u24a1\u235f\u1cf1\u19f3:(Ljava/lang/Object;)V, this)
}
