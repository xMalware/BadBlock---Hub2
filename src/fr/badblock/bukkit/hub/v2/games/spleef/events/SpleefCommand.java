package fr.badblock.bukkit.hub.v2.games.spleef.events;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Toinetoine1 on 17/01/2019.
 */
public class SpleefCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();

        if (!SpleefManager.getInstance().getSpleefPlayers().containsKey(p)) {
            return;
        }

        if (GameMode.CREATIVE.equals(p.getGameMode()) || p.hasPermission("lobbygames.bypass")) {
            return;
        }

        event.setCancelled(true);
        p.sendMessage(SpleefManager.SPLEEF_PREFIX + "§cTu ne peux pas exécuter de commande en jeu.");
    }

}
