package com.lighter.gui;

import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.*;
import com.lighter.main.*;
import org.bukkit.plugin.*;
import org.bukkit.event.inventory.*;

public class Gui implements Listener, InventoryHolder
{
    public Inventory inventory;
    
    public void onClick(final ClickData clickData) {
    }
    
    @EventHandler
    public void onGuiClick(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getType() != null && inventoryClickEvent.getCurrentItem().getType() != Material.AIR && inventoryClickEvent.getCurrentItem().getItemMeta() != null && inventoryClickEvent.getCurrentItem().getItemMeta().getDisplayName() != null) {
            if (inventoryClickEvent.getInventory() != null && inventoryClickEvent.getInventory().getHolder() == this) {
                this.onClick(new ClickData((Player)inventoryClickEvent.getWhoClicked(), inventoryClickEvent.getClickedInventory(), inventoryClickEvent.getCurrentItem(), inventoryClickEvent.getClick(), inventoryClickEvent.getSlot()));
                inventoryClickEvent.setCancelled(true);
            }
            else if (inventoryClickEvent.getView() != null && inventoryClickEvent.getView().getTopInventory() != null && inventoryClickEvent.getView().getTopInventory().getHolder() == this) {
                inventoryClickEvent.setCancelled(true);
            }
        }
        else if (inventoryClickEvent.getView().getTopInventory().getHolder() == this) {
            inventoryClickEvent.setCancelled(true);
        }
    }
    
    public void onClose() {
    }
    
    public void openGui(final Player player) {
        if (this.inventory != null) {
            player.openInventory(this.inventory);
        }
    }
    
    public Gui(final String s, final Integer n) {
        this.inventory = Bukkit.createInventory((InventoryHolder)this, (int)n, s);
        Bukkit.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)Main.getPlugin());
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    @EventHandler
    public void onGuiClose(final InventoryCloseEvent inventoryCloseEvent) {
        this.onClose();
    }
}
