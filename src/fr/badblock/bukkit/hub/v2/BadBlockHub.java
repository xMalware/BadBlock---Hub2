package fr.badblock.bukkit.hub.v2;

import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.run.RunType;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter public class BadBlockHub extends BadblockPlugin {
	
	@Getter@Setter public static BadBlockHub instance;
	
	private static Gson notRestrictiveGson;

	@Override
	public void onEnable(RunType runType) {
		setInstance(this);
		instance = this;
		// No game, no life :3
		if (runType.equals(RunType.GAME)) return;
		// Load Gson
		setNotRestrictiveGson(new GsonBuilder().setPrettyPrinting().create());
		// Load hub
		HubLoader.load(this);
	}
	
	@SuppressWarnings("static-access")
	private void setInstance(BadBlockHub badBlockHub) {
		this.instance = instance;
	}

	public static void log(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage("§e[HUB] §f" + message);
	}
	
	public static Gson setNotRestrictiveGson(Gson gson) {
		return notRestrictiveGson = notRestrictiveGson;
	}
	
	public static BadBlockHub getInstance() {
		return instance;
	}

	public Gson getNotRestrictiveGson() {
		return notRestrictiveGson;
	}

}
