package fr.badblock.bukkit.hub.v2.tasks.list;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.players.addons.HubScoreboard;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;

public class ScoreboardUpdateTask extends HubTask
{
	
	public ScoreboardUpdateTask()
	{
		super (true, 20, 20);
	}

	@Override
	public void run()
	{
		update();
	}
	
	public static void update()
	{
		for (HubPlayer hubPlayer : HubPlayer.getPlayers())
		{
			HubScoreboard scoreboard = hubPlayer.getScoreboard();
			
			// Not online
			if (!hubPlayer.isOnline())
			{
				continue;
			}
			
			// WHERE ARE YOU?
			if (scoreboard == null)
			{
				continue;
			}
			
			scoreboard.generate();
		}
	}

}
