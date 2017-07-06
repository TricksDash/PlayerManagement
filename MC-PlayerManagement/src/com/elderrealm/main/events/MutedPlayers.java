package com.elderrealm.main.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.elderrealm.main.C;
import com.elderrealm.main.ElderRealmBans;

public class MutedPlayers implements Listener {

	private ElderRealmBans plugin;

	public MutedPlayers(ElderRealmBans pl) {
		plugin = pl;

	}

	@EventHandler
	public void onMutedPlayerTalk(AsyncPlayerChatEvent event) {
		Player player = (Player)event.getPlayer();
		
		String muteReason = (String) plugin.getConfig().get("Mute-List." + player.getUniqueId() + ".MuteReason");
		
		if(plugin.getConfig().get("Mute-List." + player.getUniqueId()) == null) {
			return;
		}
		
		long systemTime = System.currentTimeMillis();
		long unmuteTime = plugin.getConfig().getLong(("Mute-List." + player.getUniqueId() + ".UnmuteTime"));
		Date unmuteDate = new Date(unmuteTime);

		if(systemTime > unmuteTime ) {
			player.sendMessage(
					C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.gray + "You have been unmuted");

			plugin.getConfig().set("Mute-List." + player.getUniqueId(), null);
			plugin.saveConfig();
		
		}else if(!(plugin.getConfig().get("Mute-List." + player.getUniqueId() + ".Muted") == null)) {
			event.setCancelled(true);
			player.sendMessage(C.gray + "[" + C.red + "Punishments" + C.gray + "] " + C.gray + "You are currently muted for" + C.red + muteReason);
			player.sendMessage(C.gray+ "Mute expires " + C.yellow +  unmuteDate);
			
			
		}else {
			return;
		}
	}
}
