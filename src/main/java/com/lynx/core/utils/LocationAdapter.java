package com.lynx.core.utils;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.lang.reflect.Type;

public final class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
    public JsonElement serialize(Location l, Type type, JsonSerializationContext ctx) {
        JsonObject o = new JsonObject();
        o.addProperty("world", l.getWorld().getName()); o.addProperty("x", l.getX()); o.addProperty("y", l.getY()); o.addProperty("z", l.getZ());
        o.addProperty("yaw", l.getYaw()); o.addProperty("pitch", l.getPitch()); return o;
    }
    public Location deserialize(JsonElement e, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        JsonObject o = e.getAsJsonObject();
        if (Bukkit.getWorld(o.get("world").getAsString()) == null) throw new JsonParseException("Unknown world: " + o.get("world").getAsString());
        return new Location(Bukkit.getWorld(o.get("world").getAsString()), o.get("x").getAsDouble(), o.get("y").getAsDouble(), o.get("z").getAsDouble(), o.get("yaw").getAsFloat(), o.get("pitch").getAsFloat());
    }
}
