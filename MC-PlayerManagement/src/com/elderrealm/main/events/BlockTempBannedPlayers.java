package com.elderrealm.main.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;

public class BlockTempBannedPlayers implements Listener {

	private ElderRealmBans plugin;

	public BlockTempBannedPlayers(ElderRealmBans pl) {
		plugin = pl;

	}

	@EventHandler
	public void playerJoinBanned(PlayerLoginEvent event) {

		Player player = event.getPlayer();

		if (plugin.getConfig().get("TempBan-List." + player.getUniqueId() + ".Current.Banned") == null) {
			return;
		}

		/* Temp Ban Data */
		String tempBanReason = (String) plugin.getConfig()
				.get("TempBan-List." + player.getUniqueId() + ".Current.BanReason");
		String tempBannedBy = (String) plugin.getConfig().get("TempBan-List." + player.getUniqueId() + ".Current.BannedBy");

		/* Timing Data */
		long systemTime = System.currentTimeMillis();
		long unbanTime = plugin.getConfig().getLong("TempBan-List." + player.getUniqueId() + ".Current.UnbanTime");
		long systemTimeLeftOnBan = System.currentTimeMillis() - unbanTime;
		long minutesLeftOnBan = systemTimeLeftOnBan / 60000;

		if (plugin.getConfig().get("TempBan-List." + player.getUniqueId() + ".Current.Banned").equals(true)) {

			if (systemTime > unbanTime) {

				plugin.getConfig().set("TempBan-List." + player.getUniqueId() + ".Current", null);
				plugin.getConfig().set("Warn-Data." + player.getUniqueId() + ".WarnCount", null);
				plugin.saveConfig();

				Bukkit.broadcast(C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.daqua + player.getName()+ C.gray + " has just logged in after their temporary ban expired", "playermanagement.staff");

			} else
				
				event.setKickMessage(C.red+C.underline + "You are temporarily banned from the Server" + "\n" + "\n" + C.gray
						+ " Banned By: " + C.gold + tempBannedBy + "\n" + C.gray + " Reason: " + C.gold + tempBanReason
						+ "\n" + C.gray + "Time Left: " + C.gold + minutesLeftOnBan + "Minutes" + "\n" + C.gray + " Account Name: "
						+ C.gold + player.getName());

				event.setResult(Result.KICK_BANNED);
		}
	}
}
