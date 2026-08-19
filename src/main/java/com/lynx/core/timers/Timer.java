package com.lynx.core.timers;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class Timer {
    private final String name;
    private final ConcurrentMap<UUID, Long> expirations = new ConcurrentHashMap<UUID, Long>();
    protected Timer(String name) { this.name = name; }
    public String getName() { return name; }
    public void activate(UUID uuid, long millis) { expirations.put(uuid, System.currentTimeMillis() + millis); }
    public void clear(UUID uuid) { expirations.remove(uuid); }
    public boolean isActive(UUID uuid) { return getRemaining(uuid) > 0L; }
    public long getRemaining(UUID uuid) {
        Long until = expirations.get(uuid);
        if (until == null) return 0L;
        long remaining = until - System.currentTimeMillis();
        if (remaining <= 0L) { expirations.remove(uuid, until); return 0L; }
        return remaining;
    }
    public void tick() { for (UUID uuid : expirations.keySet()) if (!isActive(uuid)) onExpire(uuid); }
    protected void onExpire(UUID uuid) { }
}
