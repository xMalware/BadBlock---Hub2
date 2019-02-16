package fr.badblock.bukkit.hub.v2.inventories.fillers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.badblock.api.common.utils.permissions.Permissible;
import fr.badblock.api.common.utils.permissions.PermissionsManager;
import fr.badblock.bukkit.hub.v2.instances.hubs.Hub;
import fr.badblock.bukkit.hub.v2.inventories.InventoryFiller;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.bukkit.hub.v2.tasks.list.HubUpdateTask;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.itemstack.ItemStackUtils;

public class InventoryFillerHubList extends InventoryFiller
{

	@Override
	public void fill(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		if (Hub.getHubs() == null || Hub.getHubs().isEmpty())
		{
			fillNoHub(player, inventoryObject, inventory);
			return;
		}

		Collection<Hub> hubs = Hub.getHubs();
		Map<Integer, InventoryAction[]> actions = new HashMap<>();

		int emptySlot = inventory.firstEmpty();

		boolean something = false;

		for (Hub hub : hubs)
		{
			if (emptySlot == -1)
			{
				continue;
			}

			if (!hub.isOnline())
			{
				continue;
			}

			something = true;
			fillHub(player, hub, inventory, emptySlot);

			InventoryAction leftAction = new InventoryAction(InventoryActionType.LEFT_CLICK, CustomItemActionType.TELEPORT_SERVER,
					hub.getHubName());
			InventoryAction rightAction = new InventoryAction(InventoryActionType.RIGHT_CLICK, CustomItemActionType.TELEPORT_SERVER,
					hub.getHubName());

			actions.put(emptySlot, new InventoryAction[] { leftAction, rightAction });

			emptySlot = inventory.firstEmpty();
		}

		if (!something)
		{
			fillNoHub(player, inventoryObject, inventory);
		}

		HubPlayer hubPlayer = HubPlayer.get(player);

		if (hubPlayer != null)
		{
			hubPlayer.setCustomActions(actions);
		}
	}

	private void fillHub(BadblockPlayer player, Hub hub, Inventory inventory, int slot)
	{
		ItemStack item = generate(inventory, player, hub.isOnline(), hub.getPlayers(), hub.getSlots(), hub.getId(), hub, hub.getHubName(), hub.getRanks());
		inventory.setItem(slot, item);
	}

	@SuppressWarnings("deprecation")
	private ItemStack generate(Inventory inventory, BadblockPlayer player, boolean isOnline, int players, int slots, int id, Hub hudb, String display, Map<String, Integer> ranks) {
		Material material = Material.REDSTONE_BLOCK;
		byte data = 0;
		int amount = 1;
		ChatColor chatColor = ChatColor.DARK_RED;

		if (isOnline)
		{
			material = Material.STAINED_CLAY;
			// MOCHE!
			if (players >= slots)
			{
				data = DyeColor.RED.getWoolData();
				chatColor = ChatColor.RED;
			}
			else if (players >= 80)
			{
				data = DyeColor.ORANGE.getWoolData();
				chatColor = ChatColor.GOLD;
			}
			else if (players >= 60)
			{
				data = DyeColor.YELLOW.getWoolData();
				chatColor = ChatColor.YELLOW;
			}
			else if (players >= 50)
			{
				data = DyeColor.BLUE.getWoolData();
				chatColor = ChatColor.BLUE;
			}
			else if (players >= 40)
			{
				data = DyeColor.CYAN.getWoolData();
				chatColor = ChatColor.AQUA;
			}
			else
			{
				data = DyeColor.LIME.getWoolData();
				chatColor = ChatColor.GREEN;
			}
		}

		ItemStack itemStack = new ItemStack(material, amount, data);
		itemStack = setMaxStackSize(itemStack, 100);

		itemStack.setAmount(players);

		if (id == HubUpdateTask.hubId)
		{
			itemStack = ItemStackUtils.fakeEnchant(itemStack);
		}

		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(chatColor + (hudb != null ? "Hub n°" + hudb.getId() : display));
		List<String> lore = new ArrayList<>();
		lore.add("");

		if (id == HubUpdateTask.hubId)
		{
			lore.add(player.getTranslatedMessage("hub.items.generic.hubs.alreadyonit")[0]);
		}
		// §7Connectés:
		lore.add(player.getTranslatedMessage("hub.items.generic.hubs.onlineplayers")[0] + "§f" + players + (slots >= 0 ? "/" + slots : ""));
		Map<String, String> order = new HashMap<>();
		List<Permissible> groups = PermissionsManager.getManager().getGroups().stream().sorted((a, b) -> { return Integer.compare(b.getPower(), a.getPower()); }).collect(Collectors.toList());

		int i = 0;
		for (Permissible group : groups)
		{
			String d = generateForId(i) + "";
			order.put(d, group.getName());
			i++;
		}

		System.out.println("ranks hub " + hudb.getHubName() + " : " + ranks.toString());

		if (ranks != null && !ranks.isEmpty())
		{
			lore.add("");
			for (Entry<String, Integer> entry : ranks.entrySet())
			{
				lore.add(player.getTranslatedMessage("permissions.tab." + order.get(entry.getKey()))[0].replace("null", "default") + "§f» §7" + entry.getValue());
			}
		}

		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	private char generateForId(int id)
	{
		if (id == 0)
		{
			return 'Z';
		}

		int A = 'A';

		if(id > 26)
		{
			A   = 'a';
			id -= 26;

			return (char) (A + id);
		}

		return (char) (A + id);
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

	private void fillNoHub(BadblockPlayer player, InventoryObject inventoryObject, Inventory inventory)
	{
		ItemStack itemStack = new ItemStack(Material.BUCKET);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(player.getTranslatedMessage("hub.items.generic.hubs.nohub_name")[0]);
		itemMeta.setLore(Arrays.asList(player.getTranslatedMessage("hub.items.generic.hubs.nohub_lore")));
		itemStack.setItemMeta(itemMeta);

		int emptySlot = inventory.firstEmpty();

		if (emptySlot == -1)
		{
			return;
		}

		inventory.setItem(emptySlot, itemStack);
	}

}