package com.lighter.data.manager;

import org.bukkit.entity.*;
import com.lighter.data.*;
import com.lighter.check.impl.*;
import com.lighter.main.*;
import org.bukkit.*;
import java.util.concurrent.*;
import net.md_5.bungee.api.chat.*;
import java.text.*;
import java.util.*;
import com.lighter.util.*;
import java.util.function.*;
import org.bukkit.scheduler.*;
import org.bukkit.plugin.*;

public class AlertsManager
{
    private final ExecutorService executorService;
    private static final String CERTAINTY;
    public static AlertsManager instance;
    private static final String DEBUG;
    private static final String NAME;
    private static ArrayList<Player> cooldown;
    private static final String ALERT;
    private static final String CERTAINTYEXP;
    
    public void Violation(final PlayerData playerData, final Check check, final String s, final double n, final boolean b) {
        Main.getPlugin().lastCheck.put(playerData.getPlayer().getUniqueId(), String.valueOf(new StringBuilder().append(check.getFriendlyName()).append(" ").append(check.getSubType())));
        this.executorService.submit(this::lambda$Violation$20);
    }
    
    public void _handleBan(final PlayerData playerData, final Check check) {
        PunishmentManager.getInstance().insertBan(playerData, check);
    }
    
    public void handleBan(final PlayerData playerData, final Check check) {
        if (CheckManager.getInstance().enabled(check.getType()) && OptionsManager.getInstance().isAutoBan() && check.getCheckVersion() == Check.CheckVersion.RELEASE && !playerData.isBanned() && !playerData.isDebug() && (!OptionsManager.getInstance().isBypassEnabled() || !playerData.getPlayer().hasPermission(OptionsManager.getInstance().getBypassPermission()))) {
            this._handleBan(playerData, check);
        }
    }
    
    static {
        NAME = OptionsManager.getInstance().getAnticheatName();
        CERTAINTY = OptionsManager.getInstance().getAlertCertainty();
        CERTAINTYEXP = OptionsManager.getInstance().getAlertExp();
        ALERT = OptionsManager.getInstance().getAlertMessage();
        DEBUG = String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("[").append(ChatColor.DARK_RED).append(ChatColor.BOLD).append("DEBUG").append(ChatColor.GRAY).append("] ").append(ChatColor.RED).append("%s").append(ChatColor.GRAY).append(" x ").append(ChatColor.RED).append("%s").append(ChatColor.GRAY).append(" x ").append(ChatColor.DARK_RED).append("%s"));
    }
    
    public void handleDebug(final PlayerData playerData, final Check check, final String s) {
        PlayerManager.getInstance().getPlayers().values().stream().filter(PlayerData::isDebug).map((Function<? super PlayerData, ?>)PlayerData::getPlayer).forEach(AlertsManager::lambda$handleDebug$22);
    }
    
    private void _handleViolation(final PlayerData playerData, final Check check, final String s, final double n, final boolean b) {
        if (playerData.isEnabled()) {
            check.setViolations(check.getViolations() + n);
            final int lastViolation = (int)Math.floor(check.getViolations());
            if (check.getViolations() > 0.0 && CheckManager.getInstance().enabled(check.getType())) {
                this.handleDebug(playerData, check, s);
            }
            if (lastViolation > 0) {
                if (lastViolation > check.getLastViolation()) {
                    this.handleAlert(playerData, check, s, lastViolation, b);
                    if (check.getViolations() >= check.getMaxViolation()) {
                        this.handleBan(playerData, check);
                    }
                }
                check.setLastViolation(lastViolation);
            }
        }
    }
    
    static ArrayList access$000() {
        return AlertsManager.cooldown;
    }
    
    private AlertsManager() {
        this.executorService = Executors.newSingleThreadExecutor();
        AlertsManager.cooldown = new ArrayList<Player>();
    }
    
    public void sendCommandMsg(final Player player, final String s, final Player player2, final boolean b, final String s2) {
        final TextComponent textComponent = new TextComponent(s);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.valueOf(new StringBuilder().append("/").append(OptionsManager.getInstance().getAlertHoverCommand().replace("{player}", player2.getName())))));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.valueOf(new StringBuilder().append(b ? String.valueOf(new StringBuilder().append(ChatColor.GRAY).append(" * ").append(ChatColor.WHITE).append(s2).append("\n").append("\n")) : "").append(OptionsManager.getInstance().getAlertHover().replace('&', '§')))).create()));
        player.spigot().sendMessage((BaseComponent)textComponent);
    }
    
    private void lambda$handleAlert$21(final Check check, final String s, final String s2, final PlayerData playerData, final boolean b, final String s3, final Player player) {
        this.sendCommandMsg(player, (check.getCheckVersion() == Check.CheckVersion.RELEASE) ? s : s2, playerData.getPlayer(), b, s3);
    }
    
    public static AlertsManager getInstance() {
        return (AlertsManager.instance == null) ? (AlertsManager.instance = new AlertsManager()) : AlertsManager.instance;
    }
    
    private void lambda$Violation$20(final PlayerData playerData, final Check check, final String s, final double n, final boolean b) {
        this._handleViolation(playerData, check, s, n, b);
    }
    
    private static void lambda$handleDebug$22(final PlayerData playerData, final Check check, final String s, final Player player) {
        player.sendMessage(String.format(AlertsManager.DEBUG, playerData.getPlayer().getName(), check.getType().getName(), s, null, null));
    }
    
    public String getFormattedAlert(final String s, final String s2, final String s3, final String s4, final Integer n) {
        return ChatColor.translateAlternateColorCodes('&', AlertsManager.ALERT.replace("{prefix}", AlertsManager.NAME).replace("{player}", s).replace("{certainty}", AlertsManager.CERTAINTY).replace("{cheat}", s2).replace("{vl}", s4).replace("{ping}", n.toString()).replace("{data}", s3));
    }
    
    public void handleAlert(final PlayerData playerData, final Check check, final String s, final int n, final boolean b) {
        final boolean alertInformation = OptionsManager.getInstance().isAlertInformation();
        final String formattedAlert = this.getFormattedAlert(playerData.getPlayer().getName(), String.valueOf(new StringBuilder().append(check.getFriendlyName()).append(alertInformation ? String.valueOf(new StringBuilder().append(" ").append(check.getSubType())) : "")), s, String.valueOf(n), playerData.getPing());
        final String formattedExpir = this.getFormattedExpir(playerData.getPlayer().getName(), String.valueOf(new StringBuilder().append(check.getFriendlyName()).append(alertInformation ? String.valueOf(new StringBuilder().append(" ").append(check.getSubType())) : "")), s, String.valueOf(n), playerData.getPing());
        if (CheckManager.getInstance().enabled(check.getType())) {
            BukkitUtils.log(playerData.getPlayer(), String.valueOf(new StringBuilder().append("[").append(new SimpleDateFormat("dd/MM/yy hh:mm:ss").format(new Date())).append("], ").append(check.getFriendlyName()).append(" (Type ").append(check.getSubType()).append(")@")));
            if ((OptionsManager.getInstance().isNoSpam() && AlertsManager.cooldown.contains(playerData.getPlayer()) && !check.getFriendlyName().contains("Reach")) || (AlertsManager.cooldown.contains(playerData.getPlayer()) && !check.getFriendlyName().contains("KillAura")) || (AlertsManager.cooldown.contains(playerData.getPlayer()) && !check.getFriendlyName().contains("Auto Clicker")) || (AlertsManager.cooldown.contains(playerData.getPlayer()) && !check.getFriendlyName().contains("Velocity")) || AlertsManager.cooldown.contains(playerData.getPlayer())) {
                return;
            }
            PlayerManager.getInstance().getPlayers().values().stream().filter(PlayerData::isAlerts).map((Function<? super PlayerData, ?>)PlayerData::getPlayer).forEach((Consumer<? super Object>)this::lambda$handleAlert$21);
            if (OptionsManager.getInstance().isNoSpam()) {
                AlertsManager.cooldown.add(playerData.getPlayer());
                new BukkitRunnable(this, playerData) {
                    final PlayerData val$playerData;
                    final AlertsManager this$0;
                    
                    public void run() {
                        AlertsManager.access$000().remove(this.val$playerData.getPlayer());
                    }
                }.runTaskLaterAsynchronously((Plugin)Main.getPlugin(), 15L);
            }
        }
    }
    
    public void handleViolation(final PlayerData playerData, final Check check, final String s, final boolean b) {
        this.handleViolation(playerData, check, s, 1.0, b);
    }
    
    public String getFormattedExpir(final String s, final String s2, final String s3, final String s4, final Integer n) {
        return ChatColor.translateAlternateColorCodes('&', AlertsManager.ALERT.replace("{prefix}", AlertsManager.NAME).replace("{player}", s).replace("{certainty}", AlertsManager.CERTAINTYEXP).replace("{cheat}", s2).replace("{vl}", s4).replace("{ping}", n.toString()).replace("{data}", s3));
    }
    
    public void handleViolation(final PlayerData playerData, final Check check, final String s, final double n, final boolean b) {
        this.Violation(playerData, check, s, n, b);
    }
}
