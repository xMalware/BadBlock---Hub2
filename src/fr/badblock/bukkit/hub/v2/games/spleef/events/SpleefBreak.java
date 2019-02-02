package fr.badblock.bukkit.hub.v2.games.spleef.events;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toinetoine1 on 17/01/2019.
 */
public class SpleefBreak implements Listener
{

    private static List<Location> blocks = new ArrayList<>();

    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block b = event.getBlock();

        if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player))
        {
            event.setCancelled(true);
            if (GameState.INGAME.equals(SpleefManager.getInstance().getGameState()))
            {
                blocks.add(b.getLocation());
                b.setType(Material.AIR);
            }
        }

    }

    public static void restoreBlocks()
    {
        blocks.forEach(block -> block.getWorld().getBlockAt(block).setType(Material.SNOW_BLOCK));
    }

}