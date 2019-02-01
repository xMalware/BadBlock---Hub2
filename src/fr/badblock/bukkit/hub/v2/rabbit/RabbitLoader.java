package fr.badblock.bukkit.hub.v2.rabbit;

import fr.badblock.gameapi.GameAPI;

public class RabbitLoader
{

	/**
	 * Load rabbit listeners
	 */
	public static void load()
	{
		try
		{
			GameAPI.getAPI().getRabbitSpeaker().listen(new GetServersDataListener());
			GameAPI.getAPI().getRabbitSpeaker().listen(new SEntryInfosListener());
		}
		catch (Exception error)
		{
			error.printStackTrace();
		}
	}

}
