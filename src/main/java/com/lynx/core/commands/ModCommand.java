package com.lynx.core.commands;

import com.lynx.core.staff.StaffManager; import org.bukkit.command.*; import org.bukkit.entity.Player;
public final class ModCommand implements CommandExecutor { private final StaffManager staff; public ModCommand(StaffManager staff){this.staff=staff;} public boolean onCommand(CommandSender sender, Command command, String label, String[] args){ if(!(sender instanceof Player)){sender.sendMessage("Players only.");return true;} if(!sender.hasPermission("lynx.staff")){sender.sendMessage("§cNo permission.");return true;} staff.toggleMod((Player)sender); return true; } }
