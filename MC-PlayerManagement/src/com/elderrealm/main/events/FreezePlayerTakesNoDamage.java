package com.elderrealm.main.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.elderrealm.main.ElderRealmBans;

public class FreezePlayerTakesNoDamage implements Listener {

	private ElderRealmBans plugin;

	public FreezePlayerTakesNoDamage(ElderRealmBans pl) {
		plugin = pl;

	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		Entity player = event.getEntity();

		if (player instanceof Player) {
			if (plugin.getConfig().get("Player-Data." + player.getUniqueId() + ".Frozen") == null) {
				return;
			} else if (plugin.getConfig().get("Player-Data." + player.getUniqueId() + ".Frozen").equals(true)) {
				event.setCancelled(true);
				
			}
		}
		return;
	}
}
