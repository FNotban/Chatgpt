package com.lynx.core.classes;

import org.bukkit.Material; import org.bukkit.entity.Player; import org.bukkit.inventory.ItemStack;
import java.util.*; import java.util.concurrent.ConcurrentHashMap;

public final class ClassManager {
    private static ClassManager instance; private final Map<UUID, PvPClass> active = new ConcurrentHashMap<UUID, PvPClass>(); private final Map<UUID, Integer> bardEnergy = new ConcurrentHashMap<UUID, Integer>();
    public ClassManager() { instance = this; } public static ClassManager getInstance() { return instance; }
    public PvPClass detect(Player p) { ItemStack[] a = p.getInventory().getArmorContents(); if (a == null || a.length < 4) return PvPClass.NONE; Material h=a[3]==null?Material.AIR:a[3].getType(), c=a[2]==null?Material.AIR:a[2].getType(), l=a[1]==null?Material.AIR:a[1].getType(), b=a[0]==null?Material.AIR:a[0].getType();
        if (h==Material.GOLD_HELMET&&c==Material.GOLD_CHESTPLATE&&l==Material.GOLD_LEGGINGS&&b==Material.GOLD_BOOTS) return PvPClass.BARD;
        if (h==Material.LEATHER_HELMET&&c==Material.LEATHER_CHESTPLATE&&l==Material.LEATHER_LEGGINGS&&b==Material.LEATHER_BOOTS) return PvPClass.ARCHER;
        if (h==Material.IRON_HELMET&&c==Material.IRON_CHESTPLATE&&l==Material.IRON_LEGGINGS&&b==Material.IRON_BOOTS) return PvPClass.MINER;
        if (h==Material.CHAINMAIL_HELMET&&c==Material.CHAINMAIL_CHESTPLATE&&l==Material.CHAINMAIL_LEGGINGS&&b==Material.CHAINMAIL_BOOTS) return PvPClass.ROGUE; return PvPClass.NONE; }
    public PvPClass refresh(Player p) { PvPClass clazz = detect(p); active.put(p.getUniqueId(), clazz); return clazz; }
    public PvPClass get(Player p) { PvPClass c = active.get(p.getUniqueId()); return c == null ? refresh(p) : c; }
    public int addBardEnergy(UUID uuid, int amount) { int next = Math.min(120, getBardEnergy(uuid) + amount); bardEnergy.put(uuid, next); return next; }
    public int getBardEnergy(UUID uuid) { Integer i = bardEnergy.get(uuid); return i == null ? 0 : i; }
    public boolean spendBardEnergy(UUID uuid, int amount) { int now = getBardEnergy(uuid); if (now < amount) return false; bardEnergy.put(uuid, now - amount); return true; }
}
