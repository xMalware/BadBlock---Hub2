package fr.badblock.bukkit.hub.v2.inventories.fillers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.badblock.bukkit.hub.v2.inventories.InventoryFiller;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.data.PlayerData;
import fr.badblock.gameapi.players.data.boosters.PlayerBooster;

public class InventoryFillerBooster extends InventoryFiller
{
	
	@Override
	public void fill(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		PlayerData data = player.getPlayerData();
		
		if (data == null)
		{
			fillNoBooster(player, inventoryObject, inventory);
			return;
		}
		
		if (data.getBoosters() == null || data.getBoosters().isEmpty())
		{
			fillNoBooster(player, inventoryObject, inventory);
			return;
		}
		
		// booster itemstack & booster index in list
		Map<ItemStack, Integer> workingBoosters = new HashMap<>(); 
		Map<ItemStack, Integer> disabledBoosters = new HashMap<>(); 
		Map<ItemStack, Integer> expiredBoosters = new HashMap<>(); 
		
		for (int i = 0; i < data.getBoosters().size(); i++)
		{
			PlayerBooster playerBooster = data.getBoosters().get(i);
			
			if (playerBooster.isExpired())
			{
				expiredBoosters.put(getExpiredBoosterItem(player, playerBooster), i);
				continue;
			}
			
			if (!playerBooster.isEnabled())
			{
				disabledBoosters.put(getDisabledBoosterItem(player, playerBooster), i);
				continue;
			}
			
			workingBoosters.put(getWorkingBoosterItem(player, playerBooster), i);
		}
		
		if (workingBoosters.isEmpty() && disabledBoosters.isEmpty() && expiredBoosters.isEmpty())
		{
			fillNoBooster(player, inventoryObject, inventory);
			return;
		}
		
		Map<Integer, InventoryAction[]> actions = new HashMap<>();
		int emptySlot = inventory.firstEmpty();

		for (Entry<ItemStack, Integer> entry : workingBoosters.entrySet())
		{
			if (emptySlot == -1)
			{
				continue;
			}
			
			ItemStack workingBooster = entry.getKey();
			
			inventory.setItem(emptySlot, workingBooster);

			InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
					"hub.boosters.cantdisableboosters");
			InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
					"hub.boosters.cantdisableboosters");
			
			actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			
			emptySlot = inventory.firstEmpty();
		}

		for (Entry<ItemStack, Integer> entry : disabledBoosters.entrySet())
		{
			if (emptySlot == -1)
			{
				continue;
			}

			ItemStack disabledBooster = entry.getKey();
			int index = entry.getValue();
			
			inventory.setItem(emptySlot, disabledBooster);

			InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.BOOSTER_GAMESELECT,
					Integer.toString(index));
			InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.BOOSTER_GAMESELECT,
					Integer.toString(index));
			
			actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			
			emptySlot = inventory.firstEmpty();
		}
		
		for (Entry<ItemStack, Integer> entry : expiredBoosters.entrySet())
		{
			if (emptySlot == -1)
			{
				continue;
			}
			
			ItemStack expiredBooster = entry.getKey();
			int index = entry.getValue();
			
			InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.BOOSTER_REMOVE,
					Integer.toString(index));
			InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.BOOSTER_REMOVE,
					Integer.toString(index));
			
			actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			
			inventory.setItem(emptySlot, expiredBooster);
			emptySlot = inventory.firstEmpty();
		}
		
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (hubPlayer != null)
		{
			hubPlayer.setCustomActions(actions);
		}
	}

	private ItemStack getWorkingBoosterItem(BadblockPlayer player, PlayerBooster playerBooster)
	{
		ItemStack itemStack = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		String boosterName = player.getTranslatedMessage("hub.items.generic.boosters.boostername_" + playerBooster.getBooster().getId())[0];
		String gameEnabled = playerBooster.getGameName();
		String xpMultiplier = Double.toString((playerBooster.getBooster().getXpMultiplier() - 1) * 100);
		String coinsMultiplier = Double.toString((playerBooster.getBooster().getCoinsMultiplier() - 1) * 100);
		
		itemMeta.setDisplayName(player.getTranslatedMessage("hub.items.generic.boosters.workingbooster_name", boosterName)[0]);
		itemMeta.setLore(Arrays.asList(player.getTranslatedMessage("hub.items.generic.boosters.workingbooster_lore", 
				gameEnabled, xpMultiplier, coinsMultiplier)));
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}

	private ItemStack getDisabledBoosterItem(BadblockPlayer player, PlayerBooster playerBooster)
	{
		ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		String boosterName = player.getTranslatedMessage("hub.items.generic.boosters.boostername_" + playerBooster.getBooster().getId())[0];
		String xpMultiplier = Double.toString((playerBooster.getBooster().getXpMultiplier() - 1) * 100);
		String coinsMultiplier = Double.toString((playerBooster.getBooster().getCoinsMultiplier() - 1) * 100);
		
		itemMeta.setDisplayName(player.getTranslatedMessage("hub.items.generic.boosters.disabledbooster_name", boosterName)[0]);
		itemMeta.setLore(Arrays.asList(player.getTranslatedMessage("hub.items.generic.boosters.disabledbooster_lore", 
				xpMultiplier, coinsMultiplier)));
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}

	private ItemStack getExpiredBoosterItem(BadblockPlayer player, PlayerBooster playerBooster)
	{
		ItemStack itemStack = new ItemStack(Material.COAL_BLOCK);
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		String boosterName = player.getTranslatedMessage("hub.items.generic.boosters.boostername_" + playerBooster.getBooster().getId())[0];
		String xpMultiplier = Double.toString((playerBooster.getBooster().getXpMultiplier() - 1) * 100);
		String coinsMultiplier = Double.toString((playerBooster.getBooster().getCoinsMultiplier() - 1) * 100);
		
		itemMeta.setDisplayName(player.getTranslatedMessage("hub.items.generic.boosters.expiredbooster_name", boosterName)[0]);
		itemMeta.setLore(Arrays.asList(player.getTranslatedMessage("hub.items.generic.boosters.expiredbooster_lore", 
				xpMultiplier, coinsMultiplier)));
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	private void fillNoBooster(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		ItemStack itemStack = new ItemStack(Material.BUCKET);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(player.getTranslatedMessage("hub.items.generic.boosters.nobooster_name")[0]);
		itemMeta.setLore(Arrays.asList(player.getTranslatedMessage("hub.items.generic.boosters.nobooster_lore")));
		itemStack.setItemMeta(itemMeta);
		
		int emptySlot = inventory.firstEmpty();
		
		if (emptySlot == -1)
		{
			return;
		}
		
		inventory.setItem(emptySlot, itemStack);
	}
	
}