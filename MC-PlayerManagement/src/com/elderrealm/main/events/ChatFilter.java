package com.elderrealm.main.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.elderrealm.main.C;
import com.elderrealm.main.utils.ChatFilterWords;
import com.elderrealm.main.utils.Permissions;

public class ChatFilter implements Listener {

	@EventHandler
	public void onPlayerCuss(AsyncPlayerChatEvent event) {
		Player player = (Player) event.getPlayer();
		String message = event.getMessage();

		if (event.isCancelled()) {
			return;
		}

		List<String> blockedWords = new ArrayList<>();
		
		if (!(player.hasPermission(Permissions.admin)))
			for (String b : ChatFilterWords.blockedWords) {
				if (message.toLowerCase().contains(b)) {
					blockedWords.add(b);
					event.setCancelled(true);
				}
				if (blockedWords.contains(b)) {
					Bukkit.broadcast(C.gray + "[" + C.red + "PlayerManagement" + C.gray + "] " + C.green + player.getName()
							+ C.gray + " attempted to say: " + C.daqua + event.getMessage(), Permissions.staff);

					player.sendMessage(C.gray + "[" + C.red + "ChatFilter" + C.gray + "] " + C.gray
							+ "Your message was blocked\n" + C.gray + "[" + C.red
							+ blockedWords.toString().replace("[", "").replace("]", "").trim() + C.gray + "]");
				}
			}
	}
}
