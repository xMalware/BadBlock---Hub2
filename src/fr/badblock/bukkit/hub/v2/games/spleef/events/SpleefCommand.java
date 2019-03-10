package fr.badblock.bukkit.hub.v2.games.spleef.events;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpleefCommand implements Listener {

    @EventHandler
    public void onExecute(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (SpleefManager.getInstance().getSpleefPlayers().containsKey(player)) {
            ArrayList<String> cmds = new ArrayList<>(Arrays.asList("/hub", "/lobby", "/spawn"));
            if (cmds.contains(event.getMessage().toLowerCase())) {
                player.sendMessage(SpleefManager.SPLEEF_PREFIX+"Vous avez quitté le Spleef");
                player.setGameMode(GameMode.ADVENTURE);
                SpleefPlayer spleefPlayer = SpleefManager.getInstance().getSpleefPlayers().get(player);
                spleefPlayer.getCustomInv().restoreInventory(player);
                SpleefManager.getInstance().getSpleefPlayers().remove(player);

                if (SpleefManager.getInstance().getSpleefPlayers().size() == 1) {
                    SpleefManager.getInstance().getSpleefPlayers().forEach((p, s) -> {
                        p.sendMessage(SpleefManager.SPLEEF_PREFIX + "§cTous les joueurs ont déconnecté");
                        s.getCustomInv().restoreInventory(p);
                        p.teleport(SpleefManager.getInstance().getTeleportPoint());
                        p.setGameMode(GameMode.ADVENTURE);
                    });

                    SpleefManager.getInstance().getSpleefPlayers().clear();
                    SpleefBreak.restoreBlocks();
                    SpleefManager.getInstance().setGameState(GameState.WAITING);
                }
            }
        }

    }

}
