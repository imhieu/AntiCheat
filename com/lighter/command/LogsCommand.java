package com.lighter.command;

import com.lighter.data.manager.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import com.lighter.util.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;

public class LogsCommand implements CommandExecutor
{
    private void lambda$onCommand$19(final CommandSender commandSender, final String s, final String s2) {
        commandSender.sendMessage(OptionsManager.getInstance().getLogsSepearted().replace('&', '§'));
        commandSender.sendMessage("");
        commandSender.sendMessage(OptionsManager.getInstance().getPlayerLogs().replace('&', '§').replace("{player}", Bukkit.getOfflinePlayer(s).getName()));
        commandSender.sendMessage("");
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        String[] split;
        for (int length = (split = s2.split("@")).length, i = 0; i < length; ++i) {
            final String s3 = split[i].split(",")[1];
            if (hashMap.containsKey(s3)) {
                hashMap.replace(s3, hashMap.get(s3) + 1);
            }
            else {
                hashMap.put(s3, 1);
            }
        }
        String value = "";
        for (HashMap<String, Integer> hashMap2 = this.getHighestEntry(hashMap, value); hashMap2 != null; hashMap2 = this.getHighestEntry(hashMap, value)) {
            for (final String s4 : hashMap2.keySet()) {
                commandSender.sendMessage(OptionsManager.getInstance().getPlayerLogsResult().replace('&', '§').replace("{check}", s4).replace("{vl}", hashMap2.get(s4).toString()));
                value = String.valueOf(new StringBuilder().append(value).append(s4));
            }
        }
        commandSender.sendMessage("");
        commandSender.sendMessage(OptionsManager.getInstance().getLogsSepearted().replace('&', '§'));
    }
    
    private HashMap<String, Integer> getHighestEntry(final HashMap<String, Integer> hashMap, final String s) {
        final HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();
        int intValue = 0;
        String s2 = null;
        for (final String s3 : hashMap.keySet()) {
            if (!s.contains(s3) && hashMap.get(s3) > intValue) {
                intValue = hashMap.get(s3);
                s2 = s3;
            }
        }
        if (s2 == null || intValue == 0) {
            return null;
        }
        hashMap2.put(s2, intValue);
        return hashMap2;
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorOnlyPlayer().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        if (!commandSender.hasPermission(OptionsManager.getInstance().getLogPermission())) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorPerm().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        if (array.length == 0) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorUsageLog().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return true;
        }
        if (array.length == 1) {
            final String lowerCase = array[0].toLowerCase();
            final String fromFile = BukkitUtils.getFromFile(String.valueOf(new StringBuilder().append("plugins/Lighter/data/").append(lowerCase).append(".LighterLog")));
            if (fromFile.equals("-1")) {
                commandSender.sendMessage(OptionsManager.getInstance().getErrorCannotFindLog().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")).replace("{player}", lowerCase));
            }
            else {
                Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask((Plugin)Main.getPlugin(), this::lambda$onCommand$19);
            }
        }
        return false;
    }
}
