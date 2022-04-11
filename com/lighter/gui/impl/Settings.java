package com.lighter.gui.impl;

import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import com.lighter.gui.*;
import com.lighter.data.manager.*;
import org.bukkit.*;
import java.util.*;
import com.lighter.util.*;
import org.bukkit.inventory.meta.*;

public class Settings extends Gui
{
    private ItemStack checkStack10;
    private ItemStack checkStack2;
    private ItemStack checkStack11;
    private ItemStack checkStack13;
    private ItemStack checkStack12;
    private ItemStack checkStack4;
    private ItemStack checkStack;
    private ItemStack checkStack3;
    
    private void editMessage(final Player player, final String s, final String s2, final boolean b, final String s3) {
        player.getPlayer().sendMessage("");
        player.getPlayer().sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Editing message of ").append(ChatColor.BOLD).append(s).append(ChatColor.RED).append(".")));
        player.getPlayer().sendMessage("");
        player.getPlayer().sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("The last message is")));
        player.getPlayer().sendMessage(String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(s2)));
        player.getPlayer().sendMessage("");
        if (b) {
            player.getPlayer().sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Player").append(ChatColor.GRAY).append(" = ").append(ChatColor.WHITE).append(s3)));
            player.getPlayer().sendMessage("");
        }
        player.getPlayer().sendMessage(String.valueOf(new StringBuilder().append(ChatColor.GREEN).append("Enter in the chat the new message.")));
        player.getPlayer().sendMessage(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Type 'cancel' for cancel.")));
        player.getPlayer().sendMessage("");
    }
    
    @Override
    public void onClick(final ClickData clickData) {
        if (clickData.getSlot() == 26) {
            clickData.getPlayer().closeInventory();
            GuiManager.getInstance().getacGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 1) {
            if (!OptionsManager.getInstance().getAlertNoSpam()) {
                OptionsManager.getInstance().getConfiguration().set("alerts.no-spam", (Object)true);
                OptionsManager.getInstance().alertNoSpam = true;
            }
            else {
                OptionsManager.getInstance().getConfiguration().set("alerts.no-spam", (Object)false);
                OptionsManager.getInstance().alertNoSpam = false;
            }
            this.checkStack = new ItemStack(OptionsManager.getInstance().getAlertNoSpam() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            final ItemMeta itemMeta = this.checkStack.getItemMeta();
            itemMeta.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("No-Spam Alert")));
            itemMeta.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to avoid spam alert.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().getAlertNoSpam())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
            this.checkStack.setItemMeta(itemMeta);
            this.inventory.setItem(1, this.checkStack);
            OptionsManager.saveConfig();
        }
        if (clickData.getSlot() == 2) {
            if (!OptionsManager.getInstance().isAutoBan()) {
                OptionsManager.getInstance().getConfiguration().set("bans.enabled", (Object)true);
                OptionsManager.getInstance().autoBan = true;
            }
            else {
                OptionsManager.getInstance().getConfiguration().set("bans.enabled", (Object)false);
                OptionsManager.getInstance().autoBan = false;
            }
            this.checkStack2 = new ItemStack(OptionsManager.getInstance().isAutoBan() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            final ItemMeta itemMeta2 = this.checkStack2.getItemMeta();
            itemMeta2.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Auto-Ban")));
            itemMeta2.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to ban a player when he's cheating.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isAutoBan())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
            this.checkStack2.setItemMeta(itemMeta2);
            this.inventory.setItem(2, this.checkStack2);
            OptionsManager.saveConfig();
        }
        if (clickData.getSlot() == 3) {
            if (!OptionsManager.getInstance().isBanAnnouncement()) {
                OptionsManager.getInstance().getConfiguration().set("bans.announce.enabled", (Object)true);
                OptionsManager.getInstance().banAnnouncement = true;
            }
            else {
                OptionsManager.getInstance().getConfiguration().set("bans.announce.enabled", (Object)false);
                OptionsManager.getInstance().banAnnouncement = false;
            }
            this.checkStack3 = new ItemStack(OptionsManager.getInstance().isBanAnnouncement() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            final ItemMeta itemMeta3 = this.checkStack3.getItemMeta();
            itemMeta3.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Auto-Ban Broadcast")));
            itemMeta3.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to send a broadcast for the auto-ban.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isBanAnnouncement())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
            this.checkStack3.setItemMeta(itemMeta3);
            this.inventory.setItem(3, this.checkStack3);
            OptionsManager.saveConfig();
        }
        if (clickData.getSlot() == 4) {
            if (!OptionsManager.getInstance().isBypassEnabled()) {
                OptionsManager.getInstance().getConfiguration().set("bans.bypass.enabled", (Object)true);
                OptionsManager.getInstance().bypassEnabled = true;
            }
            else {
                OptionsManager.getInstance().getConfiguration().set("bans.bypass.enabled", (Object)false);
                OptionsManager.getInstance().bypassEnabled = false;
            }
            this.checkStack4 = new ItemStack(OptionsManager.getInstance().isBypassEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            final ItemMeta itemMeta4 = this.checkStack4.getItemMeta();
            itemMeta4.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Auto-Ban Bypass")));
            itemMeta4.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to bypass auto-ban with a permission.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isBypassEnabled())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
            this.checkStack4.setItemMeta(itemMeta4);
            this.inventory.setItem(4, this.checkStack4);
            OptionsManager.saveConfig();
        }
        if (clickData.getSlot() == 5) {
            if (!OptionsManager.getInstance().isPacketEnabled()) {
                OptionsManager.getInstance().getConfiguration().set("packet-limiter.enabled", (Object)true);
                OptionsManager.getInstance().packetEnabled = true;
            }
            else {
                OptionsManager.getInstance().getConfiguration().set("packet-limiter.enabled", (Object)false);
                OptionsManager.getInstance().packetEnabled = false;
            }
            this.checkStack10 = new ItemStack(OptionsManager.getInstance().isPacketEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            final ItemMeta itemMeta5 = this.checkStack10.getItemMeta();
            itemMeta5.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Packet-Limiter")));
            itemMeta5.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to fix exploit / crasher.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isPacketEnabled())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
            this.checkStack10.setItemMeta(itemMeta5);
            this.inventory.setItem(5, this.checkStack10);
            OptionsManager.saveConfig();
        }
        if (clickData.getSlot() == 6) {
            if (!OptionsManager.getInstance().isChatEnabled()) {
                OptionsManager.getInstance().getConfiguration().set("chat-fixer.enabled", (Object)true);
                OptionsManager.getInstance().chatEnabled = true;
            }
            else {
                OptionsManager.getInstance().getConfiguration().set("chat-fixer.enabled", (Object)false);
                OptionsManager.getInstance().chatEnabled = false;
            }
            this.checkStack11 = new ItemStack(OptionsManager.getInstance().isChatEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            final ItemMeta itemMeta6 = this.checkStack11.getItemMeta();
            itemMeta6.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Chat-Fixer")));
            itemMeta6.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to avoid spam.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isChatEnabled())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
            this.checkStack11.setItemMeta(itemMeta6);
            this.inventory.setItem(6, this.checkStack11);
            OptionsManager.saveConfig();
        }
        if (clickData.getSlot() == 7) {
            if (!OptionsManager.getInstance().isRollback()) {
                OptionsManager.getInstance().getConfiguration().set("rollback.enabled", (Object)true);
                OptionsManager.getInstance().rollback = true;
            }
            else {
                OptionsManager.getInstance().getConfiguration().set("rollback.enabled", (Object)false);
                OptionsManager.getInstance().rollback = false;
            }
            this.checkStack12 = new ItemStack(OptionsManager.getInstance().isRollback() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            final ItemMeta itemMeta7 = this.checkStack12.getItemMeta();
            itemMeta7.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Rollback")));
            itemMeta7.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to rollback player when he's flagging.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isRollback())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
            this.checkStack12.setItemMeta(itemMeta7);
            this.inventory.setItem(7, this.checkStack12);
            OptionsManager.saveConfig();
        }
        if (clickData.getSlot() == 8) {
            if (!OptionsManager.getInstance().isAlertInformation()) {
                OptionsManager.getInstance().getConfiguration().set("alerts.information", (Object)true);
                OptionsManager.getInstance().alertInformation = true;
            }
            else {
                OptionsManager.getInstance().getConfiguration().set("alerts.information", (Object)false);
                OptionsManager.getInstance().alertInformation = false;
            }
            this.checkStack13 = new ItemStack(OptionsManager.getInstance().isAlertInformation() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
            final ItemMeta itemMeta8 = this.checkStack13.getItemMeta();
            itemMeta8.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Alert Information")));
            itemMeta8.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate to have information when player is flagging.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isAlertInformation())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
            this.checkStack13.setItemMeta(itemMeta8);
            this.inventory.setItem(8, this.checkStack13);
            OptionsManager.saveConfig();
        }
        if (clickData.getSlot() == 10) {
            clickData.getPlayer().closeInventory();
            this.editMessage(clickData.getPlayer(), "Logs Cmd", OptionsManager.getInstance().getPlayerLogs(), true, "{player}");
            this.removeList(clickData.getPlayer());
            Data.listLogCmd().add(clickData.getPlayer());
        }
        if (clickData.getSlot() == 11) {
            clickData.getPlayer().closeInventory();
            this.editMessage(clickData.getPlayer(), "Seperated Logs", OptionsManager.getInstance().getLogsSepearted(), false, "%s");
            this.removeList(clickData.getPlayer());
            Data.listLogSMCmd().add(clickData.getPlayer());
        }
        if (clickData.getSlot() == 12) {
            clickData.getPlayer().closeInventory();
            this.editMessage(clickData.getPlayer(), "Certainty Alerts", OptionsManager.getInstance().getAlertCertainty(), false, "%s");
            this.removeList(clickData.getPlayer());
            Data.listAlertc().add(clickData.getPlayer());
        }
        if (clickData.getSlot() == 13) {
            clickData.getPlayer().closeInventory();
            this.editMessage(clickData.getPlayer(), "Experimental Alerts", OptionsManager.getInstance().getAlertExp(), false, "%s");
            this.removeList(clickData.getPlayer());
            Data.listAlerte().add(clickData.getPlayer());
        }
        if (clickData.getSlot() == 14) {
            clickData.getPlayer().closeInventory();
            this.editMessage(clickData.getPlayer(), "Auto-Ban Broadcast", OptionsManager.getInstance().getBanMessage(), true, "%s");
            this.removeList(clickData.getPlayer());
            Data.listBan().add(clickData.getPlayer());
        }
    }
    
    public Settings() {
        super(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Settings")), 27);
        final ItemStack itemStack = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Back")));
        itemMeta.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to go back."))));
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(26, itemStack);
        final ItemStack itemStack2 = new ItemStack(Material.STAINED_GLASS_PANE);
        final ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Boolean Settings")));
        itemStack2.setItemMeta(itemMeta2);
        this.inventory.setItem(0, itemStack2);
        this.checkStack = new ItemStack(OptionsManager.getInstance().getAlertNoSpam() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        final ItemMeta itemMeta3 = this.checkStack.getItemMeta();
        itemMeta3.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("No-Spam Alert")));
        itemMeta3.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to avoid spam alert.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().getAlertNoSpam())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        this.checkStack.setItemMeta(itemMeta3);
        this.inventory.setItem(1, this.checkStack);
        this.checkStack2 = new ItemStack(OptionsManager.getInstance().isAutoBan() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        final ItemMeta itemMeta4 = this.checkStack2.getItemMeta();
        itemMeta4.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Auto-Ban")));
        itemMeta4.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to ban a player when he's cheating.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isAutoBan())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        this.checkStack2.setItemMeta(itemMeta4);
        this.inventory.setItem(2, this.checkStack2);
        this.checkStack3 = new ItemStack(OptionsManager.getInstance().isBanAnnouncement() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        final ItemMeta itemMeta5 = this.checkStack3.getItemMeta();
        itemMeta5.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Auto-Ban Broadcast")));
        itemMeta5.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to send a broadcast for the auto-ban.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isBanAnnouncement())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        this.checkStack3.setItemMeta(itemMeta5);
        this.inventory.setItem(3, this.checkStack3);
        this.checkStack4 = new ItemStack(OptionsManager.getInstance().isBypassEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        final ItemMeta itemMeta6 = this.checkStack4.getItemMeta();
        itemMeta6.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Auto-Ban Bypass")));
        itemMeta6.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to bypass auto-ban with a permission.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isBypassEnabled())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        this.checkStack4.setItemMeta(itemMeta6);
        this.inventory.setItem(4, this.checkStack4);
        this.checkStack10 = new ItemStack(OptionsManager.getInstance().isPacketEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        final ItemMeta itemMeta7 = this.checkStack10.getItemMeta();
        itemMeta7.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Packet-Limiter")));
        itemMeta7.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to fix exploit / crasher.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isPacketEnabled())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        this.checkStack10.setItemMeta(itemMeta7);
        this.inventory.setItem(5, this.checkStack10);
        this.checkStack11 = new ItemStack(OptionsManager.getInstance().isChatEnabled() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        final ItemMeta itemMeta8 = this.checkStack11.getItemMeta();
        itemMeta8.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Chat-Fixer")));
        itemMeta8.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to avoid spam.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isChatEnabled())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        this.checkStack11.setItemMeta(itemMeta8);
        this.inventory.setItem(6, this.checkStack11);
        this.checkStack12 = new ItemStack(OptionsManager.getInstance().isRollback() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        final ItemMeta itemMeta9 = this.checkStack12.getItemMeta();
        itemMeta9.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Rollback")));
        itemMeta9.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate this to rollback player when he's flagging.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isRollback())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        this.checkStack12.setItemMeta(itemMeta9);
        this.inventory.setItem(7, this.checkStack12);
        this.checkStack13 = new ItemStack(OptionsManager.getInstance().isAlertInformation() ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        final ItemMeta itemMeta10 = this.checkStack13.getItemMeta();
        itemMeta10.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Alert Information")));
        itemMeta10.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Activate to have information when player is flagging.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(OptionsManager.getInstance().isAlertInformation())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        this.checkStack13.setItemMeta(itemMeta10);
        this.inventory.setItem(8, this.checkStack13);
        final ItemStack itemStack3 = new ItemStack(Material.STAINED_GLASS_PANE);
        final ItemMeta itemMeta11 = itemStack3.getItemMeta();
        itemMeta11.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Message Settings")));
        itemStack3.setItemMeta(itemMeta11);
        this.inventory.setItem(9, itemStack3);
        final ItemStack itemStack4 = new ItemStack(Material.NETHER_STAR);
        final ItemMeta itemMeta12 = itemStack4.getItemMeta();
        itemMeta12.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Logs Cmd")));
        itemMeta12.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Message when /logs.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Message")), String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(OptionsManager.getInstance().getPlayerLogs())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        itemStack4.setItemMeta(itemMeta12);
        this.inventory.setItem(10, itemStack4);
        final ItemStack itemStack5 = new ItemStack(Material.NETHER_STAR);
        final ItemMeta itemMeta13 = itemStack5.getItemMeta();
        itemMeta13.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Separated Logs")));
        itemMeta13.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Separation when /logs.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Message")), String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(OptionsManager.getInstance().getLogsSepearted())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        itemStack5.setItemMeta(itemMeta13);
        this.inventory.setItem(11, itemStack5);
        final ItemStack itemStack6 = new ItemStack(Material.NETHER_STAR);
        final ItemMeta itemMeta14 = itemStack6.getItemMeta();
        itemMeta14.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Certainty Alerts")));
        itemMeta14.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Message when alerts is sure.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Message")), String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(OptionsManager.getInstance().getAlertCertainty())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        itemStack6.setItemMeta(itemMeta14);
        this.inventory.setItem(12, itemStack6);
        final ItemStack itemStack7 = new ItemStack(Material.NETHER_STAR);
        final ItemMeta itemMeta15 = itemStack7.getItemMeta();
        itemMeta15.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Experimental Alerts")));
        itemMeta15.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Message when alerts is not sure.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Message")), String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(OptionsManager.getInstance().getAlertExp())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        itemStack7.setItemMeta(itemMeta15);
        this.inventory.setItem(13, itemStack7);
        final ItemStack itemStack8 = new ItemStack(Material.NETHER_STAR);
        final ItemMeta itemMeta16 = itemStack8.getItemMeta();
        itemMeta16.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Auto-Ban Broadcast")));
        itemMeta16.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Message when player got banned.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Message")), String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(OptionsManager.getInstance().getBanMessage())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to change."))));
        itemStack8.setItemMeta(itemMeta16);
        this.inventory.setItem(14, itemStack8);
    }
    
    private void removeList(final Player player) {
        if (Data.isLogCmd(player)) {
            Data.listLogCmd().remove(player);
        }
        if (Data.isLogSMCmd(player)) {
            Data.listLogSMCmd().remove(player);
        }
        if (Data.isAlertc(player)) {
            Data.listAlertc().remove(player);
        }
        if (Data.isAlerte(player)) {
            Data.listAlerte().remove(player);
        }
        if (Data.isBanBroadcast(player)) {
            Data.listBan().remove(player);
        }
    }
}
