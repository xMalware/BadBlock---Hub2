package fr.badblock.bukkit.hub.v2.games.jump.events;

import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class JumpCommand implements Listener {

    @EventHandler
    public void onExecute(PlayerCommandPreprocessEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if(JumpManager.getInstance().getJumpPlayers().containsKey(player)){
            ArrayList<String> cmds = new ArrayList<>(Arrays.asList("/hub","/lobby","/spawn"));
            if(cmds.contains(event.getMessage())){
                JumpManager.getInstance().getJumpPlayers().remove(player);
                player.sendMessage(JumpManager.JUMP_PREFIX+"§cVous quittez le Jump");
            }
        }

    }

}
