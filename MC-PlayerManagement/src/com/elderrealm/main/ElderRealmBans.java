package com.elderrealm.main;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.elderrealm.main.commands.ClearWarns;
import com.elderrealm.main.commands.FreezePlayer;
import com.elderrealm.main.commands.Kick;
import com.elderrealm.main.commands.Mute;
import com.elderrealm.main.commands.PermBan;
import com.elderrealm.main.commands.TempBan;
import com.elderrealm.main.commands.Unban;
import com.elderrealm.main.commands.Unmute;
import com.elderrealm.main.commands.Warn;
import com.elderrealm.main.events.BlockPermBannedBlayers;
import com.elderrealm.main.events.BlockTempBannedPlayers;
import com.elderrealm.main.events.ChatFilter;
import com.elderrealm.main.events.FreezeEvent;
import com.elderrealm.main.events.FreezePlayerTakesNoDamage;
import com.elderrealm.main.events.MutedPlayers;
import com.elderrealm.main.gui.StaffMenuGUI;
import com.elderrealm.main.gui.StaffMenuGUIItemClicked;


public class ElderRealmBans extends JavaPlugin {
	
	@Override
	public void onEnable() {
	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();

	registerCommands();
	registerConfig();
	registerEvents();

	logger.info(pdfFile.getName() + "Has been enabled - Version " + pdfFile.getVersion());	
}

public void onDisable() {
	PluginDescriptionFile pdfFile = getDescription();
	Logger logger = getLogger();

	logger.info(pdfFile.getName() + "Has been disabled - Version " + pdfFile.getVersion());
}
public void registerCommands() {
	getCommand("ban").setExecutor(new PermBan(this));
	getCommand("unban").setExecutor(new Unban(this));
	getCommand("kick").setExecutor(new Kick());
	getCommand("mute").setExecutor(new Mute(this));
	getCommand("unmute").setExecutor(new Unmute(this));
	getCommand("staffmenu").setExecutor(new StaffMenuGUI());
	getCommand("warn").setExecutor(new Warn(this));
	getCommand("clearwarns").setExecutor(new ClearWarns(this));
	getCommand("tempban").setExecutor(new TempBan(this));
	getCommand("freeze").setExecutor(new FreezePlayer(this));

	
}

public void registerEvents() {
	PluginManager pm = getServer().getPluginManager();
	
	pm.registerEvents(new MutedPlayers(this), this);
	pm.registerEvents(new BlockPermBannedBlayers(this), this);
	pm.registerEvents(new BlockTempBannedPlayers(this), this);
	pm.registerEvents(new ChatFilter(), this);
	pm.registerEvents(new StaffMenuGUIItemClicked(), this);
	pm.registerEvents(new FreezeEvent(this), this);
	pm.registerEvents(new FreezePlayerTakesNoDamage(this), this);
	
}

public void registerConfig() {
	getConfig().options().copyDefaults(true);
	saveDefaultConfig();
	}
}

