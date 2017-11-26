package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class InventoryCloseListener extends BadListener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player)) return;
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();
		HubPlayer hubPlayer = HubPlayer.get(player);
		hubPlayer.setInventory(null);
	}

}
