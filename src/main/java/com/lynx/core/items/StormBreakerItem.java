package com.lynx.core.items;

import com.lynx.core.LynxPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import java.util.Random;

public final class StormBreakerItem implements Listener {
    private static final double PROC_CHANCE = 0.15D;
    private final Random random = new Random();
    @SuppressWarnings("unused") private final LynxPlugin plugin;
    public StormBreakerItem(LynxPlugin plugin) { this.plugin = plugin; }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        Player attacker = (Player) event.getDamager(); Player victim = (Player) event.getEntity();
        ItemStack hand = attacker.getItemInHand();
        if (hand == null || hand.getType() != Material.GOLD_AXE || !NbtUtil.hasTag(hand, "StormBreaker")) return;
        if (random.nextDouble() > PROC_CHANCE) return;
        ItemStack helmet = victim.getInventory().getHelmet();
        if (helmet == null || helmet.getType() == Material.AIR) return;
        victim.getInventory().setHelmet(null);
        Map<Integer, ItemStack> overflow = victim.getInventory().addItem(helmet.clone());
        for (ItemStack item : overflow.values()) victim.getWorld().dropItemNaturally(victim.getLocation(), item);
        victim.updateInventory(); attacker.sendMessage("§6Storm Breaker §eremoved §c" + victim.getName() + "§e's helmet.");
        victim.sendMessage("§cYour helmet was torn off by Storm Breaker!");
    }
}
