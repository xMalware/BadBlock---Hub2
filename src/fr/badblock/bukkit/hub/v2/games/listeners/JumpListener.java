package fr.badblock.bukkit.hub.v2.games.listeners;

import java.util.Map.Entry;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.JumpLocationsConfig;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;

public class JumpListener extends BadListener
{

	private JumpLocationsConfig config = ConfigLoader.getJump();

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event)
	{
		BadblockPlayer player = (BadblockPlayer) event.getPlayer();
		
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (!hubPlayer.isJump())
		{
			return;
		}
		
		if (hubPlayer.isJumpBeingTeleported())
		{
			hubPlayer.setJumpBeingTeleported(false);
			return;
		}

		player.setWalkSpeed(0.4F);
		hubPlayer.setJump(false);
		hubPlayer.setJumpBeingTeleported(false);
		hubPlayer.setJumpCheckpoint(0);
	}
	
	@EventHandler
	public void onMove (PlayerMoveEvent e)
	{
		BadblockPlayer bpj = (BadblockPlayer) e.getPlayer();

		HubPlayer hubPlayer = HubPlayer.get(bpj);

		if (!hubPlayer.isJump())
		{
			return;
		}

		// fall
		if (bpj.getFallDistance() >= 4)
		{
			int checkpoint = hubPlayer.getJumpCheckpoint();
			hubPlayer.setJumpBeingTeleported(true);
			
			if (checkpoint == 0)
			{
				bpj.teleport(config.getStart());
				hubPlayer.setJump(false);
				bpj.setWalkSpeed(0.4F);
			}
			else
			{
				bpj.teleport(config.getCheckpoints().get(checkpoint));
			}
			
			bpj.sendTranslatedMessage("hub.game.jump.tpcheckpoint");
		}

	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		BadblockPlayer bp = (BadblockPlayer) e.getPlayer();

		HubPlayer hubPlayer = HubPlayer.get(bp);

		Block block = e.getClickedBlock();

		if (block == null)
		{
			return;
		}

		if (block.getType() != Material.GOLD_PLATE && block.getRelative(BlockFace.DOWN).getType() != Material.STONE)
		{
			return;
		}

		if (!block.getWorld().equals(config.getStart().getWorld()))
		{
			return;
		}

		if (!hubPlayer.isJump())
		{
			if (config.getStart().distance(block.getLocation()) > 1)
			{
				return;
			}

			hubPlayer.setJump(true);
			hubPlayer.setJumpCheckpoint(0);
			bp.setWalkSpeed(0.2F);
			bp.setFlying(false);
			bp.setAllowFlight(false);
			bp.sendTranslatedMessage("hub.game.jump.started");
			return;
		}

		if (config.getEnd().distance(block.getLocation()) < 1)
		{
			// end
			hubPlayer.setJump(false);
			hubPlayer.setJumpCheckpoint(0);
			bp.sendTranslatedMessage("hub.game.jump.end");
			// Reset
			bp.setWalkSpeed(0.4F);
			// TODO
			return;
		}

		Optional<Entry<Integer, Location>> optional = config.getCheckpoints().entrySet().parallelStream().filter(entry -> block.getLocation().distance(entry.getValue()) < 1).findFirst();

		if (optional == null || !optional.isPresent())
		{
			return;
		}

		int checkpointId = optional.get().getKey();
		hubPlayer.setJumpCheckpoint(checkpointId);
		bp.sendTranslatedMessage("hub.game.jump.registercheckpoint");	
	}

}