package com.lighter.command;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import net.md_5.bungee.api.*;
import com.lighter.data.*;

public class PlayerCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorOnlyPlayer().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        final PlayerData player = PlayerManager.getInstance().getPlayer((Player)commandSender);
        if (!commandSender.hasPermission(OptionsManager.getInstance().getModPermission()) || !commandSender.isOp() || !commandSender.hasPermission("Lighter.*")) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorPerm().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        if (array.length == 0) {
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Your ping")));
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Ping").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player.getPing()).append("ms")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Average Ping").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player.getAveragePing()).append("ms")));
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Ping Ticks").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player.getPingTicks())));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Max Ping Ticks").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player.getMaxPingTicks())));
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Lagging").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player.hasLag())));
            commandSender.sendMessage("");
        }
        else if (array.length == 1) {
            final Player player2 = commandSender.getServer().getPlayer(array[0]);
            if (player2 == null) {
                commandSender.sendMessage(OptionsManager.getInstance().getErrorPlayerNotFound().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
                return false;
            }
            final PlayerData player3 = PlayerManager.getInstance().getPlayer(player2.getPlayer());
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Checked Ping").append(ChatColor.GRAY).append(" : ").append(ChatColor.RED).append(player2.getName())));
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Ping").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player3.getPing()).append("ms")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Average Ping").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player3.getAveragePing()).append("ms")));
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Ping Ticks").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player3.getPingTicks())));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Max Ping Ticks").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player3.getMaxPingTicks())));
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Lagging").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(player3.hasLag())));
            commandSender.sendMessage("");
        }
        return false;
    }
}
