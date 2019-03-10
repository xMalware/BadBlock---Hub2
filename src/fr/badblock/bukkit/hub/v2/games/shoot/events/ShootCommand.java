package fr.badblock.bukkit.hub.v2.games.shoot.events;

import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.Box;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.ShootPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ShootCommand implements Listener {

    @EventHandler
    public void onExecute(PlayerCommandPreprocessEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        if(ShootManager.getInstance().getShootPlayers().containsKey(player)){
            ArrayList<String> cmds = new ArrayList<>(Arrays.asList("/hub", "/lobby", "/spawn"));
            if (cmds.contains(event.getMessage().toLowerCase())) {
                player.sendMessage(ShootManager.SHOOT_PREFIX+"§cVous quittez le Tir à L'Arc");
                removePlayerFromShoot(player);
            }
        }

    }

    static void removePlayerFromShoot(BadblockPlayer player) {
        ShootPlayer shootPlayer = ShootManager.getInstance().getShootPlayers().get(player);
        if(shootPlayer != null){
            Box box = shootPlayer.getBox();
            if(box != null && box.isTaken())
                box.setTaken(false);
        }

        ShootManager.getInstance().getShootPlayers().remove(player);

        if(ShootManager.getInstance().getShootPlayers().size() <= 1 && ShootManager.getInstance().getGameState() == GameState.INGAME){
            ShootManager.getInstance().getShootPlayers().forEach((p, shootPlayer1) -> {
                p.sendMessage(ShootManager.SHOOT_PREFIX + "§cVous gagnez la partie !");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, 1);
                shootPlayer1.getCustomPlayerInventory().restoreInventory(p);
                p.performCommand("spawn");
            });

            ShootManager.getInstance().getBoxes().forEach(box1 -> box1.setTaken(false));

            ShootManager.getInstance().getShootPlayers().clear();
            ShootManager.getInstance().getBoxes().forEach(Box::restoreBlocks);
            ShootManager.getInstance().setGameState(GameState.WAITING);
        }
    }

}
