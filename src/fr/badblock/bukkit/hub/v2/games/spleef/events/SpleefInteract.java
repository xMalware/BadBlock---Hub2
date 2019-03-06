package fr.badblock.bukkit.hub.v2.games.spleef.events;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpleefInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.DIAMOND_SPADE) {
                if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player) && SpleefManager.getInstance().getGameState() == GameState.INGAME) {
                    SpleefPlayer spleefPlayer = SpleefManager.getInstance().getSpleefPlayers().get(player);
                    if (!spleefPlayer.isDead())
                        player.launchProjectile(Snowball.class);
                }
            }
        }

    }

}
