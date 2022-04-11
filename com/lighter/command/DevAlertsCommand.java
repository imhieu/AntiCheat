package com.lighter.command;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import org.bukkit.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import com.lighter.data.*;

public class DevAlertsCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorOnlyPlayer().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        final Player player = (Player)commandSender;
        if (!player.hasPermission("lighter.devalerts")) {
            player.sendMessage(OptionsManager.getInstance().getErrorPerm().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        final PlayerData player2 = PlayerManager.getInstance().getPlayer(player);
        if (player2 != null) {
            player2.setDebug(!player2.isDebug());
            if (player2.isDebug()) {
                player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.GREEN).append("Enabling devalerts.")));
                player.setMetadata("UCHEAT_DEBUG", (MetadataValue)new FixedMetadataValue((Plugin)Main.getPlugin(), (Object)true));
            }
            else {
                player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Disabling devalerts.")));
                player.removeMetadata("UCHEAT_DEBUG", (Plugin)Main.getPlugin());
            }
        }
        return true;
    }
}
