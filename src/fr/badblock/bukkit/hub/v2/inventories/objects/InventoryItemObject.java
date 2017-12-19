package fr.badblock.bukkit.hub.v2.inventories.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter public class InventoryItemObject {

	private static String				i18name;
	private static String				i18lore;
	private int    			 	place;
	private static int				 	amount = 1;
	private String 			 	type;
	private static InventoryAction[]	actions;
	private static boolean				fakeEnchant;
	
	public int getPlace() {
		return place;
	}

	public static InventoryAction[] getActions() {
		return actions;
	}

	public String getType() {
		return type;
	}

	public static int getAmount() {
		return amount;
	}

	public static String getI18name() {
		// TODO Auto-generated method stub
		return i18name;
	}

	public static String getI18lore() {
		// TODO Auto-generated method stub
		return i18lore;
	}

	public static boolean isFakeEnchant() {
		return fakeEnchant;
	}
	
}
