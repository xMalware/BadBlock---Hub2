package fr.badblock.bukkit.hub.v2.inventories.custom;

import org.bukkit.inventory.ItemStack;

import fr.badblock.gameapi.players.BadblockPlayer;

public abstract class CustomInventory
{

	public abstract void work(BadblockPlayer player, ItemStack itemStack);
	
}
