package com.lynx.core.items;

import org.bukkit.inventory.ItemStack;

public final class NbtUtil {
    private NbtUtil() { }
    public static boolean hasLynxBypass(ItemStack item) { return item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("§0LynxBypass:true"); }
    public static boolean hasTag(ItemStack item, String tag) { return item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().contains("§0LynxTag:" + tag); }
}
