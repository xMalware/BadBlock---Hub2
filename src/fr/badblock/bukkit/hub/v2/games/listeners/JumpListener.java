package fr.badblock.bukkit.hub.v2.games.listeners;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class JumpListener extends BadListener
{
	
	HubGame game;
	public static HashMap<HubGame, BadblockPlayer> gamePlayer = new HashMap<>();

	
	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onMove (PlayerMoveEvent e)
	{
		BadblockPlayer bpj = (BadblockPlayer) e.getPlayer();
		if(bpj.getLocation().getBlockY() >= 2) 
		{
			if(gamePlayer.containsKey(bpj)) 
			{
                bpj.sendTranslatedMessage("hub.game.jump.fell.back.last.checkpoint");
            }
		}
		
	
	}
	@EventHandler
    public void onInteract(PlayerInteractEvent e)
	{
		BadblockPlayer bp = (BadblockPlayer) e.getPlayer();
        if(e.getClickedBlock().getType() == Material.GOLD_PLATE && e.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.STONE)
        {
        	e.getAction();
        	bp.sendTranslatedMessage("hub.game.jump.register.checkpoint");
        	
        }
	}
}
		
