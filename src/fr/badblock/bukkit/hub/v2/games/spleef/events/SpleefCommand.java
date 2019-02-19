package fr.badblock.bukkit.hub.v2.games.spleef.events;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class SpleefCommand implements Listener {

    @EventHandler
    public void onExecute(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player)) {
            ArrayList<String> cmds = new ArrayList<>(Arrays.asList("/hub", "/lobby", "/spawn"));
            if (cmds.contains(event.getMessage())) {
                SpleefPlayer spleefPlayer = SpleefManager.getInstance().getSpleefPlayers().get(player);
                spleefPlayer.getCustomInv().restoreInventory(player);

                SpleefManager.getInstance().getSpleefPlayers().remove(player);
            }
        }

    }

}
