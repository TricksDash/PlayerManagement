package com.elderrealm.main.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;
import com.elderrealm.main.utils.Permissions;

public class Warn implements CommandExecutor {

	private ElderRealmBans plugin;

	public Warn(ElderRealmBans pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		/* Checking Players permissions */
		if (!(player.hasPermission(Permissions.moderator) || player.hasPermission(Permissions.srMod)
				|| player.hasPermission(Permissions.admin) || player.hasPermission(Permissions.assistant)))

			player.sendMessage("§7[§cPermissions§7]: §7This is a §cStaff §7command");
		else {

			/*
			 * If args is incorrect will send the player the correct usage of
			 * the command
			 */
			if (args.length == 0) {
				player.sendMessage("§7[§cPunishments§7]: Usage: /Warn (Name) (Reason)");
			} else if (args.length >= 1) {

				StringBuilder warnReason = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					if (i > 1)
						warnReason.append(" ");
					warnReason.append(args[i]);
				}

				// ** Warn **\\
				if (!(Bukkit.getPlayer(args[0]) == null)) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target.hasPermission(Permissions.admin)) {
						player.sendMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.red + "You cannot warn that player!");
						return false;
					}

					target.sendMessage("§7[§cPunishments§7]:" + C.gray + " You have been warned by " + C.yellow
							+ player.getName() + C.gray + " for" + C.dgray + " [" + warnReason.toString() + "]");

					player.sendMessage(
							"§7[§cPunishments§7]: " + C.yellow + target.getName() + C.gray + " has been warned!");

					plugin.getConfig().set("Warn-Data." + target.getUniqueId() + ".WarnCount",
							plugin.getConfig().getInt("Warn-Data." + target.getUniqueId() + ".WarnCount", 0) + 1);
					plugin.saveConfig();

					int warnCount = (int) plugin.getConfig().get("Warn-Data." + target.getUniqueId() + ".WarnCount");

					
					/* Kick player with 3 warnings */
					if (warnCount == 3) {

						target.kickPlayer(
								"§cYou have been kicked from the Server" + "\n" + "\n" + " §7Kicked By: §6Server"
										+ "\n" + " §7Reason: §6You have 3 warnings. You need to stop breaking rules.");

						Bukkit.broadcast(
								"§7[§cPunishments§7]: " + C.daqua + target.getName() + C.gray
										+ " has been kicked by §eServer" + "\n" + C.dgray + " [3 Warnings]",
								"playermanagement.staff");
					}

					/* Mute player with 5 warnings */
					if (warnCount == 5) {
						long unmuteTime = System.currentTimeMillis() + 900000;
						Date unmuteDate = new Date(unmuteTime);
						
						target.sendMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.gray
								+ "You have been muted by " + C.yellow + player.getName() + C.gray + " until " + C.daqua + unmuteDate + C.gray + " for " + C.red
								+ "[5 Warnings]");
						plugin.getConfig().set("Mute-List." + target.getUniqueId() + ".Muted", target.getName());
						plugin.getConfig().set("Mute-List." + target.getUniqueId() + ".Muted", true);
						plugin.getConfig().set("Mute-List." + target.getUniqueId() + ".MuteReason", "5 Warnings");
						plugin.getConfig().set("Mute-List." + target.getUniqueId() + ".UnmuteTime", unmuteTime);
						plugin.saveConfig();

						Bukkit.broadcast(C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.daqua + target.getName()
								+ C.gray + " has been muted by " + C.yellow + player.getName() 
								+ C.gray + " untill " +  C.daqua + unmuteDate 
								+ C.gray + " for " + C.red + "[5 Warnings]", "playermanagement.staff");

					}
					// * Ban player with 7 warnings *//
					if (warnCount == 7) {

						long unbanTime = System.currentTimeMillis() + 3600000;
						Date tempUnbanDate = new Date(unbanTime);
						target.kickPlayer(C.red + "You have been temporarily banned from Server" + "\n" + "\n" 
								+ C.gray+ " Banned By: " + C.gold + player.getName() + "\n" 
								+ C.gray + " Reason: " + C.gold + "7 warnings" + "\n" 
								+ C.gray + " Unban Date: " + C.gold + tempUnbanDate + "\n" 
								+ C.gray + " Account Name: "+ C.gold + target.getName());

						plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.Banned", true);
						plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BannedAccount", target.getName());
						plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BannedBy", player.getName());
						plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BanReason", "7 Warninings");
						plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.BanDate", new SimpleDateFormat("dd/MM/yy").format(new Date()));
						plugin.getConfig().set("TempBan-List." + target.getUniqueId() + ".Current.UnbanTime", unbanTime);
						plugin.saveConfig();

						Bukkit.broadcastMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "] "
								+ C.daqua + target.getName() 
								+ C.gray + " has been temporarily banned untill \n" 
								+ C.dgray + "[" + tempUnbanDate + "]"
								+ C.gray + " because " 
								+ C.dgray + "[7 Warnings]");
					}

				} else {
					player.sendMessage("§7[§cPunishments§7]: Invalid player name");
				}
			}
		}
		return true;
	}
}
