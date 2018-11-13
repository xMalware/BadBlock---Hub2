package fr.badblock.bukkit.hub.v2.games.runnables;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.threading.TaskManager;
import lombok.AllArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

@AllArgsConstructor
public class SpleefRunnable extends BukkitRunnable
{

    HubGame game;
    public static int TIME_BEFORE_START = 11;
    public static int task;
    ItemStack spade = new ItemStack(Material.DIAMOND_SPADE);

    @Override
    public void run()
    {
        TIME_BEFORE_START--;
        for(BadblockPlayer players : game.getPlayers()){
            players.setLevel(TIME_BEFORE_START);
        }
        if(TIME_BEFORE_START == 5 || TIME_BEFORE_START == 4 || TIME_BEFORE_START == 3 || TIME_BEFORE_START == 2 || TIME_BEFORE_START == 1)
        {
            for(BadblockPlayer players: game.getPlayers()){
                players.sendTranslatedMessage("hub.game.spleef.launching");
            }
        }
        if(TIME_BEFORE_START == 0)
        {
            TaskManager.cancelTaskById(task);
            for(BadblockPlayer players : game.getPlayers())
            {
                teleportintoTheGame(players);
                players.setGameMode(GameMode.SURVIVAL);
                players.getInventory().clear();
                players.getInventory().addItem(spade);
            }
        }


    }

    public void teleportintoTheGame(BadblockPlayer player)
    {
        Random r = new Random();
        int o = r.nextInt(8);
        if(o == 1)
        {
            Location location = new Location(player.getWorld(), -133.484, 76, 1.315);
            player.teleport(location);
        }
        if(r.equals(2))
        {
            Location location = new Location(player.getWorld(), -143.400, 76, -1.373);
            player.teleport(location);
        }
        if(r.equals(3))
        {
            Location location = new Location(player.getWorld(), -148.027, 76, -10.778);
            player.teleport(location);
        }
        if(r.equals(4))
        {
            Location location = new Location(player.getWorld(), -144.189, 76, -19.866);
            player.teleport(location);
        }
        if(r.equals(5))
        {
            Location location = new Location(player.getWorld(), -139.784, 76, -23.591);
            player.teleport(location);
        }
        if(r.equals(6))
        {
            Location location = new Location(player.getWorld(), -133.113, 76, -24.814);
            player.teleport(location);
        }
        if(r.equals(7))
        {
            Location location = new Location(player.getWorld(), -127.518, 76, -21.100);
            player.teleport(location);
        }
        if(r.equals(8))
        {
            Location location = new Location(player.getWorld(), -123.766, 76, -12.902);
            player.teleport(location);
        }
    }
}
