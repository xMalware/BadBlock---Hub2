package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.BadblockPlayer.GamePermission;
import fr.badblock.gameapi.utils.i18n.TranslatableString;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class PNJCommand extends AbstractCommand{

	String gameName = "";
	String developer = "";
	
	public PNJCommand() {
		super("pnj", new TranslatableString("commands.pnj.usage"), GamePermission.ADMIN, GamePermission.ADMIN, GamePermission.ADMIN);
		this.allowConsole(false);
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		BadblockPlayer p = (BadblockPlayer)sender;
		final ArmorStand armor = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
		final Villager ent = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
		if(args.length == 0) {
			if(p.hasAdminMode()) {
				sendTranslatedMessage(sender, "commands.pnj.help");
			} else {
				sendTranslatedMessage(sender, "commands.pnj.nopermission");
			}
		} else if(args.length == 1 || args.length == 2 || args.length == 3) {
			if(args[0].equalsIgnoreCase("create")) 
			{
				if(args.length == 2 || args.length == 3) {
					gameName = "";
					gameName = gameName + args[1];
					developer = "";
					developer = developer + args[2];
					armor.setVisible(false);
					armor.setGravity(false);
					armor.getEquipment().setHelmet(setHeadofPlayer(developer));
					armor.setCustomName("§b§lMaître du jeu: §a"+gameName);
					armor.setCustomNameVisible(true);
					ent.setAdult();
					ent.setCustomName("");
					ent.setCustomNameVisible(false);
					disableAI(ent);
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				
			}
		} else if(!(args.length == 1 || args.length == 2)) {
			sendTranslatedMessage(sender, "commands.pnj.error.namerequired");
		}
		return true;
	}
	
	public ItemStack setHeadofPlayer(String playerName) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(developer);
		skull.setItemMeta(skullm);
		return skull;
	}
	
	public void disableAI(org.bukkit.entity.Entity ent) {
		Entity nmsEntity = ((CraftEntity) ent).getHandle();
		NBTTagCompound tag = nmsEntity.getNBTTag();
		if(tag == null) {
			tag = new NBTTagCompound();
		}
		nmsEntity.c(tag);
		tag.setInt("NoAI", 1);
		nmsEntity.f(tag);
	}

}
