package fr.badblock.bukkit.hub.v2.players;

import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.data.GameData;

public class HubStoredPlayer  implements GameData {
	
	private void loadData() {
		
	}
	
	public static HubStoredPlayer get(BadblockPlayer player) {
		HubStoredPlayer hubStoredPlayer = player.getPlayerData().gameData("hub", HubStoredPlayer.class);
		hubStoredPlayer.loadData();
		return hubStoredPlayer;
	}
	
}