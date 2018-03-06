package fr.badblock.bukkit.hub.v2.cosmetics.workable.hats;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public abstract class CustomHats
{
	
	private String		customHatOwner;
	private ItemStack	itemStack;
	
	public CustomHats(String customHatOwner)
	{
		setCustomHatOwner(customHatOwner);
	}
	
	public void deploy(BadblockPlayer player)
	{
		if (getItemStack() == null)
		{
			setItem();
		}
		player.getInventory().setHelmet(getItemStack());
	}
	
	public void remove(BadblockPlayer player)
	{
		player.getInventory().setHelmet(null);
	}
	
	public void setItem()
	{
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(getCustomHatOwner());
		skull.setItemMeta(skullm);
		setItemStack(skull);
	}

}
