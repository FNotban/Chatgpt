package com.lynx.core.listeners;

import com.lynx.core.economy.EconomyManager; import org.bukkit.Material; import org.bukkit.block.Sign; import org.bukkit.entity.Player; import org.bukkit.event.*; import org.bukkit.event.block.SignChangeEvent; import org.bukkit.event.player.PlayerInteractEvent; import org.bukkit.inventory.ItemStack;

public final class EconomySignListener implements Listener {
    private final EconomyManager economy; public EconomySignListener(EconomyManager economy){ this.economy=economy; }
    @EventHandler public void onSign(SignChangeEvent e){ String top=e.getLine(0); if(top.equalsIgnoreCase("[Buy]")||top.equalsIgnoreCase("[Sell]")){ e.setLine(0, top.equalsIgnoreCase("[Buy]")?"§a[Buy]":"§c[Sell]"); e.getPlayer().sendMessage("§aEconomy sign created."); } }
    @EventHandler(ignoreCancelled=true) public void onUse(PlayerInteractEvent e){ if(e.getClickedBlock()==null||!(e.getClickedBlock().getState() instanceof Sign)) return; Sign s=(Sign)e.getClickedBlock().getState(); boolean buy=s.getLine(0).contains("[Buy]"), sell=s.getLine(0).contains("[Sell]"); if(!buy&&!sell) return; Player p=e.getPlayer(); int amount=parse(s.getLine(1),1); Material mat=Material.matchMaterial(s.getLine(2)); double price=parseDouble(s.getLine(3),0); if(mat==null||price<=0) return; if(buy){ if(!economy.withdraw(p,price)){p.sendMessage("§cInsufficient funds.");return;} p.getInventory().addItem(new ItemStack(mat,amount)); p.sendMessage("§aPurchased for $"+price); } else { if(!p.getInventory().contains(mat,amount)){p.sendMessage("§cYou do not have enough items.");return;} p.getInventory().removeItem(new ItemStack(mat,amount)); economy.deposit(p,price); p.sendMessage("§aSold for $"+price); } }
    private int parse(String s,int d){ try{return Integer.parseInt(s.replaceAll("[^0-9]",""));}catch(Exception e){return d;} } private double parseDouble(String s,double d){ try{return Double.parseDouble(s.replace("$","").trim());}catch(Exception e){return d;} }
}
