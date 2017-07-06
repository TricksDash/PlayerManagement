package com.elderrealm.main.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;
import com.elderrealm.main.utils.Permissions;

public class PermBan implements CommandExecutor {

	private ElderRealmBans plugin;

	public PermBan(ElderRealmBans pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		
		/* Checking Players permissions */
		if (!(player.hasPermission(Permissions.moderator) || player.hasPermission(Permissions.srMod)
				|| player.hasPermission(Permissions.admin)))

			player.sendMessage("§7[§cPermissions§7]: §7This is an §cStaff §7command");
		else {
			
			if (args.length == 0 || args.length == 1) {
				player.sendMessage("§7[§cPunishments§7]: Usage: /Ban (Name) (Reason)");

			}

			else if (args.length >= 2) {
				StringBuilder banReason = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					if (i > 1)
						banReason.append("");
					banReason.append(args[i]);
				}
				

// ** Online Player bans **\\
				if (!(Bukkit.getPlayer(args[0]) == null)) {
					Player target = Bukkit.getPlayer(args[0]);
					
					if(target.hasPermission(Permissions.admin)) {
						player.sendMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.red + "You cannot ban that player!");
						return false;
					}
					
					if(!(plugin.getConfig().get("Ban-List." + target.getUniqueId()) == null)) {
						
						player.sendMessage("§7[§cPunishments§7]: That player is already banned");
						return false;
					}

					target.kickPlayer(C.red + "You have been permanently banned from the Server" + "\n" + "\n"
					+ C.gray+ " Banned By: " + C.gold + player.getName() + "\n" 
					+ C.gray + " Reason: " + C.gold + banReason.toString() + "\n" 
					+ C.gray + " Date of Ban: " + C.gold + new SimpleDateFormat("dd/MM/yy").format(new Date()) + "\n" 
					+ C.gray + " Account Name: "+ C.gold + target.getName());
					
					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".Current.Banned", true);
					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".Current.BannedAccount", target.getName());
					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".Current.BannedBy", player.getName());
					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".Current.BanReason", banReason.toString());
					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".Current.BanDate", new SimpleDateFormat("dd/MM/yy").format(new Date()));
					plugin.saveConfig();
					
					/* Message details of the ban */
					Bukkit.broadcastMessage((C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.daqua + target.getName()
							+ C.gray + " has been permanently banned by " + C.yellow + player.getName() + "\n" + C.dgray
							+ " [" + banReason.toString() + "]"));

// ** Offline Player Bans **\\
				} else {
					@SuppressWarnings("deprecation")
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					
					/* Message details of the ban */
				
					Bukkit.broadcastMessage((C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.daqua + target.getName()
							+ C.gray + " has been permanently banned by " + C.yellow + player.getName() + "\n" + C.dgray
							+ " [" + banReason.toString() + "]"));

					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".Banned", true);
					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".BannedBy", player.getName());
					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".BanReason", banReason.toString());
					plugin.getConfig().set("Ban-List." + target.getUniqueId().toString() + ".BanDate",
							new SimpleDateFormat("dd/MM/yy").format(new Date()));

					plugin.saveConfig();
				}
			}
		}
		return true;
	}
}
