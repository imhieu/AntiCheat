package com.lighter.command;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import com.lighter.data.manager.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import com.lighter.data.*;

public class AlertsCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorOnlyPlayer().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        final Player player = (Player)commandSender;
        if (!player.hasPermission(OptionsManager.getInstance().getModPermission())) {
            player.sendMessage(OptionsManager.getInstance().getErrorPerm().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        final PlayerData player2 = PlayerManager.getInstance().getPlayer(player);
        if (player2 != null) {
            player2.setAlerts(!player2.isAlerts());
            if (player2.isAlerts()) {
                player.sendMessage(OptionsManager.getInstance().getAlertEnabled().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
                player.setMetadata("UCHEAT_ALERTS", (MetadataValue)new FixedMetadataValue((Plugin)Main.getPlugin(), (Object)true));
            }
            else {
                player.sendMessage(OptionsManager.getInstance().getAlertDisabled().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
                player.removeMetadata("UCHEAT_ALERTS", (Plugin)Main.getPlugin());
            }
        }
        return true;
    }
}
