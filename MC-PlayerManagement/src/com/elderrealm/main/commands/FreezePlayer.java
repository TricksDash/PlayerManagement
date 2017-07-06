package com.elderrealm.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;
import com.elderrealm.main.utils.Permissions;

public class FreezePlayer implements CommandExecutor {

	private ElderRealmBans plugin;

	public FreezePlayer(ElderRealmBans pl) {
		plugin = pl;

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		/* Checking Players permissions */
		if (!(player.hasPermission(Permissions.srMod) || player.hasPermission(Permissions.admin)
				|| player.hasPermission(Permissions.moderator)))

			player.sendMessage("§7[§cPermissions§7]: §7This is an §2Moderator§7+ §7command");
		else {
			if (args.length == 0 || args.length >= 2) {
				player.sendMessage("§7[§cPunishments§7]: Usage: /Freeze (Name)");
			}

			else if (args.length == 1) {
				if (!(Bukkit.getPlayer(args[0]) == null)) {
					Player target = Bukkit.getPlayer(args[0]);

					if (plugin.getConfig().get("Player-Data." + target.getUniqueId() + ".Frozen") == null) {
						plugin.getConfig().set("Player-Data." + target.getUniqueId() + ".Frozen", true);
						
						player.sendMessage("§7[§cFreeze§7]: §e" + target.getName() + " §7has been Frozen");
						target.sendMessage("");
						target.sendMessage(C.bold + "§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛");
						target.sendMessage(C.bold + "");
						target.sendMessage(C.bold + "   §c§lYou have been frozen");
						target.sendMessage(C.bold + "   §c§lDo not log out!");
						target.sendMessage(C.bold + "");
						target.sendMessage(C.bold + "§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛");
						target.sendMessage("");
					
					
					} else if (plugin.getConfig().get("Player-Data." + target.getUniqueId() + ".Frozen").equals(true)) {
						plugin.getConfig().set("Player-Data." + target.getUniqueId() + ".Frozen", null);
						player.sendMessage("§7[§cFreeze§7]: §e" + target.getName() + " §7has been unfrozen");
						target.sendMessage("");
						target.sendMessage(C.bold + "§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛");
						target.sendMessage(C.bold + "");
						target.sendMessage(C.bold + "§a§lYou are no longer Frozen");
						target.sendMessage(C.bold + "");
						target.sendMessage(C.bold + "§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛§c⬛§e⬛");
						target.sendMessage("");
						
					}

				}else{
					player.sendMessage(C.gray + "[" + C.red + "Freeze" + C.gray + "]: " + C.gray + "Invalid player name");

				}
			}
		}
		return true;

	}
}
