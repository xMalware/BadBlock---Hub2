package fr.badblock.bukkit.hub.v2;

import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.Box;
import fr.badblock.bukkit.hub.v2.games.spleef.events.SpleefBreak;
import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.run.RunType;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter public class BadBlockHub extends BadblockPlugin
{
	
	@Getter@Setter public static BadBlockHub instance;
	
	public boolean	threadEnabled	= true;
	private Gson notRestrictiveGson;

	@Override
	public void onEnable(RunType runType)
	{
		setInstance(this);
		instance = this;
		// No game, no life :3
		if (runType.equals(RunType.GAME)) return;
		// Load Gson
		setNotRestrictiveGson(new GsonBuilder().setPrettyPrinting().create());
		// Load hub
		HubLoader.load(this);
	}

	@Override
	public void onDisable()
	{
		threadEnabled = false;
		SpleefBreak.restoreBlocks();
		ShootManager.getInstance().getBoxes().forEach(Box::restoreBlocks);
		ShootManager.getInstance().getArmorStand().remove();
	}

	public static void log(String message)
	{
		Bukkit.getServer().getConsoleSender().sendMessage("§e[HUB] §f" + message);
	}
	
}
