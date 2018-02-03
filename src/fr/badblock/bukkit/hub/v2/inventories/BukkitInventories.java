package fr.badblock.bukkit.hub.v2.inventories;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.utils.ChatColorUtils;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.i18n.Locale;
import fr.badblock.gameapi.utils.itemstack.ItemStackUtils;

public class BukkitInventories
{

	private static Map<InventoryObject, Map<Locale, Inventory>> staticInventories = new HashMap<>();
	
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
		Locale locale = player.getPlayerData().getLocale();
		Inventory inventory = null;
		if (!staticInventories.containsKey(inventoryObject))
		{
			Map<Locale, Inventory> map = new HashMap<>();
			inventory = createInventory(locale, inventoryObject);
			map.put(locale, inventory);
			staticInventories.put(inventoryObject, map);
		}
		else
		{
			Map<Locale, Inventory> map = staticInventories.get(inventoryObject);
			if (!map.containsKey(locale))
			{
				inventory = createInventory(locale, inventoryObject);
				map.put(locale, inventory);
				staticInventories.put(inventoryObject, map);
			}
			else
			{
				inventory = map.get(locale);
			}
		}
		return inventory;
	}
	
	public static Inventory getDefaultInventory(BadblockPlayer player)
	{
		return getInventory(player, InventoriesLoader.getConfig().getJoinDefaultInventory());
	}
	
	@SuppressWarnings("deprecation")
	private static Inventory createInventory(Locale locale, InventoryObject inventoryObject)
	{
		String name = GameAPI.i18n().get(locale, inventoryObject.getI18name())[0];
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
			if (inventoryItemObject.getI18name() != null && !inventoryItemObject.getI18name().isEmpty())
			{
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', GameAPI.i18n().get(locale, inventoryItemObject.getI18name())[0]));
			}
			if (inventoryItemObject.getI18lore() != null && !inventoryItemObject.getI18lore().isEmpty())
			{
				itemMeta.setLore(ChatColorUtils.getTranslatedMessages(GameAPI.i18n().get(locale, inventoryItemObject.getI18lore())));
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
