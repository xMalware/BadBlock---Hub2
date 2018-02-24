package fr.badblock.bukkit.hub.v2.players;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.scoreboard.BadblockScoreboardGenerator;
import fr.badblock.gameapi.players.scoreboard.CustomObjective;

public class HubScoreboard extends BadblockScoreboardGenerator
{
	
	private BadblockPlayer player;
	private CustomObjective objective;
	
	public  HubScoreboard()
	{
		
		objective.showObjective(player);
		String hubNumber = GameAPI.getServerName();
		objective.setDisplayName("&b&o"+ lang("hub.scoreboard.name", 0));
		objective.setGenerator(this);
		objective.generate();
		doBadblockFooter(objective);
	}
	
	@Override
	public void generate() 
	{
		objective.changeLine(15, lang("hub.scoreboard.common", 0));
	}
	
	private String lang(String key, Object args)
	{
		return player.getTranslatedMessage(key, args)[0];
	}

}
