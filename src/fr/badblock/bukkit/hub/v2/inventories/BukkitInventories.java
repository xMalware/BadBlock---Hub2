package fr.badblock.bukkit.hub.v2.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.tags.TagManager;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.itemstack.ItemStackUtils;

public class BukkitInventories
{

	public static Inventory getInventory(BadblockPlayer player, String inventoryName)
	{
		InventoryObject inventoryObject = InventoriesLoader.getInventory(inventoryName);
		if (inventoryObject == null)
		{
			BadBlockHub.log("§cUnknown inventory '" + inventoryName + "'.");
			return null;
		}
		return getInventory(player, inventoryObject);
	}

	public static Inventory getInventory(BadblockPlayer player, InventoryObject inventoryObject)
	{
		return createInventory(player, inventoryObject);
	}

	public static Inventory getDefaultInventory(BadblockPlayer player)
	{
		return getInventory(player, InventoriesLoader.getConfig().getJoinDefaultInventory());
	}

	@SuppressWarnings("deprecation")
	private static Inventory createInventory(BadblockPlayer player, InventoryObject inventoryObject)
	{
		String name = player.getTranslatedMessage(inventoryObject.getI18name())[0];
		Inventory inventory = Bukkit.createInventory(null, 9 * inventoryObject.getLines(), name);
		for (InventoryItemObject inventoryItemObject : inventoryObject.getItems())
		{
			String[] splitter = inventoryItemObject.getType().split(":");
			String material = splitter[0];
			byte data = 0;
			if (splitter.length >= 2) data = Byte.parseByte(splitter[1]);
			Material type = null;
			try
			{
				int o = Integer.parseInt(material);
				type = Material.getMaterial(o);
			}
			catch(Exception error)
			{
				type = Material.getMaterial(material);
			}
			ItemStack itemStack = new ItemStack(type, inventoryItemObject.getAmount(), data);
			if (inventoryItemObject.isFakeEnchant())
			{
				itemStack = ItemStackUtils.fakeEnchant(itemStack);
			}
			ItemMeta itemMeta = itemStack.getItemMeta();
			TagManager tagManager = TagManager.getInstance();
			
			if (inventoryItemObject.getI18name() != null && !inventoryItemObject.getI18name().isEmpty())
			{
				String string = ChatColor.translateAlternateColorCodes('&', player.getTranslatedMessage(inventoryItemObject.getI18name())[0]);
				itemMeta.setDisplayName(tagManager.tagify(player, string, inventoryItemObject));
			}
			if (inventoryItemObject.getI18lore() != null && !inventoryItemObject.getI18lore().isEmpty())
			{
				List<String> lore = new ArrayList<>();
				for (String string : player.getTranslatedMessage(inventoryItemObject.getI18lore()))
				{
					string = tagManager.tagify(player, ChatColor.translateAlternateColorCodes('&', string), inventoryItemObject);
					lore.add(string);
				}
				itemMeta.setLore(lore);
			}
			itemStack.setItemMeta(itemMeta);
			inventory.setItem(inventoryItemObject.getPlace(), itemStack);
		}
		return inventory;
	}

	public static void giveDefaultInventory(BadblockPlayer player)
	{
		player.clearInventory();
		Inventory defaultInventory = getDefaultInventory(player);
		if (defaultInventory == null)
		{
			BadBlockHub.log("§cUnknown default inventory.");
			return;
		}
		int i = 0;
		for (ItemStack itemStack : defaultInventory.getContents())
		{
			if (itemStack != null && itemStack.getType() != Material.AIR && itemStack.getType() != null)
			{
				player.getInventory().setItem(i, itemStack);
			}
			i++;
		}
	}

}
