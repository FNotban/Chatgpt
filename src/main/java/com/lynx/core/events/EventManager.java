package com.lynx.core.events;

import org.bukkit.Bukkit; import org.bukkit.plugin.Plugin; import org.bukkit.scheduler.BukkitTask; import java.util.*; import java.util.concurrent.ConcurrentHashMap;

public final class EventManager { private static EventManager instance; private final Map<String,KothEvent> koths=new ConcurrentHashMap<String,KothEvent>(); private final Plugin plugin; private BukkitTask task; public EventManager(Plugin plugin){this.plugin=plugin;instance=this;} public static EventManager getInstance(){return instance;} public void register(KothEvent k){koths.put(k.getName().toLowerCase(Locale.ENGLISH),k);} public void start(){ task=plugin.getServer().getScheduler().runTaskTimer(plugin,new Runnable(){public void run(){ for(KothEvent k:koths.values()){ k.tick(Bukkit.getOnlinePlayers()); if(k.isCaptured()){ Bukkit.broadcastMessage("§6"+k.getCapper().getName()+" captured "+k.getName()+"!"); k.reset(); } } }},20L,20L);} public void shutdown(){if(task!=null)task.cancel();} }
