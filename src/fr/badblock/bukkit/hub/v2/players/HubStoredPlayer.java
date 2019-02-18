package fr.badblock.bukkit.hub.v2.players;

import java.util.ArrayList;
import java.util.List;

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
	private List<String>														features	= new ArrayList<>();
	
	public void save(BadblockPlayer player)
	{
		if (features == null)
		{
			features = new ArrayList<>();
		}
		
		player.getPlayerData().saveData();
		player.saveGameData();
	}
	
	public static HubStoredPlayer get(BadblockPlayer player)
	{
		HubStoredPlayer hubStoredPlayer = player.getPlayerData().gameData("lobby2", HubStoredPlayer.class);
		if (hubStoredPlayer.features == null)
		{
			hubStoredPlayer.features = new ArrayList<>();
		}
		return hubStoredPlayer;
	}
	
}