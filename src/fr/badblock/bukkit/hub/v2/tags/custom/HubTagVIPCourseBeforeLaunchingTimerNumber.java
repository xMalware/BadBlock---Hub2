package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.games.runnables.VIPCourseRunnableBeforeLaunching;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagVIPCourseBeforeLaunchingTimerNumber extends HubTag{

    @Override
    public String getTag(BadblockPlayer player, InventoryItemObject object)
    {
        return getTag(player);
    }

    @Override
    public String getTag(BadblockPlayer player)
    {
        return Integer.toString(VIPCourseRunnableBeforeLaunching.TIME_BEFORE_LAUNCHING);
    }
}
