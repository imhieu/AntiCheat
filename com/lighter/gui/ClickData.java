package com.lighter.gui;

import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.*;

public class ClickData
{
    private final ItemStack itemStack;
    private final Inventory inventory;
    private final Player player;
    private final Integer slot;
    private final ClickType clickType;
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public Integer getSlot() {
        return this.slot;
    }
    
    public ClickType getClickType() {
        return this.clickType;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public ClickData(final Player player, final Inventory inventory, final ItemStack itemStack, final ClickType clickType, final Integer slot) {
        this.player = player;
        this.inventory = inventory;
        this.itemStack = itemStack;
        this.clickType = clickType;
        this.slot = slot;
    }
}
