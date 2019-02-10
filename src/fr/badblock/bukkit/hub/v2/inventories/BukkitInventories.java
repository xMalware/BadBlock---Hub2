package fr.badblock.bukkit.hub.v2.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.config.configs.FeaturesConfig;
import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureManager;
import fr.badblock.bukkit.hub.v2.cosmetics.features.FeatureType;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.hats.CustomHats;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryItemObject;
import fr.badblock.bukkit.hub.v2.inventories.objects.InventoryObject;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
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
			if (inventoryItemObject.getNeededPermission() != null && !player.hasPermission(inventoryItemObject.getNeededPermission()))
			{
				continue;
			}
			
			String[] splitter = inventoryItemObject.getType().split(":");
			String material = splitter[0];
			byte data = 0;

			if (splitter.length >= 2)
			{
				data = Byte.parseByte(splitter[1]);
			}

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

			boolean feature = false;
			if (inventoryItemObject.getActions() != null)
			{
				InventoryAction action = inventoryItemObject.getActions()[0];

				if (action.getAction().equals(CustomItemActionType.USE_FEATURE) || 
						action.getAction().equals(CustomItemActionType.BUY_FEATURE))
				{
					feature = true;
					String featureName = action.getActionData();
					HubStoredPlayer storedPlayer = HubStoredPlayer.get(player);
					boolean owned = false;
					if (FeatureManager.getInstance().hasFeature(player, storedPlayer, featureName))
					{
						itemStack = ItemStackUtils.fakeEnchant(itemStack);
						itemMeta = itemStack.getItemMeta();
						owned = true;
					}

					// Get info
					FeaturesConfig featuresConfig = ConfigLoader.getFeatures();

					// Unknown feature
					if (!featuresConfig.getFeatures().containsKey(featureName))
					{
						System.out.println("[BadBlockHub] Unknown feature '" + featureName + "'.");
						continue;
					}

					// Get feature
					Feature featureObj = featuresConfig.getFeatures().get(featureName);

					if (featureObj == null)
					{
						System.out.println("[BadBlockHub] Feature '" + featureName + "' is null.");
						continue;
					}

					if (featureObj.getType().equals(FeatureType.HATS))
					{
						String skinOwner = CustomHats.getSkinOwner(featureObj);

						if (skinOwner != null)
						{
							SkullMeta skullMeta = (SkullMeta) itemMeta;
							skullMeta.setOwner(skinOwner);
							itemStack.setItemMeta(skullMeta);
							itemMeta = skullMeta;
						}
					}

					String displayFeatureName = ChatColor.translateAlternateColorCodes('&', featureObj.getName());
					String string = displayFeatureName + " {owned}";
					itemMeta.setDisplayName(tagManager.tagify(player, string, inventoryItemObject));

					List<String> strings = new ArrayList<>();

					String needed = "";
					int addon = 0;
					if (featureObj.getLevelNeeded() > 0)
					{
						needed = "level";
						addon = featureObj.getLevelNeeded();
					}
					else if (featureObj.getBadcoinsNeeded() > 0)
					{
						needed = "badcoins";
						addon = featureObj.getBadcoinsNeeded();
					}
					else if (featureObj.getShopPointsNeeded() > 0)
					{
						needed = "shoppoints";
						addon = featureObj.getShopPointsNeeded();
					}
					else if (featureObj.getNeeded() != null && !featureObj.getNeeded().isBuyable())
					{
						needed = "notbuyable";
					}
					else if (featureObj.getNeeded() != null && featureObj.getNeeded().getPermissions() != null
							&& featureObj.getNeeded().getPermissions().length > 0)
					{
						needed = featureName.toLowerCase();
					}

					String featureAction = featureObj.getNeeded().isBuyable() && !owned ? 
							player.getTranslatedMessage("hub.items.generic.feature_actions_buy")[0] : owned ?
									player.getTranslatedMessage("hub.items.generic.feature_actions_use")[0] : "";

									for (String message : player.getTranslatedMessage("hub.items.generic.feature_lore", displayFeatureName,
											player.getTranslatedMessage("hub.items.generic.feature_need_" + needed, addon)[0],
											player.getTranslatedMessage("hub.items.generic.feature_owned_" + owned)[0],
											featureAction))
									{
										strings.add(tagManager.tagify(player, message, inventoryItemObject));
									}

									itemMeta.setLore(strings);
				}
				else if (action.getAction().equals(CustomItemActionType.TOGGLE_PLAYERS))
				{
					HubStoredPlayer hubStoredPlayer = HubStoredPlayer.get(player);

					if (hubStoredPlayer != null)
					{
						feature = true;

						if (hubStoredPlayer.isHidePlayers())
						{
							itemStack = new ItemStack(351, 1, (short) 8);
							itemMeta = itemStack.getItemMeta();
							String displayName = ChatColor.translateAlternateColorCodes('&', player.getTranslatedMessage("hub.items.joininventory.toggleplayers.enable")[0]);
							itemMeta.setDisplayName(tagManager.tagify(player, displayName, inventoryItemObject));

							List<String> lore = new ArrayList<>();
							for (String string : player.getTranslatedMessage("hub.items.joininventory.toggleplayers.enable_lore"))
							{
								string = tagManager.tagify(player, ChatColor.translateAlternateColorCodes('&', string), inventoryItemObject);
								lore.add(string);
							}
							itemMeta.setLore(lore);
						}
						else
						{
							itemStack = new ItemStack(351, 1, (short) 10);
							itemMeta = itemStack.getItemMeta();
							String displayName = ChatColor.translateAlternateColorCodes('&', player.getTranslatedMessage("hub.items.joininventory.toggleplayers.disable")[0]);
							itemMeta.setDisplayName(tagManager.tagify(player, displayName, inventoryItemObject));

							List<String> lore = new ArrayList<>();
							for (String string : player.getTranslatedMessage("hub.items.joininventory.toggleplayers.disable_lore"))
							{
								string = tagManager.tagify(player, ChatColor.translateAlternateColorCodes('&', string), inventoryItemObject);
								lore.add(string);
							}
							itemMeta.setLore(lore);
						}
					}
				}
			}

			if (!feature)
			{
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
			}

			itemStack.setItemMeta(itemMeta);
			inventory.setItem(inventoryItemObject.getPlace(), itemStack);
		}

		// Custom fillers
		if (inventoryObject.getFiller() != null)
		{
			InventoryFillers filler = InventoryFillers.getFiller(inventoryObject.getFiller());

			if (filler != null)
			{
				filler.fill(player, inventoryObject, inventory);
			}
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
