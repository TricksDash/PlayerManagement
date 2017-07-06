package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.ElderRealmBans;
import com.elderrealm.main.utils.Permissions;

public class ClearWarns implements CommandExecutor {

	private ElderRealmBans plugin;

	public ClearWarns(ElderRealmBans pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		/* Checking Players permissions */
		if (!(player.hasPermission(Permissions.srMod) || player.hasPermission(Permissions.admin)
				|| sender instanceof ConsoleCommandSender))

			player.sendMessage("§7[§cPermissions§7]: §7This is a §cStaff §7command");
		else {
			if (args.length == 0 || args.length >= 2) {
				player.sendMessage("§7[§cPunishments§7]: Usage: /ClearWarns (Name)");
			}

			else if (args.length == 1) {

				// ** Online Player unbans **\\
				if (!(Bukkit.getPlayer(args[0]) == null)) {
					Player target = Bukkit.getPlayer(args[0]);

					plugin.getConfig().set("Warn-Data." + target.getUniqueId() + ".WarnCount", null);
					plugin.saveConfig();
					
					player.sendMessage("§7[§cPunishments§7]: §e" + target.getName() + "§7 Warns have been cleared.");
					target.sendMessage("§7[§cPunishments§7]: §7Your warns have been cleared.");
				}
			}
		}
		return true;
	}
}
