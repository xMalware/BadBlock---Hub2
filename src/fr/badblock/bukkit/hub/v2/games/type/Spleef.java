package fr.badblock.bukkit.hub.v2.games.type;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.bukkit.hub.v2.games.runnables.SpleefRunnable;
import fr.badblock.gameapi.game.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.threading.TaskManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Spleef extends HubGame
{
    BadblockPlayer bplayer;
    public static int time = 0;
    SpleefRunnable runnable;

    public Spleef()
    {
        if(GameState.getStatus(1) == GameState.WAITING){
            this.addPlayer(bplayer);
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
