package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.utils.Permissions;

public class Kick implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		
		/* Checking Players permissions */
		if (!(player.hasPermission(Permissions.moderator) || player.hasPermission(Permissions.srMod)
				|| player.hasPermission(Permissions.admin) || player.hasPermission(Permissions.assistant)))

			player.sendMessage("§7[§cPermissions§7]: §7This is a §cStaff §7command");
		else {

			/* If args is incorrect will send the player the correct usage of the command */
			if (args.length == 0) {
				player.sendMessage("§7[§cPunishments§7]: Usage: /Kick (Name) (Reason)");
			} else if (args.length >= 1) {

				
				/* Getting all of the Reason args */
				
				StringBuilder kickReason = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					if (i > 1)
						kickReason.append(" ");
					kickReason.append(args[i]);
				}

			
				
				// ** Kick **\\
				if (!(Bukkit.getPlayer(args[0]) == null)) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target.hasPermission(Permissions.admin)) {
						player.sendMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.red + "You cannot kick that player!");
						return false;
					}

					target.kickPlayer(
							"§cYou have been kicked from the Server" + "\n" + "\n" + " §7Kicked By: "
									+ "§6" + player.getName() + "\n" + " §7Reason: " + C.gold + kickReason);

					Bukkit.broadcast("§7[§cPunishments§7]: " + C.daqua + target.getName()
							+ C.gray + " has been kicked by " + C.yellow + player.getName() + "\n" + C.dgray + " ["
							+ kickReason + "]", "playermanagement.staff");
				
				
				/* If the player is offline it will return an invalid player name */
				} else {
					player.sendMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "]: " + C.gray + "Invalid player name");
				}
			}
		}
		return true;
	}
}
