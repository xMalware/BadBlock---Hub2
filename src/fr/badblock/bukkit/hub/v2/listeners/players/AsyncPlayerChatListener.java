package fr.badblock.bukkit.hub.v2.listeners.players;

import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class AsyncPlayerChatListener extends BadListener
{

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		BadblockPlayer pl = (BadblockPlayer) event.getPlayer();
		HubStoredPlayer hubPlayer = HubStoredPlayer.get(pl);

		if (hubPlayer.isHideHubChat())
		{
			event.setCancelled(true);
			pl.sendTranslatedMessage("hub.togglehubchat.cantchat");
			return;
		}

		if (event.getPlayer().hasPermission("hub.bypassdisabledchat"))
		{
			return;
		}

		Iterator<Player> iterator = event.getRecipients().iterator();
		while (iterator.hasNext())
		{
			BadblockPlayer player = (BadblockPlayer) iterator.next();
			HubStoredPlayer hubP = HubStoredPlayer.get(player);
			if (hubP.isHideHubChat())
			{
				iterator.remove();
			}
		}
	}

}
