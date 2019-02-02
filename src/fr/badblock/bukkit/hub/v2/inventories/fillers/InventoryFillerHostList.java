package fr.badblock.bukkit.hub.v2.inventories.fillers;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.badblock.api.common.minecraft.GameState;
import fr.badblock.bukkit.hub.v2.instances.hosts.Host;
import fr.badblock.bukkit.hub.v2.instances.hosts.HostWhitelistState;
import fr.badblock.bukkit.hub.v2.inventories.InventoryFiller;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.tasks.list.HostFetcherTask;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.itemstack.ItemStackUtils;

public class InventoryFillerHostList extends InventoryFiller
{

	@Override
	public void fill(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		if (HostFetcherTask.hosts == null || HostFetcherTask.hosts.isEmpty())
		{
			fillNoHost(player, inventoryObject, inventory);
			return;
		}

		Collection<Entry<String, Host>> entries = HostFetcherTask.hosts.entrySet();
		Map<Integer, InventoryAction[]> actions = new HashMap<>();

		int emptySlot = inventory.firstEmpty();

		boolean something = false;

		for (Entry<String, Host> entry : entries)
		{
			if (emptySlot == -1)
			{
				continue;
			}

			if (entry.getValue().isExpired())
			{
				continue;
			}

			something = true;

			if (entry.getValue().isWhitelistEnabled())
			{
				if (!entry.getValue().getWhitelist().contains(player.getName().toLowerCase()))
				{
					fillHost(player, inventory, entry.getKey(), entry.getValue(), HostWhitelistState.NOT_WHITELISTED, emptySlot);

					InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
							"hub.hosts.notwhitelisted");
					InventoryAction rightAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
							"hub.hosts.notwhitelisted");

					actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
					emptySlot = inventory.firstEmpty();
					continue;
				}

				boolean canJoin = fillHost(player, inventory, entry.getKey(), entry.getValue(), HostWhitelistState.NOT_WHITELISTED, emptySlot);

				InventoryAction leftAction;
				InventoryAction rightAction;
				if (canJoin)
				{
					leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.TELEPORT_SERVER,
							entry.getKey());
					rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.TELEPORT_SERVER,
							entry.getKey());
				}
				else
				{
					leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
							"hub.hosts.cantbejoinedfornow");
					rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
							"hub.hosts.cantbejoinedfornow");
				}

				actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
				emptySlot = inventory.firstEmpty();
				continue;
			}
			
			boolean canJoin = fillHost(player, inventory, entry.getKey(), entry.getValue(), HostWhitelistState.NOT_WHITELISTED, emptySlot);

			InventoryAction leftAction;
			InventoryAction rightAction;
			if (canJoin)
			{
				leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.TELEPORT_SERVER,
						entry.getKey());
				rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.TELEPORT_SERVER,
						entry.getKey());
			}
			else
			{
				leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.hosts.cantbejoinedfornow");
				rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.SEND_MESSAGE,
						"hub.hosts.cantbejoinedfornow");
			}

			actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });
			emptySlot = inventory.firstEmpty();
		}

		if (!something)
		{
			fillNoHost(player, inventoryObject, inventory);
		}

		HubPlayer hubPlayer = HubPlayer.get(player);

		if (hubPlayer != null)
		{
			hubPlayer.setCustomActions(actions);
		}
	}

	private boolean fillHost(BadblockPlayer player, Inventory inventory, String serverName, Host host, HostWhitelistState state, int slot)
	{
		SimpleEntry<ItemStack, Boolean> item = generate(player, serverName, host, state);
		inventory.setItem(slot, item.getKey());
		
		return item.getValue();
	}

	@SuppressWarnings("deprecation")
	private SimpleEntry<ItemStack, Boolean> generate(BadblockPlayer player, String serverName, Host host, HostWhitelistState state)
	{
		Material material = Material.REDSTONE_BLOCK;
		byte data = 0;
		int amount = 1;
		ChatColor chatColor = ChatColor.DARK_RED;

		String extraError = "";
		if (!state.equals(HostWhitelistState.NOT_WHITELISTED))
		{
			material = Material.STAINED_CLAY;
			
			if (!host.getKeepAlive().isJoinable())
			{
				if (host.getKeepAlive().getPlayers() >= host.getKeepAlive().getSlots())
				{
					extraError = "full";
				}
				else if (!GameState.WAITING.equals(host.getKeepAlive().getGameState()))
				{
					extraError = "running";
				}
				else
				{
					extraError = "unknown";
				}
				
				data = DyeColor.ORANGE.getWoolData();
				chatColor = ChatColor.GOLD;
			}
			else
			{
				data = DyeColor.LIME.getWoolData();
				chatColor = ChatColor.GREEN;
			}
		}
		else
		{
			extraError = "notwhitelisted";
		}

		ItemStack itemStack = new ItemStack(material, amount, data);
		itemStack = setMaxStackSize(itemStack, 100);

		itemStack.setAmount(host.getKeepAlive().getPlayers());

		if (extraError.isEmpty())
		{
			itemStack = ItemStackUtils.fakeEnchant(itemStack);
		}

		ItemMeta itemMeta = itemStack.getItemMeta();
		
		String rawType = ChatColor.stripColor(host.getType());
		itemMeta.setDisplayName(chatColor + rawType + " n°" + host.getId());
		List<String> lore = new ArrayList<>();
		
		lore.add("");
		lore.add(player.getTranslatedMessage("hub.items.generic.hosts.hostedby")[0] + "§f" + host.getOwner());
		lore.add("");
		
		if (!extraError.isEmpty())
		{
			lore.add(player.getTranslatedMessage("hub.items.generic.hosts.cantjoin_" + extraError)[0]);
		}
		
		lore.add(player.getTranslatedMessage("hub.items.generic.hosts.onlineplayers")[0] + "§f" + host.getKeepAlive().getPlayers() + "/" + host.getKeepAlive().getSlots());
		
		if (extraError.isEmpty())
		{
			lore.add("");
			lore.add(player.getTranslatedMessage("hub.items.generic.hosts.clicktojoin")[0]);
		}

		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);

		return new AbstractMap.SimpleEntry<ItemStack, Boolean>(itemStack, extraError.isEmpty());
	}
	
	public static ItemStack setMaxStackSize(ItemStack is, int amount)
	{
		try
		{
			net.minecraft.server.v1_8_R3.ItemStack nmsIS = CraftItemStack.asNMSCopy(is);
			nmsIS.getItem().c(amount);
			return CraftItemStack.asBukkitCopy(nmsIS);
		}
		catch (Throwable t)
		{

		}

		return null;
	}

	private void fillNoHost(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		ItemStack itemStack = new ItemStack(Material.BUCKET);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(player.getTranslatedMessage("hub.items.generic.hosts.nohost_name")[0]);
		itemMeta.setLore(Arrays.asList(player.getTranslatedMessage("hub.items.generic.hosts.nohost_lore")));
		itemStack.setItemMeta(itemMeta);

		int emptySlot = inventory.firstEmpty();

		if (emptySlot == -1)
		{
			return;
		}

		inventory.setItem(emptySlot, itemStack);
	}

}