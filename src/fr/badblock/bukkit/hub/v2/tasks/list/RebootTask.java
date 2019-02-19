package fr.badblock.bukkit.hub.v2.tasks.list;

import java.util.Random;

import org.bukkit.Bukkit;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.utils.BukkitUtils;

public class RebootTask extends HubTask
{

	public static int	reboot;

	public RebootTask()
	{
		super (true, 20, 20);
		reboot = 5400 + new Random().nextInt(1200);
	}

	@Override
	public void run()
	{
		if (ConfigLoader.getGameHub().isEnabled())
		{
			task.cancel();
			return;
		}
		
		reboot--;

		if (reboot <= 0)
		{
			if (reboot < -10)
			{
				Bukkit.shutdown();
				return;
			}
			
			BukkitUtils.getAllPlayers().forEach(player -> player.sendPlayer("lobby"));
		}
		
		if (reboot == 60)
		{
			GameAPI.setJoinable(false);
		}

		if (reboot <= 60 && (reboot % 10 == 0 || reboot <= 5))
		{
			BukkitUtils.getAllPlayers().forEach(player -> player.sendTranslatedMessage("hub.reboot", reboot, (reboot > 1 ? "s" : "")));
		}
	}

}