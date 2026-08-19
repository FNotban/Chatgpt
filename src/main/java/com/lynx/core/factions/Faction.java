package com.lynx.core.factions;

import org.bson.Document;
import org.bukkit.Location;
import java.util.*;

public final class Faction {
    public enum Type { PLAYER, SAFEZONE, WARZONE, KOTH, CITADEL, GLOWSTONE_MOUNTAIN, ORES_MOUNTAIN, ROAD }
    private final UUID uniqueId;
    private String name;
    private Type type;
    private final Set<UUID> members = new HashSet<UUID>();
    private final List<Claim> claims = new ArrayList<Claim>();
    private double dtr, balance;
    private long dtrFreezeUntil;
    private Location home;
    public Faction(UUID uniqueId, String name, Type type) { this.uniqueId = uniqueId; this.name = name; this.type = type; recalculateMaxDtr(); this.dtr = getMaxDtr(); }
    public UUID getUniqueId() { return uniqueId; } public String getName() { return name; } public Type getType() { return type; }
    public Set<UUID> getMembers() { return Collections.unmodifiableSet(members); } public List<Claim> getClaims() { return Collections.unmodifiableList(claims); }
    public double getDtr() { return dtr; } public double getBalance() { return balance; } public Location getHome() { return home; }
    public boolean isPlayerFaction() { return type == Type.PLAYER; } public boolean isRaidable() { return isPlayerFaction() && dtr <= 0.0D; }
    public double getMaxDtr() { return Math.min(5.5D, Math.max(1.1D, members.size() * 1.1D)); }
    public void recalculateMaxDtr() { dtr = Math.min(dtr, getMaxDtr()); }
    public void addMember(UUID uuid) { members.add(uuid); if (dtr <= 0) dtr = Math.min(1.1D, getMaxDtr()); recalculateMaxDtr(); }
    public void applyDeathPenalty() { dtr = Math.max(-0.99D, dtr - 1.0D); dtrFreezeUntil = System.currentTimeMillis() + 45L * 60L * 1000L; }
    public long getDtrFreezeRemaining() { return Math.max(0L, dtrFreezeUntil - System.currentTimeMillis()); }
    public void regenerateDtrMinute() { if (getDtrFreezeRemaining() == 0L && dtr < getMaxDtr()) dtr = Math.min(getMaxDtr(), dtr + 0.1D); }
    public boolean addClaim(Claim claim) { for (Claim existing : claims) if (existing.intersects(claim)) return false; return claims.add(claim); }
    public boolean owns(Location location) { for (Claim c : claims) if (c.contains(location)) return true; return false; }
    public Document toDocument(String json) { return new Document("_id", uniqueId.toString()).append("name", name).append("type", type.name()).append("payload", json); }
}
