package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;
import com.elderrealm.main.utils.Permissions;

public class Unban implements CommandExecutor {

	private ElderRealmBans plugin;

	public Unban(ElderRealmBans pl) {
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
				player.sendMessage("§7[§cPunishments§7]: Usage: /Unban (Name)");
			}

			else if (args.length == 1) {

				// ** Player unbans **\\
				if (Bukkit.getPlayer(args[0]) == null) {
					@SuppressWarnings("deprecation")
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					
					if(plugin.getConfig().get("Ban-List." + target.getUniqueId()) == null 
							&& plugin.getConfig().get("TempBan-List." + target.getUniqueId()) == null) {
						
						player.sendMessage("§7[§cPunishments§7]: That player is not banned");
						return false;
					}

					plugin.getConfig().set("Ban-List." + target.getUniqueId(), null);
					plugin.getConfig().set("TempBan-List." + target.getUniqueId(), null);
					plugin.getConfig().set("Warn-Data." + target.getUniqueId(), null);
					plugin.saveConfig();

					Bukkit.broadcast((C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.daqua + target.getName()
							+ C.gray + " has been unbanned by " + C.yellow + player.getName()), "playermanagement.staff");
				}
			}
		}
		return true;

	}
}
