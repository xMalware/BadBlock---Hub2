package fr.badblock.bukkit.hub.v2.games.listeners;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class SpleefListener extends BadListener{

    HubGame game;
    ArrayList<HubGame> gamePlayer = new ArrayList<>();

    @EventHandler
    public void falling(EntityDamageEvent event){
        if(event.getEntity() instanceof BadblockPlayer){
            BadblockPlayer player = (BadblockPlayer) event.getEntity();
            if(event.getCause() == EntityDamageEvent.DamageCause.LAVA){
                if(gamePlayer.contains(player)) {
                    game.remove(player);
                    player.sendTranslatedMessage("hub.game.spleef.loose");
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }
        }
    }
}
