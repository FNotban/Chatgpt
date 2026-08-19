package com.lynx.core.items;

import org.bukkit.Location; import org.bukkit.Material; import org.bukkit.entity.*; import org.bukkit.event.*; import org.bukkit.event.entity.EntityDamageByEntityEvent; import org.bukkit.event.player.*; import org.bukkit.inventory.ItemStack; import org.bukkit.util.Vector; import java.util.*; import java.util.concurrent.ConcurrentHashMap;

public final class PartnerItemListener implements Listener { private final Map<UUID,Location> lastHit=new ConcurrentHashMap<UUID,Location>();
 @EventHandler(ignoreCancelled=true) public void onDamage(EntityDamageByEntityEvent e){ if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) lastHit.put(((Player)e.getEntity()).getUniqueId(), ((Player)e.getDamager()).getLocation().clone()); }
 @EventHandler(ignoreCancelled=true) public void onFish(PlayerFishEvent e){ ItemStack h=e.getPlayer().getItemInHand(); if(h!=null&&NbtUtil.hasTag(h,"GrapplingHook")&&e.getHook()!=null){ Vector v=e.getHook().getLocation().toVector().subtract(e.getPlayer().getLocation().toVector()).normalize().multiply(1.7D).setY(0.9D); e.getPlayer().setVelocity(v); } }
 @EventHandler(ignoreCancelled=true) public void onUse(PlayerInteractEvent e){ if(e.getItem()==null) return; Player p=e.getPlayer(); if(NbtUtil.hasTag(e.getItem(),"NinjaStar")){ Location l=lastHit.get(p.getUniqueId()); if(l!=null){ Location behind=l.clone().add(l.getDirection().normalize().multiply(-1.5D)); p.teleport(behind); p.sendMessage("§6Ninja Star activated."); } } }
}
