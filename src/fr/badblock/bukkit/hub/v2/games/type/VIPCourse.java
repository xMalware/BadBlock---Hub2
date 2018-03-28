package fr.badblock.bukkit.hub.v2.games.type;

import fr.badblock.bukkit.hub.v2.games.HubGame;
import fr.badblock.bukkit.hub.v2.games.runnables.VIPCourseRunnableBeforeLaunching;
import fr.badblock.gameapi.game.GameState;
import fr.badblock.gameapi.players.BadblockPlayer;

public class VIPCourse extends HubGame{

    BadblockPlayer bPlayer;
    VIPCourseRunnableBeforeLaunching beforeLaunching;

    public VIPCourse()
    {
        if(GameState.getStatus(1) == GameState.WAITING)
        {
            addPlayer(bPlayer);
        }
        else
            {
            bPlayer.sendTranslatedMessage("hub.game.vipcourse.already_started");
        }
    }

    public void play()
    {
        if(getPlayers().size() == 5)
        {
            bPlayer.sendTranslatedMessage("hub.game.vipcourse.full");
        }
        else if(getPlayers().size() <= 5 && getPlayers().size() >= 2)
        {
            beforeLaunching.run();
        }
    }
}
