package com.lynx.core.factions;

import org.bukkit.Location;

public final class Claim {
    private final String world;
    private final int minX, maxX, minY, maxY, minZ, maxZ;
    public Claim(Location a, Location b) {
        if (!a.getWorld().equals(b.getWorld())) throw new IllegalArgumentException("Claim corners must be in the same world");
        this.world = a.getWorld().getName();
        this.minX = Math.min(a.getBlockX(), b.getBlockX()); this.maxX = Math.max(a.getBlockX(), b.getBlockX());
        this.minY = Math.min(a.getBlockY(), b.getBlockY()); this.maxY = Math.max(a.getBlockY(), b.getBlockY());
        this.minZ = Math.min(a.getBlockZ(), b.getBlockZ()); this.maxZ = Math.max(a.getBlockZ(), b.getBlockZ());
    }
    public boolean contains(Location l) { return l != null && l.getWorld() != null && world.equals(l.getWorld().getName()) && l.getBlockX() >= minX && l.getBlockX() <= maxX && l.getBlockY() >= minY && l.getBlockY() <= maxY && l.getBlockZ() >= minZ && l.getBlockZ() <= maxZ; }
    public boolean intersects(Claim o) { return world.equals(o.world) && minX <= o.maxX && maxX >= o.minX && minY <= o.maxY && maxY >= o.minY && minZ <= o.maxZ && maxZ >= o.minZ; }
    public int getWidth() { return maxX - minX + 1; }
    public int getLength() { return maxZ - minZ + 1; }
    public int getArea() { return getWidth() * getLength(); }
    public double getCost(double blockPrice) { return getArea() * blockPrice; }
}
