package fr.badblock.bukkit.hub.v2.games.gladiators.events;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.badblock.bukkit.hub.v2.games.gladiators.GladiatorManager;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.Map;
import fr.badblock.bukkit.hub.v2.games.gladiators.maps.MapManager;
import fr.badblock.gameapi.events.fakedeaths.FightingDeathEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class GladiatorDeath implements Listener {

    @EventHandler
    public void onDeath(FightingDeathEvent event){
        Player player = event.getPlayer();
        event.setCancelled(true);

        if(MapManager.get().getMaps().stream().anyMatch(map -> map.getPlayers().contains(player))){
            Map map = MapManager.get().getMaps().stream().filter(map1 -> map1.getPlayers().contains(player)).findAny().get();

            GladiatorManager.getInstance().getCustomInv().remove(player).restoreInventory(player);
            player.getKiller().sendMessage(GladiatorManager.GLADIATOR_PREFIX+"§3Vous avez tué "+player.getName());
            player.sendMessage(GladiatorManager.GLADIATOR_PREFIX+"§3Vous avez été tué par "+player.getKiller().getName());
            TextComponent tc = new TextComponent(GladiatorManager.GLADIATOR_PREFIX + "§cClique ici pour jouer à nouveau");
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playgladiator"));
            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cClique ici !").create()));
            player.getKiller().setHealth(player.getMaxHealth());
            map.getPlayers().remove(player);
            event.setDeathMessage(null);
            event.getDrops().clear();
            event.setLightning(false);
            event.setTimeBeforeRespawn(0);

            for(Sign sign1 : map.getSignLocations()){
                sign1.setLine(3, "§8"+map.getPlayers().size());
                sign1.update();
            }
        }

    }

}
