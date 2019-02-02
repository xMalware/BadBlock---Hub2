package fr.badblock.bukkit.hub.v2.tasks.list;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.badblock.api.common.utils.permissions.PermissionsManager;
import fr.badblock.bukkit.hub.v2.instances.hubs.HubAliveFactory;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubUpdateTask extends HubTask
{

	public static int		hubId;
	
	private int 		id;

	public HubUpdateTask()
	{
		super (false, 20, 20);
	}

	@Override
	public void run()
	{
		sendPacket();
	}

	public void sendPacket()
	{
		Map<String, Integer> ranks = new HashMap<>();
		id = 0;
		Map<String, String> order = new HashMap<>();
		PermissionsManager.getManager().getGroups().stream().sorted((a, b) -> { return Integer.compare(b.getPower(), a.getPower()); }).forEach(group -> {
			String d = generateForId(id) + "";
			order.put(group.getName(), d);
			id++;
		});
		
		for (Player player : Bukkit.getOnlinePlayers())
		{
			BadblockPlayer bPlayer = (BadblockPlayer) player;
			
			if 	(bPlayer.getGameMode().equals(GameMode.SPECTATOR))
			{
				continue;
			}
			
			if (!ranks.containsKey(order.get(bPlayer.getMainGroup())))
			{
				ranks.put(order.get(bPlayer.getMainGroup()), 1);
			}
			else
			{
				ranks.put(order.get(bPlayer.getMainGroup()), ranks.get(order.get(bPlayer.getMainGroup())) + 1);
			}
		}
		
		int playersWorker = Bukkit.getOnlinePlayers().size();
		
		HubAliveFactory hubAliveFactory = new HubAliveFactory(Bukkit.getServerName(), playersWorker, Bukkit.getMaxPlayers(), true, ranks);
		String content = GameAPI.getGson().toJson(hubAliveFactory);
		GameAPI.getAPI().getRabbitSpeaker().sendAsyncUTF8Publisher("hub", content, 5000, false);
		
		hubId = hubAliveFactory.getId();
	}
	
	private char generateForId(int id)
	{
		int A = 'A';

		if(id > 26)
		{
			A   = 'a';
			id -= 26;

			return (char) (A + id);
		}
		else
		{
			return (char) (A + id);
		}
	}

}