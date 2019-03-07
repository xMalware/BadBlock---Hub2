package fr.badblock.bukkit.hub.v2.games.blockparty.events;

import fr.badblock.bukkit.hub.v2.games.blockparty.BlockPartyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PartyQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        BlockPartyManager.getInstance().getBlockPlayers().remove(player).getRadioSongPlayer().setPlaying(false);
        PartyCommand.restartGames();
        PartyCommand.checkWin(player);
    }

}
