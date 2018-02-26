package fr.badblock.bukkit.hub.v2.players;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.scoreboard.BadblockScoreboardGenerator;
import fr.badblock.gameapi.players.scoreboard.CustomObjective;

public class HubScoreboard extends BadblockScoreboardGenerator
{
	
	private BadblockPlayer player;
	private CustomObjective objective;
	
	@SuppressWarnings("unused")
	public  HubScoreboard()
	{
		objective.showObjective(player);
		String hubNumber = GameAPI.getServerName();
		objective.setDisplayName("&b&o" + lang("hub.scoreboard.name", 0));
		objective.setGenerator(this);
		objective.generate();
		doBadblockFooter(objective);
		generate();
	}
	
	@Override
	public void generate() 
	{
		double calc = (double) player.getPlayerData().getXp() / (double) player.getPlayerData().getXpUntilNextLevel();
		Double.toString((double) (calc * 100.0D));
		objective.changeLine(15, lang("hub.scoreboard.common", null));
		objective.changeLine(14, lang("hub.scoreboard.badcoins", player.getPlayerData().getBadcoins()));
		objective.changeLine(13, lang("hub.scoreboard.shoppoints", player.getShopPoints()));
		objective.changeLine(12, lang("hub.scoreboard.level", player.getPlayerData().getLevel()));
		objective.changeLine(11, lang("hub.scoreboard.rank", player.getGroupPrefix()));
		objective.changeLine(10, lang("hub.scoreboard.players", HubPlayer.getPlayers().size()));
	}
	
	private String lang(String key, Object args)
	{
		return player.getTranslatedMessage(key, args)[0];
	}

}
