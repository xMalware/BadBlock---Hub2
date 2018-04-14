package fr.badblock.bukkit.hub.v2.games.listeners;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.bukkit.hub.v2.games.type.VIPCourse;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.game.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VIPCourseListener extends BadListener
{
    HubGame game;

    public static Map<HubGame, BadblockPlayer> vipcourse = new HashMap<>();

    @EventHandler
    public void move(PlayerMoveEvent event)
    {
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        if(vipcourse.containsKey(player))
        {
        if(player.getLocation().getBlock().getType() == Material.WATER || player.getLocation().getBlock().getType() == Material.STATIONARY_WATER)
        {
            Location respawn = new Location(Bukkit.getServer().getWorld("world"), 20, 81, -54);
            player.sendMessage("tu es  tombé !");
            player.teleport(respawn);
        }
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent e)
    {
        BadblockPlayer p = (BadblockPlayer) e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if(e.getClickedBlock().getType().equals(Material.SPRUCE_FENCE_GATE)){
                p.sendMessage("test");
                    new VIPCourse();
                    vipcourse.put(new VIPCourse(), p);
                    game.addPlayer(p);
                    if(vipcourse.containsKey(p)){
                        vipcourse.remove(p);
                        p.sendMessage("enlevé");
                        game.remove(p);
                    }

            }

        }
    }
}
