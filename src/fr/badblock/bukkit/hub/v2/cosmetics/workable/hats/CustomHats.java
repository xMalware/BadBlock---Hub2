package fr.badblock.bukkit.hub.v2.cosmetics.workable.hats;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


public class CustomHats {
	
	public void setCustomHatOwner(String arg0){
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(arg0);
		skull.setItemMeta(skullm);
	}

}
