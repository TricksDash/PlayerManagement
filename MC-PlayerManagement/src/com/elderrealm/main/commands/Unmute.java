package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;
import com.elderrealm.main.utils.Permissions;

public class Unmute implements CommandExecutor {

	private ElderRealmBans plugin;

	public Unmute(ElderRealmBans pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;

		/* Checking Players permissions */
		if (!(player.hasPermission(Permissions.moderator) || player.hasPermission(Permissions.srMod)
				|| player.hasPermission(Permissions.admin) || player.hasPermission(Permissions.assistant)))

			player.sendMessage("§7[§cPermissions§7]: §7This is an §cStaff §7command");
		else {

			if (args.length == 0) {
				player.sendMessage("§7[§cPunishments§7]: Usage: /Mute (Name)");
			}

			else if (args.length == 1) {
				// ** Unmute **\\
				if (!(Bukkit.getPlayer(args[0]) == null)) {
					Player target = Bukkit.getPlayer(args[0]);

					target.sendMessage(
							C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.gray + "You have been unmuted");

					plugin.getConfig().set("Mute-List." + target.getUniqueId(), null);
					plugin.saveConfig();

					Bukkit.broadcast(C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.daqua + target.getName()
							+ C.gray + " has been unmuted by " + C.yellow + player.getName(), "playermanagement.staff");
				} else {
					player.sendMessage(C.red + "Invalid player name");
				}

			}
		}
		return true;
	}
}
