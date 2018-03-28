package fr.badblock.bukkit.hub.v2.games.runnables;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.threading.TaskManager;
import org.bukkit.scheduler.BukkitRunnable;

public class VIPCourseRunnableBeforeLaunching extends BukkitRunnable
{

    HubGame game;
    public int TIME_BEFORE_LAUNCHING = 30;
    public int task;

    @Override
    public void run()
    {
        //TODO: Add a tag for the timer count
        TIME_BEFORE_LAUNCHING--;

        if(TIME_BEFORE_LAUNCHING == 15 || TIME_BEFORE_LAUNCHING == 10 || TIME_BEFORE_LAUNCHING == 5 || TIME_BEFORE_LAUNCHING == 4 || TIME_BEFORE_LAUNCHING == 3 || TIME_BEFORE_LAUNCHING == 2 || TIME_BEFORE_LAUNCHING == 1)
        {
            for(BadblockPlayer bPlayers : game.getPlayers())
            {
                bPlayers.sendTranslatedMessage("hub.vipcourse.timer_before_launching");
            }
        }
        if(TIME_BEFORE_LAUNCHING == 0){
            TaskManager.cancelTaskById(task);
            //TODO: Set the timer and level for Launching
        }
    }
}
