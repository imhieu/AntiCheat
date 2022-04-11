package com.lighter.command;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import java.text.*;
import net.md_5.bungee.api.*;
import com.lighter.main.*;
import java.lang.management.*;
import com.lighter.util.*;
import org.bukkit.*;

public class PerfCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorOnlyPlayer().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        if (!commandSender.hasPermission("Lighter.*") || !commandSender.isOp()) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorPerm().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        if (array.length == 0) {
            final long currentTimeMillis = System.currentTimeMillis();
            final double n = (double)(Runtime.getRuntime().freeMemory() / 1024L / 1024L);
            final double n2 = (double)(Runtime.getRuntime().maxMemory() / 1024L / 1024L);
            final double n3 = n / n2;
            final DecimalFormat decimalFormat = new DecimalFormat("00.00");
            final DecimalFormat decimalFormat2 = new DecimalFormat("00");
            final DecimalFormat decimalFormat3 = new DecimalFormat("00.0");
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Lighter version ").append(Main.getPlugin().Version).append(".")));
            commandSender.sendMessage("");
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Runtime").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(DateUtil.formatDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime()))));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("TPS").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(decimalFormat.format(Bukkit.spigot().getTPS()[0]))));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Load Ram").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(decimalFormat3.format(n3 * 100.0)).append("%")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Max Ram").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(decimalFormat2.format(n2)).append("MB")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Used Ram").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(decimalFormat2.format(n)).append("MB")));
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("(Perf loaded in ").append(System.currentTimeMillis() - currentTimeMillis).append("ms)")));
            commandSender.sendMessage("");
        }
        return false;
    }
}
