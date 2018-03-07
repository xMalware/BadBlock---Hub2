package fr.badblock.bukkit.hub.v2.players.addons;

import fr.badblock.bukkit.hub.v2.tags.TagManager;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.scoreboard.BadblockScoreboardGenerator;
import fr.badblock.gameapi.players.scoreboard.CustomObjective;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode (callSuper = false)
@Data
public class HubScoreboard extends BadblockScoreboardGenerator
{

	private static TagManager tagManager = TagManager.getInstance();
	
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
	}

	@Override
	public void generate() 
	{
		int i = 15;
		for (String line : lang("hub.scoreboard.lore"))
		{		
			objective.changeLine(i, tagManager.tagify(player, line));
			i--;
		}
	}

	private String[] lang(String key, Object... object)
	{
		return player.getTranslatedMessage(key, object);
	}

}
