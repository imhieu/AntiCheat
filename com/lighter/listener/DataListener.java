package com.lighter.listener;

import java.util.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import com.lighter.util.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class DataListener implements Listener
{
    private final PlayerManager playerManager;
    public HashMap<Player, String> same;
    
    @EventHandler
    public void AsyncPlayerChatEvent(final AsyncPlayerChatEvent asyncPlayerChatEvent) {
        final Player player = asyncPlayerChatEvent.getPlayer();
        if (OptionsManager.getInstance().isChatEnabled()) {
            if (asyncPlayerChatEvent.getMessage().length() < OptionsManager.getInstance().getChatMin()) {
                asyncPlayerChatEvent.setCancelled(true);
                player.sendMessage(OptionsManager.getInstance().getChatMinMessage().replace("&", "\u00c2§").replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "\u00c2§")));
                return;
            }
            if (this.same.containsKey(player) && asyncPlayerChatEvent.getMessage().equalsIgnoreCase(this.same.get(player))) {
                asyncPlayerChatEvent.setCancelled(true);
                player.sendMessage(OptionsManager.getInstance().getChatSameMessage().replace("&", "\u00c2§").replace("{message}", asyncPlayerChatEvent.getMessage()).replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "\u00c2§")));
            }
            this.same.put(player, asyncPlayerChatEvent.getMessage());
        }
        if (Data.isLogCmd(player)) {
            asyncPlayerChatEvent.setCancelled(true);
            if (asyncPlayerChatEvent.getMessage().contains("cancel")) {
                Data.listLogCmd().remove(player);
                player.sendMessage("\u00c2§cEditing message of \u00c2§lLog Cmd \u00c2§ccanceled.");
                return;
            }
            OptionsManager.getInstance().getConfiguration().set("logs.playerlogs", (Object)asyncPlayerChatEvent.getMessage());
            OptionsManager.getInstance().logsPlayer = asyncPlayerChatEvent.getMessage();
            player.sendMessage(String.valueOf(new StringBuilder().append("\u00c2§aYou have changed the message to \u00c2§f").append(asyncPlayerChatEvent.getMessage())));
            OptionsManager.saveConfig();
        }
        if (Data.isLogSMCmd(player)) {
            asyncPlayerChatEvent.setCancelled(true);
            if (asyncPlayerChatEvent.getMessage().contains("cancel")) {
                Data.listLogSMCmd().remove(player);
                player.sendMessage("\u00c2§cEditing message of \u00c2§lSeperated Logs \u00c2§ccanceled.");
                return;
            }
            OptionsManager.getInstance().getConfiguration().set("logs.separated", (Object)asyncPlayerChatEvent.getMessage().replace("'", "''"));
            OptionsManager.getInstance().logsSeparated = asyncPlayerChatEvent.getMessage();
            player.sendMessage(String.valueOf(new StringBuilder().append("\u00c2§aYou have changed the message to \u00c2§f").append(asyncPlayerChatEvent.getMessage())));
            OptionsManager.saveConfig();
        }
        if (Data.isAlertc(player)) {
            asyncPlayerChatEvent.setCancelled(true);
            if (asyncPlayerChatEvent.getMessage().contains("cancel")) {
                Data.listAlertc().remove(player);
                player.sendMessage("\u00c2§cEditing message of \u00c2§lCertainty Alerts \u00c2§ccanceled.");
                return;
            }
            OptionsManager.getInstance().getConfiguration().set("alerts.certainty", (Object)asyncPlayerChatEvent.getMessage().replace("'", "''"));
            OptionsManager.getInstance().alertCertainty = asyncPlayerChatEvent.getMessage();
            player.sendMessage(String.valueOf(new StringBuilder().append("\u00c2§aYou have changed the message to \u00c2§f").append(asyncPlayerChatEvent.getMessage())));
            OptionsManager.saveConfig();
        }
        if (Data.isAlerte(player)) {
            asyncPlayerChatEvent.setCancelled(true);
            if (asyncPlayerChatEvent.getMessage().contains("cancel")) {
                Data.listAlerte().remove(player);
                player.sendMessage("\u00c2§cEditing message of \u00c2§lExperimental Alerts \u00c2§ccanceled.");
                return;
            }
            OptionsManager.getInstance().getConfiguration().set("alerts.experimental", (Object)asyncPlayerChatEvent.getMessage().replace("'", "''"));
            OptionsManager.getInstance().alertExperimental = asyncPlayerChatEvent.getMessage();
            player.sendMessage(String.valueOf(new StringBuilder().append("\u00c2§aYou have changed the message to \u00c2§f").append(asyncPlayerChatEvent.getMessage())));
            OptionsManager.saveConfig();
        }
        if (Data.isBanBroadcast(player)) {
            asyncPlayerChatEvent.setCancelled(true);
            if (asyncPlayerChatEvent.getMessage().contains("cancel")) {
                Data.listBan().remove(player);
                player.sendMessage("\u00c2§cEditing message of \u00c2§lAuto-Ban Broadcast \u00c2§ccanceled.");
                return;
            }
            OptionsManager.getInstance().getConfiguration().set("bans.message", (Object)asyncPlayerChatEvent.getMessage().replace("'", "''"));
            OptionsManager.getInstance().banMessage = asyncPlayerChatEvent.getMessage();
            player.sendMessage(String.valueOf(new StringBuilder().append("\u00c2§aYou have changed the message to \u00c2§f").append(asyncPlayerChatEvent.getMessage())));
            OptionsManager.saveConfig();
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent playerJoinEvent) {
        final Player player = playerJoinEvent.getPlayer();
        if (player.hasPermission(OptionsManager.getInstance().getModPermission())) {
            player.setMetadata("UCHEAT_ALERTS", (MetadataValue)new FixedMetadataValue((Plugin)Main.getPlugin(), (Object)true));
        }
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Main.getPlugin(), this::lambda$onJoin$0);
    }
    
    @EventHandler
    public void onRespawn(final PlayerRespawnEvent playerRespawnEvent) {
        this.playerManager.getPlayer(playerRespawnEvent.getPlayer()).handle((Event)playerRespawnEvent);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(final PlayerQuitEvent playerQuitEvent) {
        final Player player = playerQuitEvent.getPlayer();
        this.playerManager.uninject(player);
        if (player.hasPermission("admin.use")) {
            player.removeMetadata("UCHEAT_DEBUG", (Plugin)Main.getPlugin());
        }
    }
    
    private void lambda$onJoin$0(final Player player, final PlayerJoinEvent playerJoinEvent) {
        this.playerManager.inject(player);
        this.playerManager.getPlayer(player).handle((Event)playerJoinEvent);
    }
    
    public DataListener() {
        this.playerManager = PlayerManager.getInstance();
        this.same = new HashMap<Player, String>();
    }
}
