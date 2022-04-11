package com.lighter.gui.check.combat;

import com.lighter.check.impl.*;
import com.lighter.gui.*;
import org.bukkit.*;
import com.lighter.data.manager.*;
import java.util.*;
import com.lighter.gui.impl.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import com.google.common.collect.*;

public class HitBoxes extends Gui
{
    private final Map<Integer, Check.CheckType> checksById;
    
    @Override
    public void onClick(final ClickData clickData) {
        final Integer slot = clickData.getSlot();
        if (slot <= this.getInventory().getSize() && slot >= 0) {
            final ItemStack itemStack = clickData.getItemStack();
            final ItemMeta itemMeta = itemStack.getItemMeta();
            final Check.CheckType checkType = this.checksById.get(slot);
            if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Back")))) {
                clickData.getPlayer().closeInventory();
                GuiManager.getInstance().getCheckGui().openGui(clickData.getPlayer());
            }
            else if (clickData.getClickType().isLeftClick() && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(String.valueOf(new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD.toString()).append(checkType.getName())))) {
                final boolean enabled = CheckManager.getInstance().enabled(checkType);
                itemStack.setType(enabled ? Material.REDSTONE_BLOCK : Material.EMERALD_BLOCK);
                if (enabled) {
                    CheckManager.getInstance().disableType(checkType, clickData.getPlayer());
                }
                else {
                    CheckManager.getInstance().enableType(checkType, clickData.getPlayer());
                }
                itemMeta.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GREEN).append("This check is safe.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(CheckManager.getInstance().enabled(checkType))), String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Max-VL").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(VlManager.getInstance().vl_to_ban(checkType))), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Left-click to enable / disable.")), String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Right-click to change Max-VL."))));
                itemStack.setItemMeta(itemMeta);
                this.inventory.setItem((int)slot, itemStack);
            }
            else if (clickData.getClickType().isRightClick() && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(String.valueOf(new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD.toString()).append(checkType.getName())))) {
                clickData.getPlayer().closeInventory();
                VlSettings.vlGui(clickData.getPlayer(), checkType, this, slot, itemStack);
            }
        }
    }
    
    public HitBoxes() {
        super(String.valueOf(new StringBuilder().append(ChatColor.RED).append("HitBoxes")), 18);
        this.checksById = (Map<Integer, Check.CheckType>)Maps.newConcurrentMap();
        int n = 0;
        final ItemStack itemStack = new ItemStack(Material.ARROW);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append("Back")));
        itemMeta.setLore((List)Arrays.asList("", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Click to go back."))));
        itemStack.setItemMeta(itemMeta);
        this.inventory.setItem(17, itemStack);
        for (final Check.CheckType checkType : Check.CheckType.values()) {
            if (checkType.getName().contains("HitBoxes")) {
                final ItemStack itemStack2 = new ItemStack(CheckManager.getInstance().enabled(checkType) ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
                final ItemMeta itemMeta2 = itemStack2.getItemMeta();
                itemMeta2.setDisplayName(String.valueOf(new StringBuilder().append(ChatColor.RED.toString()).append(ChatColor.BOLD.toString()).append(checkType.getName())));
                itemMeta2.setLore((List)Arrays.asList(String.valueOf(new StringBuilder().append(ChatColor.GREEN).append("This check is safe.")), "", String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Enabled").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(CheckManager.getInstance().enabled(checkType))), String.valueOf(new StringBuilder().append(ChatColor.DARK_RED).append("Max-VL").append(ChatColor.GRAY).append(" : ").append(ChatColor.WHITE).append(VlManager.getInstance().vl_to_ban(checkType))), "", String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Left-click to enable / disable.")), String.valueOf(new StringBuilder().append(ChatColor.GRAY).append("Right-click to change Max-VL."))));
                itemStack2.setItemMeta(itemMeta2);
                this.inventory.setItem(n, itemStack2);
                this.checksById.put(n, checkType);
                ++n;
            }
        }
    }
}
