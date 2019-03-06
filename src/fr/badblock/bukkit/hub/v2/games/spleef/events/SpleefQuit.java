package fr.badblock.bukkit.hub.v2.games.spleef.events;

import fr.badblock.bukkit.hub.v2.games.spleef.SpleefManager;
import fr.badblock.bukkit.hub.v2.games.spleef.SpleefPlayer;
import fr.badblock.bukkit.hub.v2.games.states.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Toinetoine1 on 17/01/2019.
 */

public class SpleefQuit implements Listener {


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        SpleefManager.getInstance().getSpleefPlayers().remove(player);

        List<SpleefPlayer> playersAlive = SpleefManager.getInstance().getSpleefPlayers().values().stream().filter(spleefPlayer -> !spleefPlayer.isDead()).collect(Collectors.toList());

        if (playersAlive.size() <= 1 && SpleefManager.getInstance().getGameState() == GameState.INGAME) {
            SpleefManager.getInstance().getSpleefPlayers().forEach((p, spleefPlayer) -> {
                p.sendMessage(SpleefManager.SPLEEF_PREFIX + "§cUn joueur s'est déconnecté !");
                spleefPlayer.getCustomInv().restoreInventory(p);
                p.performCommand("spawn");
                p.setGameMode(GameMode.ADVENTURE);
            });

            SpleefManager.getInstance().getSpleefPlayers().clear();
            SpleefBreak.restoreBlocks();
            SpleefManager.getInstance().setGameState(GameState.WAITING);
        }

        if (SpleefManager.getInstance().getSpleefPlayers().size() == 0) {
            SpleefManager.getInstance().setGameState(GameState.WAITING);
            SpleefBreak.restoreBlocks();
        }
    }

}
