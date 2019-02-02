package fr.badblock.bukkit.hub.v2.games.gladiators.events;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GladiatorQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        GladiatorManager.getInstance().getCustomInv().remove(player);
        if(MapManager.get().getMaps().stream().anyMatch(map -> map.getPlayers().contains(player))){
            Map map = MapManager.get().getMaps().stream().filter(map1 -> map1.getPlayers().contains(player)).findAny().get();
            map.getPlayers().remove(player);

            for(Sign sign1 : map.getSignLocations()){
                sign1.setLine(3, "§8"+map.getPlayers().size());
                sign1.update();
            }
        }

    }

}
