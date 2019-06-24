package fr.badblock.bukkit.hub.v2.inventories.fillers;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.badblock.bukkit.hub.v2.inventories.InventoryFiller;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.players.Friend;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.itemstack.ItemStackUtils;

public class InventoryFillerFriendList extends InventoryFiller
{

	@Override
	public void fill(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		HubPlayer hubPlayer = HubPlayer.get(player);

		if (hubPlayer.getFriends() == null || hubPlayer.getFriends().isEmpty())
		{
			fillNoFriend(player, inventoryObject, inventory);
			return;
		}

		Map<Integer, InventoryAction[]> actions = new HashMap<>();

		int emptySlot = inventory.firstEmpty();

		List<String> p = new ArrayList<>();
		boolean something = false;

		for (Friend friend : hubPlayer.getFriends())
		{
			if (emptySlot == -1)
			{
				continue;
			}

			if (!friend.isOnline() || !friend.isAccepted())
			{
				continue;
			}

			if (p.contains(friend.getName().toLowerCase()))
			{
				continue;
			}
			
			p.add(friend.getName().toLowerCase());
			
			something = true;

			fillFriend(player, inventory, friend, emptySlot);

			if (!friend.isAccepted())
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.waiting");
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.waiting");

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			}
			else if (friend.isOnline())
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.EXECUTE_COMMANDBUNGEE,
						"server " + friend.getServer());
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.EXECUTE_COMMANDBUNGEE,
						"party invite " + friend.getName());

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			}
			else
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.offlineinf");
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.offlineinf");

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });	
			}
			
			emptySlot = inventory.firstEmpty();
		}

		for (Friend friend : hubPlayer.getFriends())
		{
			if (emptySlot == -1)
			{
				continue;
			}

			if (!friend.isAccepted())
			{
				continue;
			}
			
			if (p.contains(friend.getName().toLowerCase()))
			{
				continue;
			}
			
			p.add(friend.getName().toLowerCase());
			
			something = true;

			fillFriend(player, inventory, friend, emptySlot);

			if (!friend.isAccepted())
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.waiting");
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.waiting");

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			}
			else if (friend.isOnline())
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.EXECUTE_COMMANDBUNGEE,
						"server " + friend.getServer());
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.EXECUTE_COMMANDBUNGEE,
						"party invite " + friend.getName());

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			}
			else
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.offlineinf");
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.offlineinf");

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });	
			}

			emptySlot = inventory.firstEmpty();
		}

		for (Friend friend : hubPlayer.getFriends())
		{
			if (emptySlot == -1)
			{
				continue;
			}

			if (friend.isAccepted())
			{
				continue;
			}

			if (p.contains(friend.getName().toLowerCase()))
			{
				continue;
			}
			
			p.add(friend.getName().toLowerCase());
			
			something = true;

			fillFriend(player, inventory, friend, emptySlot);
			
			if (!friend.isAccepted())
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.waiting");
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.waiting");

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			}
			else if (friend.isOnline())
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.EXECUTE_COMMANDBUNGEE,
						"server " + friend.getServer());
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.EXECUTE_COMMANDBUNGEE,
						"party invite " + friend.getName());

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			}
			else
			{
				InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.offlineinf");
				InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.items.generic.friends.offlineinf");

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });	
			}
			
			emptySlot = inventory.firstEmpty();
		}

		if (!something)
		{
			fillNoFriend(player, inventoryObject, inventory);
		}

		if (hubPlayer != null)
		{
			hubPlayer.setCustomActions(actions);
		}
	}

	private boolean fillFriend(BadblockPlayer player, Inventory inventory, Friend friend, int slot)
	{
		SimpleEntry<ItemStack, Boolean> item = generate(player, friend);
		inventory.setItem(slot, item.getKey());

		return item.getValue();
	}

	private SimpleEntry<ItemStack, Boolean> generate(BadblockPlayer player, Friend friend)
	{
		Material material = Material.SKULL_ITEM;
		byte data = 3;
		int amount = 1;

		ItemStack itemStack = new ItemStack(material, amount, data);

		if (friend.isAccepted() && friend.isOnline())
		{
			itemStack = ItemStackUtils.fakeEnchant(itemStack);
		}
		
		ItemMeta itemMeta = itemStack.getItemMeta();

		ChatColor color = ChatColor.RED;
		if (friend.isAccepted())
		{
			if (friend.isOnline())
			{
				color = ChatColor.GREEN;
			}
			else
			{
				color = ChatColor.BLUE;
			}
		}
		else
		{
			color = ChatColor.GRAY;
		}
		
		itemMeta.setDisplayName(color + friend.getName());
		List<String> lore = new ArrayList<>();

		lore.add("");
		
		if (!friend.isAccepted())
		{
			lore.add(player.getTranslatedMessage("hub.items.generic.friends.waiting")[0]);
		}
		else if (friend.isOnline())
		{
			lore.add(player.getTranslatedMessage("hub.items.generic.friends.online")[0]);
			lore.add(player.getTranslatedMessage("hub.items.generic.friends.server", friend.getServer())[0]);
			lore.add("");
			lore.add(player.getTranslatedMessage("hub.items.generic.friends.leftclick")[0]);
			lore.add(player.getTranslatedMessage("hub.items.generic.friends.rightclick")[0]);
		}
		else
		{
			lore.add(player.getTranslatedMessage("hub.items.generic.friends.offline")[0]);	
		}
		
		lore.add("");
		
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		
		SkullMeta skullMeta = (SkullMeta) itemMeta;
		skullMeta.setOwner(friend.getName());
		itemStack.setItemMeta(skullMeta);

		if (friend.isAccepted() && friend.isOnline())
		{
			itemStack = ItemStackUtils.fakeEnchant(itemStack);
		}
		
		return new AbstractMap.SimpleEntry<ItemStack, Boolean>(itemStack, true);
	}

	private void fillNoFriend(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		ItemStack itemStack = new ItemStack(Material.BUCKET);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(player.getTranslatedMessage("hub.items.generic.friends.nofriend_name")[0]);
		itemMeta.setLore(Arrays.asList(player.getTranslatedMessage("hub.items.generic.friends.nofriend_lore")));
		itemStack.setItemMeta(itemMeta);

		int emptySlot = inventory.firstEmpty();

		if (emptySlot == -1)
		{
			return;
		}

		inventory.setItem(emptySlot, itemStack);
	}

}