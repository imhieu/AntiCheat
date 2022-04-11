package com.lighter.gui.impl;

import com.lighter.gui.*;
import com.lighter.data.manager.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.inventory.meta.*;

public class Other extends Gui
{
    @Override
    public void onClick(final ClickData clickData) {
        final Integer slot = clickData.getSlot();
        if (slot <= this.getInventory().getSize() && slot >= 0) {
            if (clickData.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Back")))) {
                clickData.getPlayer().closeInventory();
                GuiManager.getInstance().getMainGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 0) {
                GuiManager.getInstance().getClientGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 1) {
                GuiManager.getInstance().getBreakGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 2) {
                GuiManager.getInstance().getPosGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 3) {
                GuiManager.getInstance().getPingSpoofGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 4) {
                GuiManager.getInstance().getRegenGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 5) {
                GuiManager.getInstance().getCraserGui().openGui(clickData.getPlayer());
            }
            if (clickData.getSlot() == 6) {
                GuiManager.getInstance().getTimerGui().openGui(clickData.getPlayer());
            }
        }
    }
    
    public Other() {
        super(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Other")), 18);
        final ItemStack itemStack = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Back")));
        itemMeta.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to go back."))));
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(17, itemStack);
        final ItemStack itemStack2 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Client")));
        itemMeta2.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to configure."))));
        itemStack2.setItemMeta(itemMeta2);
        this.inventory.setItem(0, itemStack2);
        final ItemStack itemStack3 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta3 = itemStack3.getItemMeta();
        itemMeta3.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("FastBreak")));
        itemMeta3.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to configure."))));
        itemStack3.setItemMeta(itemMeta3);
        this.inventory.setItem(1, itemStack3);
        final ItemStack itemStack4 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta4 = itemStack4.getItemMeta();
        itemMeta4.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("InvalidPos")));
        itemMeta4.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to configure."))));
        itemStack4.setItemMeta(itemMeta4);
        this.inventory.setItem(2, itemStack4);
        final ItemStack itemStack5 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta5 = itemStack5.getItemMeta();
        itemMeta5.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("PingSpoof")));
        itemMeta5.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to configure."))));
        itemStack5.setItemMeta(itemMeta5);
        this.inventory.setItem(3, itemStack5);
        final ItemStack itemStack6 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta6 = itemStack6.getItemMeta();
        itemMeta6.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Regen")));
        itemMeta6.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to configure."))));
        itemStack6.setItemMeta(itemMeta6);
        this.inventory.setItem(4, itemStack6);
        final ItemStack itemStack7 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta7 = itemStack7.getItemMeta();
        itemMeta7.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("ServerCrasher")));
        itemMeta7.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to configure."))));
        itemStack7.setItemMeta(itemMeta7);
        this.inventory.setItem(5, itemStack7);
        final ItemStack itemStack8 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta8 = itemStack8.getItemMeta();
        itemMeta8.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Timer")));
        itemMeta8.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to configure."))));
        itemStack8.setItemMeta(itemMeta8);
        this.inventory.setItem(6, itemStack8);
    }
}
