package fr.badblock.bukkit.hub.v2.inventories.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.utils.i18n.TranslatableString;
import fr.badblock.gameapi.utils.itemstack.ItemStackFactory;

public class ItemLoader {

	public ItemStack loadItem(TranslatableString displayName, TranslatableString lore, Material material, int amount, boolean fakeEnchant) {
		GameAPI gameAPI = GameAPI.getAPI();
		ItemStackFactory item = gameAPI.createItemStackFactory().displayName(displayName).type(material).lore(lore);
		if (fakeEnchant) item = item.fakeEnchantment();
		return item.create(amount);
	}
	
}
