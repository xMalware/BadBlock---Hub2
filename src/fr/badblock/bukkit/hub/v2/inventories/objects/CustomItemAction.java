package fr.badblock.bukkit.hub.v2.inventories.objects;

import fr.badblock.gameapi.players.BadblockPlayer;

public abstract class CustomItemAction
{
	
	public abstract void execute(BadblockPlayer player, CustomItemActionType action, String actionData);
	
}
