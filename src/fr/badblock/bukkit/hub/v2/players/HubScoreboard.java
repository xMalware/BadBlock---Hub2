package fr.badblock.bukkit.hub.v2.players;

import java.util.UUID;

import fr.badblock.bukkit.hub.v2.inventories.tags.InventoryTags;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.scoreboard.BadblockScoreboardGenerator;
import fr.badblock.gameapi.players.scoreboard.CustomObjective;
import fr.badblock.gameapi.utils.threading.TaskManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode (callSuper = false)
@Data
public class HubScoreboard extends BadblockScoreboardGenerator
{

	private BadblockPlayer player;
	private CustomObjective objective;

	@SuppressWarnings("unused")
	public HubScoreboard(BadblockPlayer player)
	{
		setPlayer(player);
		this.objective = GameAPI.getAPI().buildCustomObjective("hub");
		objective.showObjective(player);
		String hubNumber = GameAPI.getServerName();
		objective.setDisplayName("&b&o" + lang("hub.scoreboard.name", 0)[0]);
		objective.setGenerator(this);
		objective.generate();
		doBadblockFooter(objective);

		String rawTask = "scoreboard." + player.getName() + "." + UUID.randomUUID().toString();
		TaskManager.scheduleSyncRepeatingTask(rawTask, new Runnable()
		{
			@Override
			public void run()
			{
				if (!player.isOnline())
				{
					TaskManager.cancelTaskByName(rawTask);
					return;
				}
				generate();
			}
		}, 20, 20 * 10);
	}

	@Override
	public void generate() 
	{
		int i = 15;
		for (String line : lang("hub.scoreboard.lore"))
		{
			for (InventoryTags inventoryTag : InventoryTags.values())
			{
				for (String tag : inventoryTag.getTags())
				{
					if (line.contains(tag))
					{
						line = inventoryTag.replace(player, line, tag);
					}
				}
			}
			objective.changeLine(i, line);
			i--;
		}
	}

	private String[] lang(String key, Object... object)
	{
		return player.getTranslatedMessage(key, object);
	}

}
