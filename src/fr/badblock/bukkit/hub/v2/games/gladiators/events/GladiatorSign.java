package fr.badblock.bukkit.hub.v2.games.gladiators.events;

import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class GladiatorSign implements Listener {

    @EventHandler
    public void onChangeSign(SignChangeEvent event){
        Player player = event.getPlayer();

        if(event.getLine(0).equals("[Gladiators]") && MapManager.get().getMaps().stream().anyMatch(map -> map.getName().equalsIgnoreCase(event.getLine(1)))
                && player.hasPermission("lobbygames.bypass")){
            Map map = MapManager.get().getMap(event.getLine(1));

            event.setLine(0, "§c[Gladiators]");
            event.setLine(1, "§3"+map.getName());
            event.setLine(2, "§7§lJoueurs:");
            event.setLine(3, "§8"+map.getPlayers().size());
        }

    }

}
