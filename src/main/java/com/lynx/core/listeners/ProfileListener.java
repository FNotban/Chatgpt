package com.lynx.core.listeners;

import com.lynx.core.profiles.ProfileManager; import org.bukkit.event.*; import org.bukkit.event.player.*;
public final class ProfileListener implements Listener { private final ProfileManager profiles; public ProfileListener(ProfileManager profiles){this.profiles=profiles;} @EventHandler public void onJoin(PlayerJoinEvent e){profiles.load(e.getPlayer());} @EventHandler public void onQuit(PlayerQuitEvent e){profiles.unload(e.getPlayer().getUniqueId());} }
