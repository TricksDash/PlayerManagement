package com.elderrealm.main.gui;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.elderrealm.main.C;
import com.elderrealm.main.utils.Permissions;
import com.elderrealm.main.utils.StaffGUIItems;

public class StaffMenuGUI implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player");
			return false;
		}
		Player player = (Player) sender;
		
		if (!(player.hasPermission(Permissions.moderator) || player.hasPermission(Permissions.srMod)
				|| player.hasPermission(Permissions.admin) || player.hasPermission(Permissions.assistant))) {
			
			player.sendMessage(
					"§7[§cPermissions§7]: §7This is a §cStaff §7command");
	return false;
	}
		
		if(args.length == 1 || args.length > 1) {
			player.sendMessage("Usage: /StaffMenu (Name)");
		}
		
		if (Bukkit.getPlayer(args[0]) == null) {
			player.sendMessage(
					C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.gray + "Invalid player name");
		}
		
		else {

				Inventory staffMenu = Bukkit.createInventory(null, 27, C.red + "Staff Menu");
				Player target = Bukkit.getPlayer(args[0]);

				if (!(target == null))

				staffMenu.setItem(4, StaffGUIItems.playerSkull(player, target));
				staffMenu.setItem(11, StaffGUIItems.sameIPPlayers(player, target));
				staffMenu.setItem(12, StaffGUIItems.playerFrozen(player, target));
				staffMenu.setItem(14, StaffGUIItems.playerMuted(player, target));
				staffMenu.setItem(15, StaffGUIItems.playerWarns(player, target));

				player.openInventory(staffMenu);

		}
		return true;
	}
}
