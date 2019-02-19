package fr.badblock.bukkit.hub.v2.tasks;

import java.util.ArrayList;
import java.util.List;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.JoinTitleConfig;
import fr.badblock.bukkit.hub.v2.players.addons.TempTitle;
import fr.badblock.gameapi.players.BadblockPlayer;

public class JoinTitleUpdateTask extends HubTask
{

	private BadblockPlayer	player;
	private int 		id;
	private int			ticks;
	private long		lastUpdate;

	public JoinTitleUpdateTask(BadblockPlayer player)
	{
		super (true, 1, 1);
		this.player = player;
	}

	@Override
	public void run()
	{
		if (!player.isOnline())
		{
			task.cancel();
			return;
		}

		update();
	}

	private void update()
	{
		JoinTitleConfig joinTitleConfig = ConfigLoader.getJoinTitle();

		List<TempTitle> tempTitles = new ArrayList<>(joinTitleConfig.getTitles().values());
		TempTitle tempTitle = tempTitles.get(id);

		boolean updated = false;

		if (tempTitle != null)
		{
			if (ticks >= tempTitle.getTicks())
			{
				id++;
				ticks = 0;
				updated = true;
			}
			else
			{
				ticks++;
			}
		}

		if (id + 1 > tempTitles.size())
		{
			task.cancel();
			return;
		}

		tempTitle = tempTitles.get(id);

		show(tempTitle, updated);
	}

	@SuppressWarnings("deprecation")
	private void show(TempTitle tempTitle, boolean updated)
	{
		long time = System.currentTimeMillis();

		if (lastUpdate > time && !updated)
		{
			return;
		}

		lastUpdate = time + 500;

		player.sendTitle(tempTitle.getTitle(), tempTitle.getSubTitle());

		if (id + 1 > new ArrayList<>(ConfigLoader.getJoinTitle().getTitles().values()).size())
		{
			player.setTitleTimes(0, tempTitle.getTicks(), 0);
			return;
		}

		player.setTitleTimes(0, tempTitle.getTicks() * 5, 0);
	}

}