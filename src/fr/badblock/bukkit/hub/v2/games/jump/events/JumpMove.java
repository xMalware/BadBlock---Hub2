package fr.badblock.bukkit.hub.v2.games.jump.events;

import fr.badblock.bukkit.hub.v2.utils.FeatureUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.badblock.bukkit.hub.v2.games.jump.JumpManager;
import fr.badblock.bukkit.hub.v2.games.jump.objects.JumpPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

/**
 * Created by Toinetoine1 on 16/01/2019.
 */
public class JumpMove implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        BadblockPlayer player = (BadblockPlayer) e.getPlayer();

        if (JumpManager.getInstance().getJumpPlayers().containsKey(player)) {
            JumpPlayer jumpPlayer = JumpManager.getInstance().getJumpPlayers().get(player);

            if (e.getTo().getBlockY() < 70) {
                jumpPlayer.removeLife();
                if(jumpPlayer.getLife() == 0){
                    player.sendMessage(JumpManager.JUMP_PREFIX + "Vous n'avez plus de vie ! Retente ta chance ;)");
                    player.teleport(JumpManager.getInstance().getTeleportPoint());
                    if(player.hasPermission("hub.fly"))
                        player.setAllowFlight(true);
                    JumpManager.getInstance().getJumpPlayers().remove(player);
                } else {
                    player.sendMessage(JumpManager.JUMP_PREFIX + "Vous êtes tombé ! Il vous reste §c"+jumpPlayer.getLife()+" "+(jumpPlayer.getLife() == 1 ? "vie" : "vies")+" !");
                    player.teleport(JumpManager.getInstance().getCheckpoint().get(jumpPlayer.getCheckpoint()));
                }
            } else if (JumpManager.getInstance().getCheckpoint().size() == jumpPlayer.getCheckpoint()) {
                Bukkit.broadcastMessage(JumpManager.JUMP_PREFIX + "§cBravo à " + player.getName() + " qui a réussi le Jump !");
                JumpManager.getInstance().getJumpPlayers().remove(player);
                if(player.hasPermission("hub.fly"))
                    player.setAllowFlight(true);
            } else if (player.getLocation().distance(JumpManager.getInstance().getCheckpoint().get(jumpPlayer.getCheckpoint() + 1)) <= 1 && !JumpManager.getInstance().getCheckpoint().isEmpty()) {
                jumpPlayer.setCheckpoint(jumpPlayer.getCheckpoint() + 1);
                jumpPlayer.getBukkitPlayer().sendMessage(JumpManager.JUMP_PREFIX + "Bravo, vous avez passé le checkpoint n°" + jumpPlayer.getCheckpoint());
            } else if(JumpManager.getInstance().getCheckpoint().stream()
                    .filter(location -> {
                        int current = JumpManager.getInstance().getCheckpoint().indexOf(JumpManager.getInstance().getCheckpoint().get(jumpPlayer.getCheckpoint()));
                        int loop = JumpManager.getInstance().getCheckpoint().indexOf(location);
                        return current < loop;
                    })
                    .anyMatch(location -> player.getLocation().distance(location) <= 1)){
                player.sendMessage(JumpManager.JUMP_PREFIX+"Vous avez oublié un checkpoint !");
                player.teleport(JumpManager.getInstance().getCheckpoint().get(jumpPlayer.getCheckpoint()));
            }

        } else {
            if (!JumpManager.getInstance().getCheckpoint().isEmpty() && player.getLocation().distance(JumpManager.getInstance().getCheckpoint().get(0)) <= 1) {
                JumpPlayer j = new JumpPlayer(player, 0, 3);
                j.getBukkitPlayer().sendMessage(JumpManager.JUMP_PREFIX + "Vous commencez le Jump ! Vous avez §c3 vies");
                player.setFlying(false);
                player.setAllowFlight(false);
                FeatureUtils.removeAllFeatures(player);
                JumpManager.getInstance().getJumpPlayers().put(player, j);
            }

        }
    }


}
