package com.elderrealm.main.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;

public class FreezeEvent implements Listener {

	private ElderRealmBans plugin;

	public FreezeEvent(ElderRealmBans pl) {
		plugin = pl;

	}

	@EventHandler
	public void onFreezePlayerWalk(PlayerMoveEvent event) {
		Player player = (Player) event.getPlayer();

		if (plugin.getConfig().get("Player-Data." + player.getUniqueId() + ".Frozen") == null) {
			return;
		} else if (plugin.getConfig().get("Player-Data." + player.getUniqueId() + ".Frozen").equals(true)) {
			event.setCancelled(true);
			player.sendMessage("");
			player.sendMessage(C.bold + "§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛");
			player.sendMessage(C.bold + "");
			player.sendMessage(C.bold + "   §c§lYou have been frozen");
			player.sendMessage(C.bold + "   §c§lDo not log out!");
			player.sendMessage(C.bold + "");
			player.sendMessage(C.bold + "§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛§3⬛§b⬛");
			player.sendMessage("");
		}
		return;
	}
}
