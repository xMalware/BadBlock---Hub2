package fr.badblock.bukkit.hub.v2.listeners;

import java.io.IOException;

import org.bukkit.Bukkit;

import fr.badblock.bukkit.hub.v2.rabbit.HubPacketListener;
import fr.badblock.bukkit.hub.v2.rabbit.InstanceKeepAliveListener;
import fr.badblock.bukkit.hub.v2.rabbit.SEntryInfosListener;
import fr.badblock.gameapi.BadblockPlugin;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.technologies.RabbitAPIListener;
import fr.badblock.gameapi.utils.BukkitUtils;

public class ListenerPackages {

	private static String[] string = new String[]
			{
		"fr.badblock.bukkit.hub.v2.listeners.players"	
	};
	
	public static void load(BadblockPlugin plugin)
	{
		loadBukkitListeners(plugin);
		loadRabbitListeners();
	}
	
	// Load bukkit listeners
	private static void loadBukkitListeners(BadblockPlugin plugin)
	{
		try {
			BukkitUtils.instanciateListenersAndCommandsFrom(plugin, string);
		} catch (IOException e) {
			e.printStackTrace();
			Bukkit.shutdown();
		}
	}
	
	private static void loadRabbitListeners()
	{
		loadRabbitListener(HubPacketListener.class);
		loadRabbitListener(SEntryInfosListener.class);
		loadRabbitListener(InstanceKeepAliveListener.class);
	}
	
	private static void loadRabbitListener(Class<? extends RabbitAPIListener> listener)
	{
		try {
			GameAPI.getAPI().getRabbitSpeaker().listen(listener.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
