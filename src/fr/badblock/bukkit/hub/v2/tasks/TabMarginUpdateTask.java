package fr.badblock.bukkit.hub.v2.tasks;

import java.util.Arrays;
import java.util.Iterator;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.tags.TagManager;
import fr.badblock.gameapi.players.BadblockPlayer;

public class TabMarginUpdateTask extends _HubTask
{
	
	public TabMarginUpdateTask()
	{
		super (true, 20 * 5, 20 * 5);
	}

	@Override
	public void run()
	{
		update();
	}
	
	private void update()
	{
		for (HubPlayer hubPlayer : HubPlayer.getPlayers())
		{
			// Not online
			if (!hubPlayer.isOnline())
			{
				continue;
			}
			
			BadblockPlayer player = hubPlayer.getPlayer();
			
			String header = toStringWithLines(player, "hub.players.tablist.header");
			String footer = toStringWithLines(player, "hub.players.tablist.footer");
			
			// Tagify
			TagManager tagManager = TagManager.getInstance();
			header = tagManager.tagify(player, header);
			footer = tagManager.tagify(player, footer);
			
			player.sendTabHeader(header, footer);
		}
	}
	
	private String toStringWithLines(BadblockPlayer player, String key)
	{
		String result = "";
		Iterator<String> iterator = Arrays.asList(player.getTranslatedMessage(key)).iterator();
		while (iterator.hasNext())
		{
			result += iterator.next() + (iterator.hasNext() ? System.lineSeparator() : "");
		}
		return result;
	}

}