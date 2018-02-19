package fr.badblock.bukkit.hub.v2.listeners.players;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises.CustomDisguiseEffect;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class PlayerMoveListener extends BadListener
{
	

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void whenPlayerMoved(PlayerMoveEvent event)
	{	
		
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();
		HubPlayer hubPlayer = HubPlayer.get(player);
		Location loc1 = event.getTo();
		Location loc2 = loc1.clone().add(0, -1, 0);
		Location loc3 = loc1.clone().add(0, -2, 0);
		Block block1 = loc2.getBlock();
		Block block2 = loc3.getBlock();
		workWithDisguiseEffects(player, hubPlayer);
		if((block1.getType().equals(Material.WOOL) && block1.getData() == 10)
				|| (block2.getType().equals(Material.WOOL) && block2.getData() == 10)) 
		{
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
