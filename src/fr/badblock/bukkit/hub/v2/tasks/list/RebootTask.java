package fr.badblock.bukkit.hub.v2.tasks.list;

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