package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

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
		if(event.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.CARPET) {
			player.setVelocity(player.getLocation().getDirection().multiply(20).add(new Vector(0.5, 3, 0.5)));
		}
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
