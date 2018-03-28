package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.games.runnables.VIPCourseRunnableLaunching;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagVIPCourseLaunchingTimerNumber extends HubTag
{
    @Override
    public String getTag(BadblockPlayer player)
    {
        return getTag(player);
    }

    @Override
    public String getTag(BadblockPlayer player, InventoryItemObject object)
    {
        return Integer.toString(VIPCourseRunnableLaunching.TIME_BEFORE_START);
    }
}
