package fr.badblock.bukkit.hub.v2.tasks.list;

import java.util.ArrayList;
import java.util.List;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.ActionBarsConfig;
import fr.badblock.bukkit.hub.v2.players.addons.ActionBar;
import fr.badblock.bukkit.hub.v2.tags.TagManager;
import fr.badblock.bukkit.hub.v2.tasks.HubTask;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.BukkitUtils;

public class ActionBarsUpdateTask extends HubTask
{

	private int 		id;
	private int			ticks;
	private long		lastUpdate;

	public ActionBarsUpdateTask()
	{
		super (true, 1, 1);
	}

	@Override
	public void run()
	{
		update();
	}

	private void update()
	{
		ActionBarsConfig actionBarsConfig = ConfigLoader.getActionBars();
		
		List<ActionBar> actionBars = new ArrayList<>(actionBarsConfig.getActionBars().values());
		ActionBar actionBar = actionBars.get(id);

		boolean updated = false;

		if (actionBar != null)
		{
			if (ticks >= actionBar.getTicks())
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

		if (id + 1 > actionBars.size())
		{
			id = 0;
			updated = true;
			ticks = 0;
		}

		actionBar = actionBars.get(id);

		show(actionBar, updated);
	}

	private void show(ActionBar actionBar, boolean updated)
	{
		long time = System.currentTimeMillis();

		if (lastUpdate > time && !updated)
		{
			return;
		}

		lastUpdate = time + 500;

		for (BadblockPlayer player : BukkitUtils.getAllPlayers())
		{
			String line = player.getTranslatedMessage(actionBar.getI18nKey())[0];
			
			// Tagify
			TagManager tagManager = TagManager.getInstance();
			line = tagManager.tagify(player, line);
			
			player.sendActionBar(line);
		}
	}

}