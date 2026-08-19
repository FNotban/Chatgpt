package com.lynx.core.listeners;

import com.lynx.core.items.NbtUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public final class EnchantLimiterListener implements Listener {
    private static final int MAX_PROTECTION = 2, MAX_SHARPNESS = 2, BYPASS_MAX = 3;
    @EventHandler(priority = EventPriority.HIGHEST) public void onEnchant(EnchantItemEvent event) { limit(event.getItem(), NbtUtil.hasLynxBypass(event.getItem())); }
    @EventHandler(priority = EventPriority.HIGHEST) public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == null || event.getInventory().getType() != InventoryType.ANVIL) return;
        limit(event.getCurrentItem(), NbtUtil.hasLynxBypass(event.getCurrentItem())); limit(event.getCursor(), NbtUtil.hasLynxBypass(event.getCursor()));
    }
    private void limit(ItemStack item, boolean bypass) {
        if (item == null) return;
        int max = bypass ? BYPASS_MAX : MAX_PROTECTION;
        for (Map.Entry<Enchantment, Integer> e : item.getEnchantments().entrySet()) {
            Enchantment ench = e.getKey(); int allowed = ench.equals(Enchantment.PROTECTION_ENVIRONMENTAL) ? max : ench.equals(Enchantment.DAMAGE_ALL) ? (bypass ? BYPASS_MAX : MAX_SHARPNESS) : -1;
            if (allowed > -1 && e.getValue() > allowed) item.addUnsafeEnchantment(ench, allowed);
        }
    }
}
