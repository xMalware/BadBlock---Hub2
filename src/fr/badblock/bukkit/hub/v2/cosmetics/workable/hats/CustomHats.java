package fr.badblock.bukkit.hub.v2.cosmetics.workable.hats;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Data;

@Data
public abstract class CustomHats
{
	
	private static Map<String, ItemStack> heads = new HashMap<>();

	public static void work(BadblockPlayer player, Feature feature)
	{
		String[] splitter = feature.getName().split("_");
		if (splitter.length != 2)
		{
			return;
		}
		
		String head = splitter[1].toLowerCase();
		
		if (!heads.containsKey(head))
		{
			return;
		}
		
		deploy(player, head);
	}
	
	public static void add(String key, String skinOwner)
	{
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(skinOwner);
		skull.setItemMeta(skullm);
		heads.put(key, skull);
	}
	
	public static void deploy(BadblockPlayer player, String name)
	{
		ItemStack item = heads.get(name);
		
		if (item == null)
		{
			return;
		}
		
		player.getInventory().setHelmet(item);
	}
	
	public static void remove(BadblockPlayer player)
	{
		player.getInventory().setHelmet(null);
	}
	
}
