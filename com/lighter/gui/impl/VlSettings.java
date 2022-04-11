package com.lighter.gui.impl;

import org.bukkit.entity.*;
import com.lighter.gui.*;
import com.lighter.check.impl.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.scheduler.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.event.inventory.*;
import com.lighter.data.manager.*;

public class VlSettings implements Listener
{
    private static final Map<Player, ItemStack> lastItemStack;
    private static final Map<Player, Gui> lastGui;
    private static final Map<Player, Integer> lastSlot;
    private static final Map<Player, Check.CheckType> check;
    
    private void updateItem(final Player player, final ItemStack itemStack, final ItemMeta itemMeta, final Integer n, final Check.CheckType checkType) {
        new BukkitRunnable(this, itemMeta, checkType, itemStack, player, n) {
            final Integer val$slot;
            final Player val$p;
            final ItemMeta val$checkMeta;
            final Check.CheckType val$type;
            final VlSettings this$0;
            final ItemStack val$checkStack;
            
            public void run() {
                this.val$checkMeta.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GREEN).append("This check is safe.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(CheckManager.getInstance().enabled(this.val$type))), String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Max-VL").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(VlManager.getInstance().vl_to_ban(this.val$type))), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Left-click to enable / disable.")), String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Right-click to change Max-VL."))));
                this.val$checkStack.setItemMeta(this.val$checkMeta);
                this.val$p.getOpenInventory().setItem((int)this.val$slot, this.val$checkStack);
            }
        }.runTaskLater((Plugin)Main.getPlugin(), 2L);
    }
    
    @EventHandler
    private void onClick(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getWhoClicked() instanceof Player) {
            final Player player = (Player)inventoryClickEvent.getWhoClicked();
            if (VlSettings.check.containsKey(player) && inventoryClickEvent.getInventory().getName().equalsIgnoreCase(VlSettings.check.get(player).getName())) {
                inventoryClickEvent.setCancelled(true);
                if (inventoryClickEvent.getWhoClicked() instanceof Player && inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getType() != null && inventoryClickEvent.getCurrentItem().getType() != Material.AIR && inventoryClickEvent.getCurrentItem().getItemMeta() != null && inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() != null) {
                    final Check.CheckType checkType = VlSettings.check.get(player);
                    if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals(String.valueOf(new StringBuilder().append(ChatColor.RED).append("- 1")))) {
                        if (VlManager.getInstance().vl_to_ban(checkType) == 0) {
                            player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("You can't set negative Max-VL.")));
                            return;
                        }
                        VlManager.getInstance().removeVl(checkType, player, VlManager.getInstance().vl_to_ban(checkType) - 1);
                        final ItemStack itemStack = new ItemStack(Material.BOOK);
                        final ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED.toString()).append(ChatColor.BOLD.toString()).append(checkType.getName())));
                        itemMeta.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.RED).append("Max-Vl")), String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(String.valueOf(VlManager.getInstance().vl_to_ban(checkType))))));
                        itemStack.setItemMeta(itemMeta);
                        player.getOpenInventory().setItem(4, itemStack);
                        player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Removed 1 to ").append(checkType.getName()).append(" Max-VL.")));
                    }
                    if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals(String.valueOf(new StringBuilder().append(ChatColor.GREEN).append("+ 1")))) {
                        VlManager.getInstance().addVl(checkType, player, VlManager.getInstance().vl_to_ban(checkType) + 1);
                        final ItemStack itemStack2 = new ItemStack(Material.BOOK);
                        final ItemMeta itemMeta2 = itemStack2.getItemMeta();
                        itemMeta2.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED.toString()).append(ChatColor.BOLD.toString()).append(checkType.getName())));
                        itemMeta2.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.RED).append("Max-Vl")), String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(String.valueOf(VlManager.getInstance().vl_to_ban(checkType))))));
                        itemStack2.setItemMeta(itemMeta2);
                        player.getOpenInventory().setItem(4, itemStack2);
                        player.sendMessage(String.valueOf(new StringBuilder().append(ChatColor.GREEN).append("Added 1 to ").append(checkType.getName()).append(" Max-VL.")));
                    }
                    if (inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName().equals(String.valueOf(new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD.toString()).append("Back")))) {
                        player.closeInventory();
                    }
                }
            }
        }
    }
    
    static Map access$000() {
        return VlSettings.lastGui;
    }
    
    public static void vlGui(final Player player, final Check.CheckType checkType, final Gui gui, final Integer n, final ItemStack itemStack) {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 18, String.valueOf(checkType.getName()));
        VlSettings.check.put(player, checkType);
        VlSettings.lastGui.put(player, gui);
        VlSettings.lastSlot.put(player, n);
        VlSettings.lastItemStack.put(player, itemStack);
        final ItemStack itemStack2 = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta = itemStack2.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Back")));
        itemMeta.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to go back."))));
        itemStack2.setItemMeta(itemMeta);
        inventory.setItem(17, itemStack2);
        final ItemStack itemStack3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        final ItemMeta itemMeta2 = itemStack3.getItemMeta();
        itemMeta2.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED).append("- 1")));
        itemMeta2.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to remove 1 to Max-VL."))));
        itemStack3.setItemMeta(itemMeta2);
        inventory.setItem(2, itemStack3);
        final ItemStack itemStack4 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta3 = itemStack4.getItemMeta();
        itemMeta3.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED.toString()).append(ChatColor.BOLD.toString()).append(checkType.getName())));
        itemMeta3.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.RED).append("Max-VL")), String.valueOf(new StringBuilder().append(ChatColor.WHITE).append(String.valueOf(VlManager.getInstance().vl_to_ban(checkType))))));
        itemStack4.setItemMeta(itemMeta3);
        inventory.setItem(4, itemStack4);
        final ItemStack itemStack5 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
        final ItemMeta itemMeta4 = itemStack5.getItemMeta();
        itemMeta4.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.GREEN).append("+ 1")));
        itemMeta4.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to add 1 to Max-VL."))));
        itemStack5.setItemMeta(itemMeta4);
        inventory.setItem(6, itemStack5);
        player.openInventory(inventory);
    }
    
    static {
        check = new HashMap<Player, Check.CheckType>();
        lastGui = new HashMap<Player, Gui>();
        lastSlot = new HashMap<Player, Integer>();
        lastItemStack = new HashMap<Player, ItemStack>();
    }
    
    static Map access$100() {
        return VlSettings.check;
    }
    
    @EventHandler
    private void onCloseInventory(final InventoryCloseEvent inventoryCloseEvent) {
        final Player player = (Player)inventoryCloseEvent.getPlayer();
        if (VlSettings.check.containsKey(player)) {
            final Check.CheckType checkType = VlSettings.check.get(player);
            CheckManager.getInstance().reloadCheck(checkType);
            new BukkitRunnable(this, player) {
                final Player val$p;
                final VlSettings this$0;
                
                public void run() {
                    GuiManager.getInstance().getGui(VlSettings.access$000().get(this.val$p)).openGui(this.val$p);
                }
            }.runTaskLater((Plugin)Main.getPlugin(), 1L);
            this.updateItem(player, VlSettings.lastItemStack.get(player), VlSettings.lastItemStack.get(player).getItemMeta(), VlSettings.lastSlot.get(player), checkType);
            this.clearMap(player);
        }
    }
    
    private void clearMap(final Player player) {
        new BukkitRunnable(this, player) {
            final VlSettings this$0;
            final Player val$p;
            
            public void run() {
                VlSettings.access$100().remove(this.val$p);
                VlSettings.access$000().remove(this.val$p);
                VlSettings.access$200().remove(this.val$p);
                VlSettings.access$300().remove(this.val$p);
            }
        }.runTaskLaterAsynchronously((Plugin)Main.getPlugin(), 3L);
    }
    
    static Map access$200() {
        return VlSettings.lastSlot;
    }
    
    static Map access$300() {
        return VlSettings.lastItemStack;
    }
}
