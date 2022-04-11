package com.lighter.data.manager;

import com.lighter.data.*;
import com.lighter.check.impl.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;
import org.bukkit.command.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import java.util.*;

public class PunishmentManager
{
    private static PunishmentManager instance;
    
    public void enable() {
    }
    
    public void insertBan(final PlayerData playerData, final Check check) {
        playerData.setBanned(true);
        final String name = playerData.getPlayer().getName();
        if (OptionsManager.getInstance().isBanAnnouncement()) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', OptionsManager.getInstance().getSm()));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', String.format(OptionsManager.getInstance().getBanMessage().replace("{check}", check.getFriendlyName()), playerData.getPlayer().getName())));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', OptionsManager.getInstance().getSm()));
        }
        final Iterator<String> iterator = OptionsManager.getInstance().getBanCommands().iterator();
        while (iterator.hasNext()) {
            new BukkitRunnable(this, iterator.next(), name, check) {
                final String val$name;
                final PunishmentManager this$0;
                final String val$banCommand;
                final Check val$check;
                
                public void run() {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), String.format(this.val$banCommand, this.val$name).replace("{check}", this.val$check.getFriendlyName()));
                }
            }.runTask((Plugin)Main.getPlugin());
        }
    }
    
    public void disable() {
    }
    
    public static PunishmentManager getInstance() {
        return (PunishmentManager.instance == null) ? (PunishmentManager.instance = new PunishmentManager()) : PunishmentManager.instance;
    }
}
