package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.yapzhenyie.GadgetsMenu.api.GadgetsMenuAPI;
import com.yapzhenyie.GadgetsMenu.player.PlayerManager;

import fr.badblock.gameapi.BadListener;

public class PlayerJoinListener extends BadListener
{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		removeDefaultJoinMessage(event);
		removeCustomGadgets(event);
	}

	private void removeCustomGadgets(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerManager playerManager = GadgetsMenuAPI.getPlayerManager(player);

		playerManager.removeGadget();
	}

	private void removeDefaultJoinMessage(PlayerJoinEvent event)
	{
		event.setJoinMessage(null);
	}
	
}
