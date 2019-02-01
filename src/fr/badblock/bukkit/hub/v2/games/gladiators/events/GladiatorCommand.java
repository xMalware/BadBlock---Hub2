package fr.badblock.bukkit.hub.v2.games.gladiators.events;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class GladiatorCommand implements Listener {

    @EventHandler
    public void onExecute(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();

        if(GladiatorManager.getInstance().getCustomInv().containsKey(player)){
            ArrayList<String> cmds = new ArrayList<>(Arrays.asList("/hub","/lobby","/spawn"));
            if(cmds.contains(event.getMessage())){
                Map map = MapManager.get().getMaps().stream().filter(map1 -> map1.getPlayers().contains(player)).findAny().get();

                GladiatorManager.getInstance().getCustomInv().remove(player).restoreInventory(player);
                player.sendMessage(GladiatorManager.GLADIATOR_PREFIX+"§3Vous quittez le Gladiator !");
                player.setHealth(player.getMaxHealth());
                map.getPlayers().remove(player);

                for(Sign sign1 : map.getSignLocations()){
                    sign1.setLine(3, "§8"+map.getPlayers().size());
                    sign1.update();
                }
            }
        }

    }

}
