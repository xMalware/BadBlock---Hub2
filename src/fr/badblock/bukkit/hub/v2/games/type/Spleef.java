package fr.badblock.bukkit.hub.v2.games.type;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.bukkit.hub.v2.games.listeners.SpleefListener;
import fr.badblock.bukkit.hub.v2.games.runnables.SpleefRunnable;
import fr.badblock.gameapi.game.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Spleef extends HubGame
{
    BadblockPlayer bplayer;
    public static int time = 0;
    SpleefRunnable runnable;

    public Spleef()
    {
        if(GameState.getStatus(1) == GameState.WAITING){
            addPlayer(bplayer);
            SpleefListener.gamePlayer.put(new Spleef(), bplayer);
        } else {
            bplayer.sendTranslatedMessage("hub.game.spleef.already_started");
        }
        play();
    }

    public void play()
    {

        ItemStack spade = new ItemStack(Material.DIAMOND_SPADE);
        // Set 8 temporairly
        if(getPlayers().size() == 8)
        {
            runnable.run();
        }
    }
}
