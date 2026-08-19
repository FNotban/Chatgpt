package com.lynx.core.factions;

import com.google.gson.Gson; import com.mongodb.client.MongoCollection; import com.mongodb.client.MongoDatabase; import org.bson.Document; import org.bukkit.Location;
import java.util.*; import java.util.concurrent.ConcurrentHashMap; import static com.mongodb.client.model.Filters.eq;

public final class FactionManager {
    private static FactionManager instance; private final Gson gson; private final MongoCollection<Document> collection; private final Map<UUID, Faction> factions = new ConcurrentHashMap<UUID, Faction>();
    public FactionManager(MongoDatabase db, Gson gson) { instance = this; this.gson = gson; this.collection = db.getCollection("factions"); }
    public static FactionManager getInstance() { return instance; }
    public void register(Faction faction) { factions.put(faction.getUniqueId(), faction); }
    public Faction get(UUID id) { return factions.get(id); }
    public Faction getByName(String name) { for (Faction f : factions.values()) if (f.getName().equalsIgnoreCase(name)) return f; return null; }
    public Faction getAt(Location location) { for (Faction f : factions.values()) if (f.owns(location)) return f; return null; }
    public void save(Faction f) { collection.replaceOne(eq("_id", f.getUniqueId().toString()), f.toDocument(gson.toJson(f)), new com.mongodb.client.model.ReplaceOptions().upsert(true)); }
    public void tickDtrRegen() { for (Faction f : factions.values()) { double before = f.getDtr(); f.regenerateDtrMinute(); if (before != f.getDtr()) save(f); } }
}
