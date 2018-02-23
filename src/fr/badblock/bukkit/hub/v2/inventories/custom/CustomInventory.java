package fr.badblock.bukkit.hub.v2.inventories.custom;

import org.bukkit.inventory.ItemStack;

import fr.badblock.gameapi.players.BadblockPlayer;

public abstract class CustomInventory
{

	/**
	 * @param player
	 * @param itemStack
	 * @return true : handle
	 * false : not handle
	 */
	public abstract boolean work(BadblockPlayer player, ItemStack itemStack);
	
}
