package fr.badblock.bukkit.hub.v2.inventories.objects;

import lombok.Getter;

@Getter
public class InventoriesConfig {

	private static String joinDefaultInventory = "joinInventory";

	public static String getJoinDefaultInventory() {
		return joinDefaultInventory;
	}
	
}
