package com.lynx.core.timers;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class TimerManager {
    private static TimerManager instance;
    private final Plugin plugin;
    private final Map<String, Timer> timers = new ConcurrentHashMap<String, Timer>();
    private BukkitTask task;
    public TimerManager(Plugin plugin) { this.plugin = plugin; instance = this; register(new SimpleTimer("Combat")); register(new SimpleTimer("Enderpearl")); register(new SimpleTimer("PvPTimer")); }
    public static TimerManager getInstance() { return instance; }
    public void register(Timer timer) { timers.put(timer.getName().toLowerCase(Locale.ENGLISH), timer); }
    public Timer get(String name) { return timers.get(name.toLowerCase(Locale.ENGLISH)); }
    public Collection<Timer> getTimers() { return Collections.unmodifiableCollection(timers.values()); }
    public void start() { if (task == null) task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable(){ public void run(){ for (Timer t : timers.values()) t.tick(); }}, 20L, 20L); }
    public void shutdown() { if (task != null) task.cancel(); timers.clear(); }
    private static final class SimpleTimer extends Timer { private SimpleTimer(String name) { super(name); } }
}
