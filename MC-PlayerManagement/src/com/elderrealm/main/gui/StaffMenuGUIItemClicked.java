package com.elderrealm.main.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import com.elderrealm.main.C;

public class StaffMenuGUIItemClicked implements Listener {

	@EventHandler
    public void onInvClickEvent(InventoryClickEvent event) {

		if (event.getInventory().getTitle().equals(C.red+ "Staff Menu")) {
              event.setCancelled(true);
		}
	}
}
