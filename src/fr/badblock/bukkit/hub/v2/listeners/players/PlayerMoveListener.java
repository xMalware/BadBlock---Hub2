package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.CustomDisguiseEffect;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerMoveListener extends BadListener
{
	
	@EventHandler
	public void whenPlayerMoved(PlayerMoveEvent event)
	{
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();
		HubPlayer hubPlayer = HubPlayer.get(player);
		workWithDisguiseEffects(player, hubPlayer);
	}
	
	private void workWithDisguiseEffects(BadblockPlayer player, HubPlayer hubPlayer)
	{
		// Not disguised
		if (!player.isDisguised())
		{
			return;
		}
		// Null custom disguise
		if (hubPlayer.getDisguise() == null)
		{
			return;
		}
		CustomDisguiseEffect customDisguiseEffect = hubPlayer.getDisguise().getEffect();
		// No effect, we don't do anything more about it
		if (customDisguiseEffect == null)
		{
			return;
		}
		//player.playEffect(player.getLocation(), customDisguiseEffect.build(), 0);
	}

}
