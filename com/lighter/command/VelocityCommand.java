package com.lighter.command;

import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.command.*;
import net.md_5.bungee.api.*;
import com.lighter.data.manager.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;
import com.lighter.data.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import org.bukkit.util.*;

public class VelocityCommand implements CommandExecutor
{
    int vl;
    HashMap<Player, Float> walkpeed;
    ArrayList<Player> inCheck;
    double yDiff;
    double lastLocation;
    
    public VelocityCommand() {
        this.inCheck = new ArrayList<Player>();
        this.walkpeed = new HashMap<Player, Float>();
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorOnlyPlayer().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        if (!commandSender.hasPermission(OptionsManager.getInstance().getModPermission()) || !commandSender.isOp() || !commandSender.hasPermission("Lighter.*")) {
            commandSender.sendMessage(OptionsManager.getInstance().getErrorPerm().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
            return false;
        }
        if (array.length == 0) {
            commandSender.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("/veltest <player>")));
            return false;
        }
        if (array.length == 1) {
            final Player player = commandSender.getServer().getPlayer(array[0]);
            if (player == null) {
                commandSender.sendMessage(OptionsManager.getInstance().getErrorPlayerNotFound().replace('&', '§').replace("{prefix}", OptionsManager.getInstance().getAnticheatName().replace("&", "§")));
                return false;
            }
            final PlayerData player2 = PlayerManager.getInstance().getPlayer(player);
            if (player2.hasLag() || player2.getPing() > 130) {
                commandSender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.RED).append(" Error! Player is lagging.")));
                return false;
            }
            if (this.inCheck.contains(player)) {
                commandSender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.RED).append(" He is already in velocity test, please wait...")));
                return false;
            }
            if (!player.getLocation().getBlock().getRelative(BlockFace.UP).getType().equals((Object)Material.AIR) || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals((Object)Material.ICE)) {
                commandSender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.RED).append(" Error! Player is in bad position.")));
                return false;
            }
            switch (player.getLocation().getBlock().getType()) {
                case WATER:
                case WEB:
                case LAVA: {
                    commandSender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.RED).append(" Error! Player is in bad position.")));
                    return false;
                }
                default: {
                    commandSender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.WHITE).append(" Launching velocity test on ").append(ChatColor.RED).append(player.getName()).append(ChatColor.WHITE).append(".")));
                    this.addVelocity(player);
                    new BukkitRunnable(this, player, commandSender, player2) {
                        final PlayerData val$targetData;
                        final Player val$t;
                        final CommandSender val$sender;
                        final VelocityCommand this$0;
                        
                        public void run() {
                            this.this$0.yDiff = this.val$t.getLocation().getY() - this.this$0.lastLocation;
                            this.val$sender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.WHITE).append(" Test on ").append(ChatColor.RED).append(this.val$t.getName()).append(ChatColor.WHITE).append(".")));
                            if (this.this$0.yDiff <= 3.9 && this.this$0.yDiff > 3.7) {
                                final VelocityCommand this$0 = this.this$0;
                                ++this$0.vl;
                                this.val$sender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.WHITE).append(" Result:").append(ChatColor.RED).append(" Maybe").append(ChatColor.WHITE).append(" VL:").append(ChatColor.RED).append(this.this$0.vl + this.val$targetData.getPingTicks() * 2 + this.val$targetData.getPing() / 2)));
                            }
                            else if (this.this$0.yDiff <= 3.7) {
                                final VelocityCommand this$2 = this.this$0;
                                ++this$2.vl;
                                this.val$sender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.WHITE).append(" Result:").append(ChatColor.DARK_RED).append(" Detected").append(ChatColor.WHITE).append(" VL:").append(ChatColor.RED).append(this.this$0.vl + this.val$targetData.getPingTicks() * 10 + this.val$targetData.getPing() / 2)));
                            }
                            else {
                                this.val$sender.sendMessage(String.valueOf(new StringBuilder().append(OptionsManager.getInstance().getAnticheatName().replace("&", "§")).append(ChatColor.WHITE).append(" Result:").append(ChatColor.GREEN).append(" Not Detected")));
                            }
                            this.val$t.setWalkSpeed((float)this.this$0.walkpeed.get(this.val$t));
                            this.this$0.walkpeed.remove(this.val$t);
                            this.this$0.vl = 0;
                        }
                    }.runTaskLater((Plugin)Main.getPlugin(), 10L);
                    this.remove(player);
                    break;
                }
            }
        }
        return false;
    }
    
    private void remove(final Player player) {
        new BukkitRunnable(this, player) {
            final VelocityCommand this$0;
            final Player val$p;
            
            public void run() {
                this.this$0.inCheck.remove(this.val$p);
            }
        }.runTaskLaterAsynchronously((Plugin)Main.getPlugin(), 30L);
    }
    
    private void addVelocity(final Player player) {
        this.walkpeed.put(player, player.getWalkSpeed());
        this.lastLocation = player.getLocation().getY();
        player.setWalkSpeed(0.0f);
        player.setVelocity(new Vector(0, 1, 0));
        this.inCheck.add(player);
    }
}
