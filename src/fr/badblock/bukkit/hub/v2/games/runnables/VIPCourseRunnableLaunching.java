package fr.badblock.bukkit.hub.v2.games.runnables;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.threading.TaskManager;
import org.bukkit.scheduler.BukkitRunnable;

public class VIPCourseRunnableLaunching extends BukkitRunnable
{

    HubGame game;
    public static int TIME_BEFORE_START = 11;
    public static int task;

    @Override
    public void run()
    {
        //TODO: Add a tag for the timer count
        TIME_BEFORE_START--;

        if(TIME_BEFORE_START == 10 || TIME_BEFORE_START == 5 || TIME_BEFORE_START == 4 || TIME_BEFORE_START == 3 || TIME_BEFORE_START == 2 || TIME_BEFORE_START == 1)
        {
            for(BadblockPlayer bPlayers : game.getPlayers())
            {
                bPlayers.sendTranslatedMessage("hub.vipcourse.timer_launching");
            }
        }
        if(TIME_BEFORE_START == 0)
        {
            TaskManager.cancelTaskById(task);
        }
    }
}
