package com.lynx.core.profiles;

import org.bson.Document;
import java.util.UUID;

public final class Profile {
    private final UUID uniqueId;
    private String name;
    private int kills, deaths;
    private double balance;
    private long deathbanUntil, pvpTimerUntil;

    public Profile(UUID uniqueId, String name) { this.uniqueId = uniqueId; this.name = name; this.balance = 250.0D; this.pvpTimerUntil = System.currentTimeMillis() + 30L * 60L * 1000L; }
    public UUID getUniqueId() { return uniqueId; } public String getName() { return name; } public int getKills() { return kills; } public int getDeaths() { return deaths; }
    public double getBalance() { return balance; } public long getDeathbanUntil() { return deathbanUntil; } public boolean isDeathbanned() { return deathbanUntil > System.currentTimeMillis(); }
    public long getPvpTimerRemaining() { return Math.max(0L, pvpTimerUntil - System.currentTimeMillis()); }
    public void setName(String name) { this.name = name; } public void addKill() { kills++; } public void addDeath() { deaths++; }
    public boolean withdraw(double amount) { if (amount < 0 || balance < amount) return false; balance -= amount; return true; }
    public void deposit(double amount) { if (amount > 0) balance += amount; }
    public void deathban(long millis) { deathbanUntil = System.currentTimeMillis() + millis; }
    public void disablePvpTimer() { pvpTimerUntil = 0L; }
    public Document toDocument() { return new Document("_id", uniqueId.toString()).append("name", name).append("kills", kills).append("deaths", deaths).append("balance", balance).append("deathbanUntil", deathbanUntil).append("pvpTimerUntil", pvpTimerUntil); }
    public static Profile fromDocument(Document d) { Profile p = new Profile(UUID.fromString(d.getString("_id")), d.getString("name")); p.kills = d.getInteger("kills", 0); p.deaths = d.getInteger("deaths", 0); p.balance = d.getDouble("balance") == null ? 250.0D : d.getDouble("balance"); p.deathbanUntil = d.getLong("deathbanUntil") == null ? 0L : d.getLong("deathbanUntil"); p.pvpTimerUntil = d.getLong("pvpTimerUntil") == null ? 0L : d.getLong("pvpTimerUntil"); return p; }
}
