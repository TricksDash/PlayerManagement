package com.elderrealm.main.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;

public class BlockPermBannedBlayers implements Listener {

	private ElderRealmBans plugin;

	public BlockPermBannedBlayers(ElderRealmBans pl) {
		plugin = pl;

	}

	@EventHandler
	public void playerJoinBanned(PlayerLoginEvent event) {

		Player player = event.getPlayer();
		/* Perm Ban data */
		String banReason = (String) plugin.getConfig().get("Ban-List." + player.getUniqueId().toString()  + ".Current.BanReason");
		String bannedBy = (String) plugin.getConfig().get("Ban-List." + player.getUniqueId().toString()  + ".Current.BannedBy");
		String dateBanned = (String) plugin.getConfig().get("Ban-List." + player.getUniqueId().toString()  + ".Current.BanDate");
		Object currentBanned = plugin.getConfig().get("Ban-List." + player.getUniqueId().toString() + ".Current.Banned");

		if(currentBanned == null) {
			return;
		}
		
		if (currentBanned.equals(true)) {

			/* PERM BANS */
			event.setKickMessage(C.red + C.underline + "You are permanently banned from the Server" + "\n" + "\n"
					+ C.gray + " Banned By: " + C.gold + bannedBy + "\n" + C.gray + " Reason: " + C.gold + banReason
					+ "\n" + C.gray + " Date of Ban: " + C.gold + dateBanned + "\n" + C.gray + " Account Name: "
					+ C.gold + player.getName());

			event.setResult(Result.KICK_BANNED);
		} else
			return;
	}
}
