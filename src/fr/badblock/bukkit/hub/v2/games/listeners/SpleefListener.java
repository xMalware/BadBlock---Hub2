package fr.badblock.bukkit.hub.v2.games.listeners;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.gameapi.BadListener;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class SpleefListener extends BadListener{

    HubGame game;
   public static HashMap<HubGame, BadblockPlayer> gamePlayer = new HashMap<>();

    //TODO: Get the defeath without moving
    @EventHandler
    public void move(PlayerMoveEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();
        if(event.getFrom().getBlock().equals(Material.WATER) && event.getTo().getBlock().equals(Material.WATER)){
            if(gamePlayer.containsKey(player)) {
                game.remove(player);
                player.sendTranslatedMessage("hub.game.spleef.loose");
                player.setGameMode(GameMode.ADVENTURE);
            }
        }
    }
}
