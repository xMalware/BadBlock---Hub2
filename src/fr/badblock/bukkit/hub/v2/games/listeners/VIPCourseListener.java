package fr.badblock.bukkit.hub.v2.games.listeners;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class VIPCourseListener extends BadListener
{
    public static HashMap<HubGame, BadblockPlayer> vipcourse = new HashMap<>();

    @EventHandler
    public void move(PlayerMoveEvent event)
    {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        if(vipcourse.containsKey(player))
        {
            if(event.getTo().getBlock().equals(Material.WATER) || event.getFrom().getBlock().equals(Material.WATER))
            {
                //TODO: Get the location of the water

            }
        }
    }
}
