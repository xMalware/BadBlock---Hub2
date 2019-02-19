package fr.badblock.bukkit.hub.v2.games.shoot.events;

import fr.badblock.bukkit.hub.v2.games.shoot.ShootManager;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.Box;
import fr.badblock.bukkit.hub.v2.games.shoot.utils.ShootPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ShootQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        BadblockPlayer player = (BadblockPlayer) event.getPlayer();

        ShootPlayer shootPlayer = ShootManager.getInstance().getShootPlayers().get(player);
        if(shootPlayer != null){
            Box box = shootPlayer.getBox();
            if(box != null && box.isTaken())
                box.setTaken(false);
        }

        if(ShootManager.getInstance().getShootPlayers().size() == 1){
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
