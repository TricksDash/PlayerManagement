package com.elderrealm.main.commands;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;
import com.elderrealm.main.utils.Permissions;

public class Mute implements CommandExecutor {

	private ElderRealmBans plugin;

	public Mute(ElderRealmBans pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		/* Checking Players permissions */
		if (!(player.hasPermission(Permissions.moderator) || player.hasPermission(Permissions.srMod)
				|| player.hasPermission(Permissions.admin) || player.hasPermission(Permissions.assistant)))

			player.sendMessage("§7[§cPermissions§7]: §7This is an §9Staff §7command");
		else {

			if (args.length < 2) {
				player.sendMessage("§7[§cPunishments§7]: Usage: /Mute (Name) (Time) (Reason)");
			}

			else if (args.length >= 2) {
				// ** Mute **\\
				if (!(Bukkit.getPlayer(args[0]) == null)) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target.hasPermission(Permissions.admin)) {
						player.sendMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.red + "You cannot mute that player!");
						return false;
					}
					
					StringBuilder muteReason = new StringBuilder();
					for (int i = 2; i < args.length; i++) {
						if (i > 1)
							muteReason.append(" ");
						muteReason.append(args[i]);
					}
					
					long unmuteTime = System.currentTimeMillis() + Long.parseLong(args[1]);
					Date unmuteDate = new Date(unmuteTime);


					target.sendMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.gray
							+ "You have been muted by " + C.yellow + player.getName() + C.gray + " until " + C.daqua + unmuteDate + C.gray + " for" + C.red
							+ " [" + muteReason.toString() + "]");
					plugin.getConfig().set("Mute-List." + target.getUniqueId() + ".Muted", target.getName());
					plugin.getConfig().set("Mute-List." + target.getUniqueId() + ".Muted", true);
					plugin.getConfig().set("Mute-List." + target.getUniqueId() + ".MuteReason", muteReason.toString());
					plugin.getConfig().set("Mute-List." + target.getUniqueId() + ".UnmuteTime", unmuteTime);
					plugin.saveConfig();

					Bukkit.broadcast(C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.daqua + target.getName()
							+ C.gray + " has been muted by " + C.yellow + player.getName() 
							+ C.gray + " untill " +  C.daqua + unmuteDate 
							+ C.red + " ["+ muteReason.toString() +"]", "playermanagement.staff");

					/*
					 * If the player is offline it will return an invalid player
					 * name
					 */
				} else {
					player.sendMessage(
							C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.gray + "Invalid player name");
				}
			}
		}
		return true;
	}
}
