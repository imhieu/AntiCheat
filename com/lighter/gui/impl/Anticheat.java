package com.lighter.gui.impl;

import com.lighter.gui.*;
import com.lighter.main.*;
import com.lighter.data.manager.*;
import com.lighter.check.impl.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.inventory.meta.*;

public class Anticheat extends Gui
{
    @Override
    public void onClick(final ClickData clickData) {
        if (clickData.getSlot() == 4 && clickData.getClickType().isRightClick()) {
            clickData.getPlayer().closeInventory();
            clickData.getPlayer().performCommand("anticheat");
        }
        if (clickData.getSlot() == 4 && clickData.getClickType().isLeftClick()) {
            clickData.getPlayer().closeInventory();
            clickData.getPlayer().sendMessage(String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Lighter discrord: ").append(ChatColor.RED).append(Main.getPlugin().Discord)));
        }
        if (clickData.getSlot() == 12) {
            clickData.getPlayer().closeInventory();
            GuiManager.getInstance().getMainGui().openGui(clickData.getPlayer());
        }
        if (clickData.getSlot() == 14) {
            clickData.getPlayer().closeInventory();
            GuiManager.getInstance().getSettingGui().openGui(clickData.getPlayer());
        }
    }
    
    public Anticheat() {
        super(String.valueOf(new StringBuilder().append(ChatColor.RED).append("Lighter Anticheat")), 18);
        final ArrayList list = new ArrayList((Collection<? extends E>)Arrays.asList(Check.CheckType.values()));
        final ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Lighter Info")));
        itemMeta.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.RED).append("Version: ").append(ChatColor.WHITE).append(Main.getPlugin().Version)), String.valueOf(new StringBuilder().append(ChatColor.RED).append("Discord: ").append(ChatColor.WHITE).append(Main.getPlugin().Discord)), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append(ChatColor.ITALIC.toString()).append("Lighter have ").append(ChatColor.WHITE).append(ChatColor.ITALIC).append(list.size()).append(ChatColor.GRAY).append(ChatColor.ITALIC).append(" checks.")), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Left-click to join Lighter discord.")), String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Right-click to see Lighter commands."))));
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(4, itemStack);
        final ItemStack itemStack2 = new ItemStack(Material.BOOK);
        final ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Manage check")));
        itemMeta2.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to manage all check."))));
        itemStack2.setItemMeta(itemMeta2);
        this.inventory.setItem(12, itemStack2);
        final ItemStack itemStack3 = new ItemStack(Material.REDSTONE_COMPARATOR);
        final ItemMeta itemMeta3 = itemStack3.getItemMeta();
        itemMeta3.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Fast Settings")));
        itemMeta3.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to configure Lighter."))));
        itemStack3.setItemMeta(itemMeta3);
        this.inventory.setItem(14, itemStack3);
    }
}
