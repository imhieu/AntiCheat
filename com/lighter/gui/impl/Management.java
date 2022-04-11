package com.lighter.gui.impl;

import com.lighter.check.impl.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.inventory.meta.*;
import com.lighter.gui.*;
import com.lighter.data.manager.*;

public class Management extends Gui
{
    public Management() {
        super(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Choose a category")), 18);
        final ArrayList<Check.CheckType> list = new ArrayList<Check.CheckType>();
        final ArrayList<Check.CheckType> list2 = new ArrayList<Check.CheckType>();
        final ArrayList<Check.CheckType> list3 = new ArrayList<Check.CheckType>();
        for (final Check.CheckType checkType : Check.CheckType.values()) {
            if (checkType.getSuffix().contains("Combat")) {
                list.add(checkType);
            }
            if (checkType.getSuffix().contains("Mouvement")) {
                list2.add(checkType);
            }
            if (checkType.getSuffix().contains("Other")) {
                list3.add(checkType);
            }
        }
        final ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Combat")));
        itemMeta.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Checks").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(list.size())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to open the category."))));
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(2, itemStack);
        final ItemStack itemStack2 = new ItemStack(Material.DIAMOND_BOOTS);
        final ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Mouvement")));
        itemMeta2.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Checks").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(list2.size())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to open the category."))));
        itemStack2.setItemMeta(itemMeta2);
        this.inventory.setItem(4, itemStack2);
        final ItemStack itemStack3 = new ItemStack(Material.REDSTONE);
        final ItemMeta itemMeta3 = itemStack3.getItemMeta();
        itemMeta3.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Other")));
        itemMeta3.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Checks").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(list3.size())), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to open the category."))));
        itemStack3.setItemMeta(itemMeta3);
        this.inventory.setItem(6, itemStack3);
        final ItemStack itemStack4 = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta4 = itemStack4.getItemMeta();
        itemMeta4.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Back")));
        itemMeta4.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to go back."))));
        itemStack4.setItemMeta(itemMeta4);
        this.inventory.setItem(17, itemStack4);
    }
    
    @Override
    public void onClick(final ClickData clickData) {
        if (clickData.getSlot() == 2) {
            clickData.getPlayer().closeInventory();
            GuiManager.getInstance().getCheckGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 4) {
            clickData.getPlayer().closeInventory();
            GuiManager.getInstance().getMovGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 6) {
            clickData.getPlayer().closeInventory();
            GuiManager.getInstance().getOtherGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 17) {
            clickData.getPlayer().closeInventory();
            GuiManager.getInstance().getacGui().openGui(clickData.getPlayer());
        }
    }
}
