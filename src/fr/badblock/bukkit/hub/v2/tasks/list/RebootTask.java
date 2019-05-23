package fr.badblock.bukkit.hub.v2.tasks.list;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.instances.hubs.Hub;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.game.GameState;
import fr.badblock.gameapi.utils.BukkitUtils;

public class RebootTask extends HubTask
{

	public static long	rebootTime;
	public static int reboot = 300;

	public RebootTask()
	{
		super (true, 20, 20);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR, 4);
		calendar.set(Calendar.MINUTE, 55);
		while (calendar.getTimeInMillis() < System.currentTimeMillis())
		{
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			calendar.set(Calendar.HOUR, 4);
			calendar.set(Calendar.MINUTE, 55);
		}
		rebootTime = calendar.getTimeInMillis();
	}

	@Override
	public void run()
	{
		if (ConfigLoader.getGameHub().isEnabled())
		{
			task.cancel();
			return;
		}
		
		if (System.currentTimeMillis() < rebootTime)
		{
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
			
			String lobbyName = "lobby";
			
			List<Hub> h = Hub.getHubs().stream().filter(hub -> hub.getHubName().equals(Bukkit.getServerName())).collect(Collectors.toList());
			
			if (h != null && !h.isEmpty())
			{
				Hub hub = h.get(new Random().nextInt(h.size()));
				
				if (hub != null)
				{
					lobbyName = hub.getHubName();
				}
			}
			
			final String lName = lobbyName;
			
			BukkitUtils.getAllPlayers().forEach(player -> player.sendPlayer(lName));
		}
		
		if (reboot == 60)
		{
			GameAPI.getAPI().setShouldBeRemovedFromDocker(true);
			GameAPI.getAPI().getGameServer().setGameState(GameState.STOPPING);
		}

		if (reboot > 0 && reboot <= 60 && (reboot % 10 == 0 || reboot <= 5))
		{
			BukkitUtils.getAllPlayers().forEach(player -> player.sendTranslatedMessage("hub.reboot", reboot, (reboot > 1 ? "s" : "")));
		}
	}

}