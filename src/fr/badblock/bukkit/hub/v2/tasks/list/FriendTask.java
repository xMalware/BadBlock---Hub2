package fr.badblock.bukkit.hub.v2.tasks.list;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;

public class FriendTask extends HubTask
{

	public FriendTask()
	{
		super (false, 20 * 5, 20 * 5);
	}

	@Override
	public void run()
	{
		synchronize();
	}

	public static void synchronize()
	{
		for (HubPlayer pl : HubPlayer.getPlayers())
		{
			pl.updateFriends();
		}
	}

}