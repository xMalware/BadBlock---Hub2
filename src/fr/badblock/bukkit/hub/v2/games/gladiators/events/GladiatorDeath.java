package fr.badblock.bukkit.hub.v2.games.gladiators.events;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import fr.badblock.gameapi.events.fakedeaths.FightingDeathEvent;

public class GladiatorDeath implements Listener {

    @EventHandler
    public void onDeath(FightingDeathEvent event){
        Player player = event.getPlayer();

        event.setLightning(false);
        event.setTimeBeforeRespawn(0);
        event.setRespawnPlace(Bukkit.getWorlds().get(0).getSpawnLocation());

        if(MapManager.get().getMaps().stream().anyMatch(map -> map.getPlayers().contains(player))){
            Map map = MapManager.get().getMaps().stream().filter(map1 -> map1.getPlayers().contains(player)).findAny().get();

            GladiatorManager.getInstance().getCustomInv().remove(player).restoreInventory(player);
            player.getKiller().sendMessage(GladiatorManager.GLADIATOR_PREFIX+"§3Vous avez tué "+player.getName());
            player.sendMessage(GladiatorManager.GLADIATOR_PREFIX+"§3Vous avez été tué par "+player.getKiller().getName());
            player.getKiller().setHealth(player.getMaxHealth());
            map.getPlayers().remove(player);
            event.setDeathMessage(null);
            event.getDrops().clear();

            for(Sign sign1 : map.getSignLocations()){
                sign1.setLine(3, "§8"+map.getPlayers().size());
                sign1.update();
            }
        }

    }

}
