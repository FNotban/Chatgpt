package com.lynx.core.profiles;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.entity.Player;
import java.util.*; import java.util.concurrent.ConcurrentHashMap;
import static com.mongodb.client.model.Filters.eq;

public final class ProfileManager {
    private static ProfileManager instance; private final MongoCollection<Document> collection; private final Map<UUID, Profile> profiles = new ConcurrentHashMap<UUID, Profile>();
    public ProfileManager(MongoDatabase db) { instance = this; this.collection = db.getCollection("profiles"); }
    public static ProfileManager getInstance() { return instance; }
    public Profile load(Player player) { Document d = collection.find(eq("_id", player.getUniqueId().toString())).first(); Profile p = d == null ? new Profile(player.getUniqueId(), player.getName()) : Profile.fromDocument(d); p.setName(player.getName()); profiles.put(player.getUniqueId(), p); return p; }
    public Profile get(UUID uuid) { return profiles.get(uuid); }
    public void save(UUID uuid) { Profile p = profiles.get(uuid); if (p != null) collection.replaceOne(eq("_id", uuid.toString()), p.toDocument(), new com.mongodb.client.model.ReplaceOptions().upsert(true)); }
    public void unload(UUID uuid) { save(uuid); profiles.remove(uuid); }
    public Collection<Profile> all() { return Collections.unmodifiableCollection(profiles.values()); }
}
