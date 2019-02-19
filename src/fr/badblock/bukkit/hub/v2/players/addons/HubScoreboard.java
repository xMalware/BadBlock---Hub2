package fr.badblock.bukkit.hub.v2.players.addons;

import java.util.Map.Entry;

import fr.badblock.api.common.utils.general.MathUtils;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
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
		objective.setDisplayName("&b&o" + TagManager.getInstance().tagify(player, lang("hub.scoreboard.name", 0)[0]));
		objective.setGenerator(this);
		objective.generate();
	}

	@Override
	public void generate() 
	{
		int i = 15;
		for (String line : lang("hub.scoreboard.lore"))
		{		
			String l = tagManager.tagify(player, line);
			
			if (ConfigLoader.getGameHub().isEnabled())
			{
				for (Entry<String, String> entry : ConfigLoader.getGameHub().getStats().entrySet())
				{
					double d = MathUtils.round(player.getPlayerData().getStatistics(ConfigLoader.getGameHub().getInternalGameName(), entry.getValue()), 2);
					String replace = "";
					
					if (isInteger(d))
					{
						replace = "" + (int) d;
					}
					else
					{
						replace = "" + d;
					}
					
					l = l.replace(entry.getKey(), replace);
				}
			}
			
			objective.changeLine(i, l);
			i--;
		}
	}

	private String[] lang(String key, Object... object)
	{
		return player.getTranslatedMessage(key, object);
	}
	
	private final static double epsilon = 1E-10;
	
	public static boolean isInteger(final double d)
	{
		return Math.abs(Math.floor(d) - d) < epsilon;
	}
	
}