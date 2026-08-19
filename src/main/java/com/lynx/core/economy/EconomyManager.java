package com.lynx.core.economy;

import com.lynx.core.profiles.*; import org.bukkit.entity.Player;
public final class EconomyManager { private static EconomyManager instance; private final ProfileManager profiles; public EconomyManager(ProfileManager profiles){ instance=this; this.profiles=profiles; } public static EconomyManager getInstance(){ return instance; } public double balance(Player p){ Profile pr=profiles.get(p.getUniqueId()); return pr==null?0D:pr.getBalance(); } public boolean withdraw(Player p,double a){ Profile pr=profiles.get(p.getUniqueId()); return pr!=null&&pr.withdraw(a); } public void deposit(Player p,double a){ Profile pr=profiles.get(p.getUniqueId()); if(pr!=null) pr.deposit(a); } }
