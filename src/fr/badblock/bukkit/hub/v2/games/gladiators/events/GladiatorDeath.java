package fr.badblock.bukkit.hub.v2.games.gladiators.events;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GladiatorDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();

        if(MapManager.get().getMaps().stream().anyMatch(map -> map.getPlayers().contains(player))){
            Map map = MapManager.get().getMaps().stream().filter(map1 -> map1.getPlayers().contains(player)).findAny().get();

            GladiatorManager.getInstance().getCustomInv().remove(player).restoreInventory(player);
            player.getKiller().sendMessage(GladiatorManager.GLADIATOR_PREFIX+"§3Vous avez tué "+player.getName());
            player.sendMessage(GladiatorManager.GLADIATOR_PREFIX+"§3Vous avez été tué par "+player.getKiller().getName());
            player.getKiller().setHealth(player.getMaxHealth());
            map.getPlayers().remove(player);
            player.spigot().respawn();
            event.setDeathMessage(null);
            event.getDrops().clear();

            for(Sign sign1 : map.getSignLocations()){
                sign1.setLine(3, "§8"+map.getPlayers().size());
                sign1.update();
            }
        }

    }

}
