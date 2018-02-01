package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMiniUs extends CustomDisguise{

	ArmorStand armorStand;
	
	
	
	public DisguiseMiniUs(BadblockPlayer player) {
		super(player, EntityType.ARMOR_STAND);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
	    LeatherArmorMeta meta =  (LeatherArmorMeta) chestplate.getItemMeta();
	    meta.setColor(Color.GRAY);
	    ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
	    LeatherArmorMeta meta1 =  (LeatherArmorMeta) leggings.getItemMeta();
	    meta1.setColor(Color.GRAY);
	    ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
	    LeatherArmorMeta meta2 =  (LeatherArmorMeta) boots.getItemMeta();
	    meta2.setColor(Color.GRAY);
		armorStand.getEquipment().setHelmet(setHeadofPlayer(player.getName()));
		armorStand.getEquipment().setChestplate(chestplate);
		armorStand.getEquipment().setLeggings(leggings);
		armorStand.getEquipment().setBoots(boots);
		armorStand.setSmall(true);
	}

	@Override
	public CustomDisguiseEffect getEffect() {
		// TODO Auto-generated method stub
		return new CustomDisguiseEffect(ParticleEffectType.LAVA, 3);
	}
	
	public ItemStack setHeadofPlayer(String playerName) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(playerName);
		skull.setItemMeta(skullm);
		return skull;
	}

}
