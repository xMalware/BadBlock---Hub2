package fr.badblock.bukkit.hub.v2.players;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureType;
import fr.badblock.bukkit.hub.v2.cosmetics.features.OwnedFeature;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.data.GameData;
import lombok.Data;

@Data
public class HubStoredPlayer  implements GameData
{

	private boolean															hidePlayers;
	private boolean															hideParticles;
	private boolean															hideGameMessages;
	private boolean															hideHubChat;
	private boolean															modeSelected;
	private Map<FeatureType, List<OwnedFeature>>		features	= new HashMap<>();
	
	public void save(BadblockPlayer player)
	{
		if (features == null)
		{
			features = new HashMap<>();
		}
		
		player.getPlayerData().saveData();
		player.saveGameData();
	}
	
	public static HubStoredPlayer get(BadblockPlayer player)
	{
		HubStoredPlayer hubStoredPlayer = player.getPlayerData().gameData("hub", HubStoredPlayer.class);
		if (hubStoredPlayer.features == null)
		{
			hubStoredPlayer.features = new HashMap<>();
		}
		return hubStoredPlayer;
	}
	
}