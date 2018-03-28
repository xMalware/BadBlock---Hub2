package fr.badblock.bukkit.hub.v2.tags.custom;

import fr.badblock.bukkit.hub.v2.games.runnables.SpleefRunnable;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.gameapi.players.BadblockPlayer;

public class HubTagSpleefTimerNumber extends HubTag
{
    @Override
    public String getTag(BadblockPlayer player)
    {
        return getTag(player);
    }

    @Override
    public String getTag(BadblockPlayer player, InventoryItemObject object)
    {
        return Integer.toString(SpleefRunnable.TIME_BEFORE_START);
    }
}
