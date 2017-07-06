package com.elderrealm.main.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;

public class StaffGUIItems {

	public static ItemStack playerSkull(Player player, Player target) {
		String ipAddress = "§4Blocked";
		ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);

		if (player.hasPermission(Permissions.admin)) {
			ipAddress = "§7" + target.getAddress();
		}
		String rank;

		if (target.hasPermission(Permissions.admin)) {
			rank = "§cAdmin";
		} else if (target.hasPermission(Permissions.srMod)) {
			rank = "§2Sr.Moderator";
		} else if (target.hasPermission(Permissions.moderator)) {
			rank = "§dModerator";
		} else if (target.hasPermission(Permissions.assistant)) {
			rank = "§9Assistant";
		} else if (target.hasPermission(Permissions.emerald)) {
			rank = "§aEmerald";
		} else if (target.hasPermission(Permissions.diamond)) {
			rank = "§3Diamond";
		} else if (target.hasPermission(Permissions.gold)) {
			rank = "§3Diamond";
		} else if (target.hasPermission(Permissions.iron)) {
			rank = "§6Gold";
		} else {
			rank = "§8Player";
		}

		SkullMeta playerSkullMeta = (SkullMeta) playerSkull.getItemMeta();
		playerSkullMeta.setOwner(target.getName());

		playerSkullMeta.setDisplayName(C.red + target.getName());
		playerSkullMeta
				.setLore(Arrays.asList("§7§nPlayer Information:", "", "§7IP Address: " + ipAddress,
						"§7World: " + "§6" + target.getLocation().getWorld().getName(),
						"§7Location: " + "§6" + target.getLocation().getBlockX() + ", "
								+ target.getLocation().getBlockY() + ", " + target.getLocation().getBlockZ(),
						"§7Rank: " + rank));

		playerSkull.setItemMeta(playerSkullMeta);

		return playerSkull;
	}

	public static ItemStack sameIPPlayers(Player player, Player target) {
		ItemStack sameIP = new ItemStack(Material.BOOK);

		List<String> playersWithSameIP = new ArrayList<>();
		String targetIPAddress = target.getAddress().getHostString();

		for (Player onlinePlayers : Bukkit.getServer().getOnlinePlayers()) {
			if (onlinePlayers.getAddress().getHostString().equals(targetIPAddress)) {
				playersWithSameIP.add(onlinePlayers.getName());
			}
		}

		ItemMeta sameIPMeta = sameIP.getItemMeta();
		sameIPMeta.setDisplayName("§6Accounts on same IP");
		sameIPMeta.setLore(Arrays.asList("§7§nAccounts:", "",
				"§7" + playersWithSameIP.toString().replace("[", "").replace("]", "\n").trim()));
		sameIP.setItemMeta(sameIPMeta);
		return sameIP;
	}
	
	public static ItemStack playerFrozen(Player player, Player target) {
		ItemStack playerFrozen = new ItemStack(Material.ICE);
		
		String isPlayerFrozen = C.red + "Frozen";
		if(ElderRealmBans.getPlugin(ElderRealmBans.class).getConfig().get("Player-Data." + target.getUniqueId() + ".Frozen") == null) {
			isPlayerFrozen = C.green+ "Not Frozen";
		}
		ItemMeta playerFrozenMeta = playerFrozen.getItemMeta();
		playerFrozenMeta.setDisplayName("§6Player Frozen");
		playerFrozenMeta.setLore(Arrays.asList("§7§nFrozen:", "",
				isPlayerFrozen));
		playerFrozen.setItemMeta(playerFrozenMeta);
		return playerFrozen;
	}
	
	public static ItemStack playerMuted(Player player, Player target) {
		ItemStack playerMuted = new ItemStack(Material.PAPER);
		
		String isPlayerMuted= C.red + "Muted";
		if(ElderRealmBans.getPlugin(ElderRealmBans.class).getConfig().get("Mute-List." + target.getUniqueId() + ".Muted") == null) {
			isPlayerMuted = C.green+ "Not Muted";
		}
		ItemMeta playerMutedMeta = playerMuted.getItemMeta();
		playerMutedMeta.setDisplayName("§6Player Muted");
		playerMutedMeta.setLore(Arrays.asList("§7§nMuted:", "",
				isPlayerMuted));
		playerMuted.setItemMeta(playerMutedMeta);
		return playerMuted;
	}
	public static ItemStack playerWarns(Player player, Player target) {
		ItemStack playerWarns = new ItemStack(Material.BOOK);
		
		String playerWarnCount = C.red + ElderRealmBans.getPlugin(ElderRealmBans.class).getConfig().get("Warn-Data." + target.getUniqueId() + ".WarnCount");
		if(ElderRealmBans.getPlugin(ElderRealmBans.class).getConfig().get("Warn-Data." + target.getUniqueId() + ".WarnCount") == null) {
			playerWarnCount = C.green+ "No warns";
		}
		
		
		ItemMeta playerWarnsMeta = playerWarns.getItemMeta();
		playerWarnsMeta.setDisplayName("§6Warn Count");
		playerWarnsMeta.setLore(Arrays.asList("§7§nWarns:", "",
				playerWarnCount));
		playerWarns.setItemMeta(playerWarnsMeta);
		return playerWarns;
	}
}
