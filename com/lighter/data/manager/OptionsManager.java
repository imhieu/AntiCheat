package com.lighter.data.manager;

import java.io.*;
import org.bukkit.configuration.file.*;
import java.util.*;
import com.lighter.main.*;

public class OptionsManager
{
    public String errorCannotFindLog;
    public boolean async;
    public boolean bypassEnabled;
    public boolean alertNoSpam;
    public Integer maxCps;
    public Integer chatMin;
    public String alertExperimental;
    public static File fileCheck;
    public String liscence;
    public String errorUsageLog;
    public String errorOnlyPlayer;
    public String banSeparated;
    public String errorOnlyOp;
    public String alertHoverCommand;
    public String alertEnabled;
    public static YamlConfiguration configuration;
    public static String HEADER;
    public boolean banAnnouncement;
    public String alertCertainty;
    public boolean chatEnabled;
    public String logsResult;
    public String logsSeparated;
    public static OptionsManager instance;
    public boolean autoBan;
    public boolean rollback;
    public static YamlConfiguration configurationCheck;
    public String alertMessage;
    public String alertHover;
    public String errorPlayerNotFound;
    public static File file;
    public String banMessage;
    public boolean alertInformation;
    public String bypassPermission;
    public String anticheatName;
    public String logPermission;
    public Integer packetlimiterlog;
    public String logsPlayer;
    public String chatMinMessage;
    public List<String> banCommands;
    public String alertDisabled;
    public String errorPerm;
    public boolean packetEnabled;
    public String modPermission;
    public Integer packetlimiterban;
    public String chatSameMessage;
    
    public boolean isRollback() {
        return this.rollback;
    }
    
    public String getAlertCertainty() {
        return this.alertCertainty;
    }
    
    public boolean isChatEnabled() {
        return this.chatEnabled;
    }
    
    public static OptionsManager getInstance() {
        return (OptionsManager.instance == null) ? (OptionsManager.instance = new OptionsManager()) : OptionsManager.instance;
    }
    
    public void loadConfigCheck() {
        try {
            OptionsManager.configurationCheck.load(OptionsManager.fileCheck);
        }
        catch (Exception ex) {}
    }
    
    public boolean isBypassEnabled() {
        return this.bypassEnabled;
    }
    
    public void disable() {
        saveConfig();
    }
    
    public boolean isPacketEnabled() {
        return this.packetEnabled;
    }
    
    public boolean getAlertNoSpam() {
        return this.alertNoSpam;
    }
    
    public Integer getPacketPerSecondLog() {
        return this.packetlimiterlog;
    }
    
    public boolean isBanAnnouncement() {
        return this.banAnnouncement;
    }
    
    public OptionsManager() {
        OptionsManager.configuration = new YamlConfiguration();
        OptionsManager.configurationCheck = new YamlConfiguration();
    }
    
    public String getChatSameMessage() {
        return this.chatSameMessage;
    }
    
    public void enable() {
        this.setupConfig();
        this.setupConfigCheck();
        this.readConfig();
    }
    
    public Integer getPacketPerSecondBan() {
        return this.packetlimiterban;
    }
    
    public String getPlayerLogs() {
        return this.logsPlayer;
    }
    
    public List<String> getBanCommands() {
        return this.banCommands;
    }
    
    public String getAlertDisabled() {
        return this.alertDisabled;
    }
    
    public Integer getChatMin() {
        return this.chatMin;
    }
    
    public void readConfig() {
        this.liscence = OptionsManager.configuration.getString("liscence", "Your liscence here");
        this.anticheatName = OptionsManager.configuration.getString("prefix", "&4&lAC &f\u00c2»");
        this.logPermission = OptionsManager.configuration.getString("logs.permission", "Lighter.notify");
        this.logsPlayer = OptionsManager.configuration.getString("logs.playerlogs", "&7Violations of &4{player}&7:");
        this.logsResult = OptionsManager.configuration.getString("logs.result", " &7* &c{check} &7- x{vl}");
        this.logsSeparated = OptionsManager.configuration.getString("logs.separated", "&f&m----------------------------------");
        this.alertNoSpam = OptionsManager.configuration.getBoolean("alerts.no-spam", false);
        this.alertInformation = OptionsManager.configuration.getBoolean("alerts.information", true);
        this.alertCertainty = OptionsManager.configuration.getString("alerts.certainty", "&7using");
        this.alertExperimental = OptionsManager.configuration.getString("alerts.experimental", "&7maybe using");
        this.alertMessage = OptionsManager.configuration.getString("alerts.message", "{prefix} &c{player} {certainty} &c{cheat} &7(&cx{vl}&7/&c{ping}ms&7)");
        this.alertHover = OptionsManager.configuration.getString("alerts.hover.message", "&7Click to teleport!");
        this.alertHoverCommand = OptionsManager.configuration.getString("alerts.hover.command", "tp {player}");
        this.modPermission = OptionsManager.configuration.getString("alerts.permission", "Lighter.notify");
        this.alertEnabled = OptionsManager.configuration.getString("alerts.enabled", "{prefix} &aEnabling alerts.");
        this.alertDisabled = OptionsManager.configuration.getString("alerts.disabled", "{prefix} &cDisabling alerts.");
        this.rollback = OptionsManager.configuration.getBoolean("rollback.enabled", false);
        this.banCommands = this.readList(OptionsManager.configuration.get("bans.commands", (Object)new String[] { "banip %s 30d Lighter // Cheating -S" }));
        this.banMessage = OptionsManager.configuration.getString("bans.message", "&7[&4&l{check}&7] &4%s &7was auto-banned.");
        this.banSeparated = OptionsManager.configuration.getString("bans.separated", " ");
        this.autoBan = OptionsManager.configuration.getBoolean("bans.enabled", true);
        this.banAnnouncement = OptionsManager.configuration.getBoolean("bans.announce.enabled", true);
        this.bypassEnabled = OptionsManager.configuration.getBoolean("bans.bypass.enabled", true);
        this.bypassPermission = OptionsManager.configuration.getString("bans.bypass.permission", "Lighter.bypass");
        this.async = OptionsManager.configuration.getBoolean("optimization.async.detection", false);
        this.async = OptionsManager.configuration.getBoolean("optimization.async.commands", false);
        this.async = OptionsManager.configuration.getBoolean("optimization.async.packet", false);
        this.async = OptionsManager.configuration.getBoolean("optimization.async.ban", false);
        this.maxCps = OptionsManager.configuration.getInt("clicks-per-seconds.max", 20);
        this.packetEnabled = OptionsManager.configuration.getBoolean("packet-limiter.enabled", true);
        this.packetlimiterban = OptionsManager.configuration.getInt("packet-limiter.packets-per-second.ban", 220);
        this.packetlimiterlog = OptionsManager.configuration.getInt("packet-limiter.packets-per-second.log", 200);
        this.chatEnabled = OptionsManager.configuration.getBoolean("chat-fixer.enabled", true);
        this.chatMin = OptionsManager.configuration.getInt("chat-fixer.min", 2);
        this.chatMinMessage = OptionsManager.configuration.getString("chat-fixer.minMessage", "{prefix} &cYour message is too short.");
        this.chatSameMessage = OptionsManager.configuration.getString("chat-fixer.sameMessage", "{prefix} &cYou cannot send the same message. &7({message})");
        this.errorPerm = OptionsManager.configuration.getString("error.permission", "{prefix} &cYou do not have the permission.");
        this.errorOnlyOp = OptionsManager.configuration.getString("error.only-op", "{prefix} &cOnly players op can use this command.");
        this.errorOnlyPlayer = OptionsManager.configuration.getString("error.only-player", "{prefix} &cOnly players can use this command.");
        this.errorPlayerNotFound = OptionsManager.configuration.getString("error.player-not-found", "{prefix} &cPlayer not found.");
        this.errorCannotFindLog = OptionsManager.configuration.getString("error.flag-not-found", "{prefix} &cCannot find flags for {player}.");
        this.errorUsageLog = OptionsManager.configuration.getString("error.usage-log", "{prefix} &cUsage: /logs <player>");
    }
    
    public String getErrorOnlyPlayer() {
        return this.errorOnlyPlayer;
    }
    
    public boolean isAlertInformation() {
        return this.alertInformation;
    }
    
    public String getChatMinMessage() {
        return this.chatMinMessage;
    }
    
    public Integer getMaxCps() {
        return this.maxCps;
    }
    
    public String getAlertHover() {
        return this.alertHover;
    }
    
    public static void saveConfig() {
        try {
            OptionsManager.configuration.save(OptionsManager.file);
        }
        catch (Exception ex) {}
    }
    
    public String getPlayerLogsResult() {
        return this.logsResult;
    }
    
    public YamlConfiguration getConfigurationCheck() {
        return OptionsManager.configurationCheck;
    }
    
    public String getAlertExp() {
        return this.alertExperimental;
    }
    
    public String getSm() {
        return this.banSeparated;
    }
    
    public static void saveConfigCheck() {
        try {
            OptionsManager.configurationCheck.save(OptionsManager.fileCheck);
        }
        catch (Exception ex) {}
    }
    
    public String getErrorPlayerNotFound() {
        return this.errorPlayerNotFound;
    }
    
    public boolean isNoSpam() {
        return this.alertNoSpam;
    }
    
    public String getErrorCannotFindLog() {
        return this.errorCannotFindLog;
    }
    
    public String getLogPermission() {
        return this.logPermission;
    }
    
    public String getErrorPerm() {
        return this.errorPerm;
    }
    
    public void loadConfig() {
        try {
            OptionsManager.configuration.load(OptionsManager.file);
        }
        catch (Exception ex) {}
    }
    
    public String getAlertMessage() {
        return this.alertMessage;
    }
    
    public String getModPermission() {
        return this.modPermission;
    }
    
    public String getErrorUsageLog() {
        return this.errorUsageLog;
    }
    
    public <T> List<T> readList(final Object o) {
        return (List<T>)((o instanceof List) ? ((List)o) : Collections.singletonList(o));
    }
    
    public void setupConfigCheck() {
        OptionsManager.fileCheck = new File(Main.getPlugin().getDataFolder(), "Lighter-Checks.yml");
        this.loadConfigCheck();
        OptionsManager.configurationCheck.options().header(OptionsManager.HEADER);
        OptionsManager.configurationCheck.options().copyDefaults(true).copyHeader(true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist B.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist C.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist D.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist D.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist E.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist E.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist F.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist F.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist G.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist G.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist H.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist H.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist I.enabled", (Object)false);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist I.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist J.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist J.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist K.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist K.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist L.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist L.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist M.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist M.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist N.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist N.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist O.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist O.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist P.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist P.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist Q.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist Q.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist R.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist R.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist S.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist S.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist T.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist T.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist U.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist U.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist V.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist V.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist W.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist W.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist X.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AimAssist X.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker C.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker D.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker D.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker E.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker E.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker F.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker F.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker G.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker G.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker H.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker H.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker I.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker I.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker J.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker J.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker K.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker K.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker L.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker L.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker M.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker M.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker N.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker N.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker O.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker O.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker P.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker P.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker Q.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker Q.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker R.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker R.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker S.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker S.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker T.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker T.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker U.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker U.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker V.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoClicker V.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.BadPackets.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly A.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly B.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly C.max-vl", (Object)30);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly D.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly D.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly E.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly E.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly F.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly F.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly G.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly G.max-vl", (Object)30);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly H.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly H.max-vl", (Object)30);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly I.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly I.max-vl", (Object)30);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly J.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly J.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly K.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly K.max-vl", (Object)30);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly L.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly L.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly M.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly M.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly N.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly N.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly O.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly O.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly P.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Fly P.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Jesus A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Jesus A.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Inventory.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura A.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura B.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura C.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura D.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura D.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura E.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura E.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura F.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura F.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura G.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura G.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura H.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura H.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura I.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura I.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura J.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura J.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura K.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura K.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura L.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura L.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura M.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura M.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura N.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura N.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura O.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura O.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura P.enabled", (Object)false);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura P.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura Q.enabled", (Object)false);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura Q.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura R.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.KillAura R.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.HitBoxes A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.HitBoxes A.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.HitBoxes B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.HitBoxes B.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Client A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Client A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Client B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Client B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Phase A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Phase A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Reach A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Reach A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Reach B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Reach B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Bad Range A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Bad Range A.max-vl", (Object)35);
        OptionsManager.configurationCheck.addDefault("checktypes.Bad Range B.enabled", (Object)false);
        OptionsManager.configurationCheck.addDefault("checktypes.Bad Range B.max-vl", (Object)35);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed A.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed B.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed C.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed D.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed D.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed E.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed E.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed F.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed F.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed G.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed G.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed Dev.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Speed Dev.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer A.max-vl", (Object)20);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer B.max-vl", (Object)20);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer C.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer D.enabled", (Object)false);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer D.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer E.enabled", (Object)false);
        OptionsManager.configurationCheck.addDefault("checktypes.Timer E.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.Velocity A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Velocity A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Velocity B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Velocity B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Velocity C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Velocity C.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Velocity D.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Velocity D.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Criticals A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Criticals A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Tower A.enabled", (Object)false);
        OptionsManager.configurationCheck.addDefault("checktypes.Tower A.max-vl", (Object)20);
        OptionsManager.configurationCheck.addDefault("checktypes.Scaffold A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Scaffold A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Scaffold B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Scaffold B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Scaffold C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Scaffold C.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Scaffold D.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Scaffold D.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.Regen A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.Regen A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidPos A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidPos A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.PingSpoof A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.PingSpoof A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.PingSpoof B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.PingSpoof B.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInteract A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInteract A.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInteract B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInteract B.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.NoSlow A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.NoSlow A.max-vl", (Object)10);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory C.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory D.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory D.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory E.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory E.max-vl", (Object)15);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory F.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory F.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory G.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory G.max-vl", (Object)15);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory H.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory H.max-vl", (Object)15);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory I.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidInventory I.max-vl", (Object)15);
        OptionsManager.configurationCheck.addDefault("checktypes.ServerCrasher A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.ServerCrasher A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.ServerCrasher B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.ServerCrasher B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.ServerCrasher C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.ServerCrasher C.max-vl", (Object)15);
        OptionsManager.configurationCheck.addDefault("checktypes.MultiUse A.enabled", (Object)false);
        OptionsManager.configurationCheck.addDefault("checktypes.MultiUse A.max-vl", (Object)20);
        OptionsManager.configurationCheck.addDefault("checktypes.FastBreak A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.FastBreak A.max-vl", (Object)20);
        OptionsManager.configurationCheck.addDefault("checktypes.W-Tap A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.W-Tap A.max-vl", (Object)20);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidDirection A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidDirection A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidDirection B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.InvalidDirection B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoSneak A.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoSneak A.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoSneak B.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoSneak B.max-vl", (Object)5);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoSneak C.enabled", (Object)true);
        OptionsManager.configurationCheck.addDefault("checktypes.AutoSneak C.max-vl", (Object)5);
        saveConfigCheck();
    }
    
    public String getLiscence() {
        return this.liscence;
    }
    
    public String getLogsSepearted() {
        return this.logsSeparated;
    }
    
    static {
        OptionsManager.HEADER = "Lighter - AntiCheat.\nTo change a value you have to stop your server before.\n";
    }
    
    public YamlConfiguration getConfiguration() {
        return OptionsManager.configuration;
    }
    
    public void setupConfig() {
        OptionsManager.file = new File(Main.getPlugin().getDataFolder(), "Lighter-Config.yml");
        this.loadConfig();
        OptionsManager.configuration.options().header(OptionsManager.HEADER);
        OptionsManager.configuration.options().copyDefaults(true).copyHeader(true);
        OptionsManager.configuration.addDefault("liscence", (Object)"Your liscence here");
        OptionsManager.configuration.addDefault("prefix", (Object)"&4&lAC &f\u00c2»");
        OptionsManager.configuration.addDefault("logs.permission", (Object)"Lighter.notify");
        OptionsManager.configuration.addDefault("logs.playerlogs", (Object)"&7Violations of &4{player}&7:");
        OptionsManager.configuration.addDefault("logs.result", (Object)" &7* &c{check} &7- x{vl}");
        OptionsManager.configuration.addDefault("logs.separated", (Object)"&f&m----------------------------------");
        OptionsManager.configuration.addDefault("alerts.no-spam", (Object)false);
        OptionsManager.configuration.addDefault("alerts.information", (Object)true);
        OptionsManager.configuration.addDefault("alerts.certainty", (Object)"&7using");
        OptionsManager.configuration.addDefault("alerts.experimental", (Object)"&7maybe using");
        OptionsManager.configuration.addDefault("alerts.message", (Object)"{prefix} &c{player} {certainty} &c{cheat} &7(&cx{vl}&7/&c{ping}ms&7)");
        OptionsManager.configuration.addDefault("alerts.hover.message", (Object)"&7Click to teleport!");
        OptionsManager.configuration.addDefault("alerts.hover.command", (Object)"tp {player}");
        OptionsManager.configuration.addDefault("alerts.permission", (Object)"Lighter.notify");
        OptionsManager.configuration.addDefault("alerts.enabled", (Object)"{prefix} &aEnabling alerts.");
        OptionsManager.configuration.addDefault("alerts.disabled", (Object)"{prefix} &cDisabling alerts.");
        OptionsManager.configuration.addDefault("rollback.enabled", (Object)false);
        OptionsManager.configuration.addDefault("bans.commands", (Object)new String[] { "banip %s 30d Lighter // Cheating -S" });
        OptionsManager.configuration.addDefault("bans.message", (Object)"&7[&4&l{check}&7] &4%s &7was auto-banned.");
        OptionsManager.configuration.addDefault("bans.separated", (Object)" ");
        OptionsManager.configuration.addDefault("bans.enabled", (Object)true);
        OptionsManager.configuration.addDefault("bans.announce.enabled", (Object)true);
        OptionsManager.configuration.addDefault("bans.bypass.enabled", (Object)true);
        OptionsManager.configuration.addDefault("bans.bypass.permission", (Object)"Lighter.bypass");
        OptionsManager.configuration.addDefault("optimization.async.detection", (Object)false);
        OptionsManager.configuration.addDefault("optimization.async.commands", (Object)false);
        OptionsManager.configuration.addDefault("optimization.async.packet", (Object)false);
        OptionsManager.configuration.addDefault("optimization.async.ban", (Object)false);
        OptionsManager.configuration.addDefault("optimization.ticks.fly", (Object)1);
        OptionsManager.configuration.addDefault("optimization.ticks.speed", (Object)1);
        OptionsManager.configuration.addDefault("clicks-per-seconds.max", (Object)20);
        OptionsManager.configuration.addDefault("packet-limiter.enabled", (Object)true);
        OptionsManager.configuration.addDefault("packet-limiter.packets-per-second.log", (Object)200);
        OptionsManager.configuration.addDefault("packet-limiter.packets-per-second.ban", (Object)220);
        OptionsManager.configuration.addDefault("chat-fixer.enabled", (Object)true);
        OptionsManager.configuration.addDefault("chat-fixer.min", (Object)2);
        OptionsManager.configuration.addDefault("chat-fixer.minMessage", (Object)"{prefix} &cYour message is too short.");
        OptionsManager.configuration.addDefault("chat-fixer.sameMessage", (Object)"{prefix} &cYou cannot send the same message. &7({message})");
        OptionsManager.configuration.addDefault("error.permission", (Object)"{prefix} &cYou do not have the permission.");
        OptionsManager.configuration.addDefault("error.only-op", (Object)"{prefix} &cOnly players op can use this command.");
        OptionsManager.configuration.addDefault("error.only-player", (Object)"{prefix} &cOnly players can use this command.");
        OptionsManager.configuration.addDefault("error.player-not-found", (Object)"{prefix} &cPlayer not found.");
        OptionsManager.configuration.addDefault("error.flag-not-found", (Object)"{prefix} &cCannot find flags for {player}.");
        OptionsManager.configuration.addDefault("error.usage-log", (Object)"{prefix} &cUsage: /logs <player>");
        saveConfig();
    }
    
    public String getAlertEnabled() {
        return this.alertEnabled;
    }
    
    public String getAlertHoverCommand() {
        return this.alertHoverCommand;
    }
    
    public String getBypassPermission() {
        return this.bypassPermission;
    }
    
    public boolean isAutoBan() {
        return this.autoBan;
    }
    
    public String getAnticheatName() {
        return this.anticheatName;
    }
    
    public String getBanMessage() {
        return this.banMessage;
    }
}
