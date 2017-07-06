package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;
import com.elderrealm.main.utils.Permissions;

public class TempBan implements CommandExecutor {

	private ElderRealmBans plugin;

	public TempBan(ElderRealmBans pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		if (!(player.hasPermission(Permissions.moderator) || player.hasPermission(Permissions.srMod)
				|| player.hasPermission(Permissions.admin)))

			player.sendMessage("§7[§cPermissions§7]: §7This is an §cStaff §7command");
		else {
			
			if (args.length == 0 || args.length == 1) {
				player.sendMessage("§7[§cPunishments§7]: Usage: /TempBan (Name) (Time) (Reason)");

			}

			else if (args.length >= 3) {
				StringBuilder banReason = new StringBuilder();
				for (int i = 2; i < args.length; i++) {
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
					
	                try {
	                    Integer.parseInt(args[1]);
	                }
	                catch (NumberFormatException e) {
	                	player.sendMessage("§7[§cPunishments§7]: Usage: /TempBan (Name) (Time) (Reason)");
	                   return false;
	                }

					long hoursToMiliseconds = 3600000 * Long.parseLong(args[1]);
					long unbanTime = System.currentTimeMillis() + hoursToMiliseconds;

					target.kickPlayer(C.red + "You have been temporarily banned from the Server" + "\n" + "\n" 
					+ C.gray+ " Banned By: " + C.gold + player.getName() + "\n" 
					+ C.gray + " Reason: " + C.gold + banReason.toString() + "\n" 
					+ C.gray + " Ban Length: " + C.gold + Long.parseLong(args[1]) + " Hours" + "\n" 
					+ C.gray + " Account Name: "+ C.gold + target.getName());
					
					Bukkit.broadcastMessage(
					C.gray + "[" + C.red + "Punishments" + C.gray + "] "
					+ C.daqua + target.getName() 
					+ C.gray + " has been temporarily banned for\n" 
					+ C.dgray + "[" + Long.parseLong(args[1]) + " Hours" + "]");
					
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.Banned", true);
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BannedAccount", target.getName());
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BannedBy", player.getName());
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BanReason", banReason.toString());
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.LengthOfBanHours", Long.parseLong(args[1]));
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.UnbanTime", unbanTime);
					plugin.saveConfig();
				
				}else {
					@SuppressWarnings("deprecation")
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

					long hoursToMiliseconds = Long.parseLong(args[1]) * 3600000;
					long unbanTime = System.currentTimeMillis() + hoursToMiliseconds;
					
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.Banned", true);
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BannedAccount", target.getName());
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BannedBy", player.getName());
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BanReason", banReason.toString());
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.LengthOfBanHours", Long.parseLong(args[1]));
					plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.UnbanTime", unbanTime);
					plugin.saveConfig();

					Bukkit.broadcastMessage(
					C.gray + "[" + C.red + "Punishments" + C.gray + "] "
					+ C.daqua + target.getName() 
					+ C.gray + " has been temporarily banned for \n" 
					+ C.dgray + "[" + Long.parseLong(args[1]) + " Hours" + "]");
				}
			}
		}
		return true;
	}
}
