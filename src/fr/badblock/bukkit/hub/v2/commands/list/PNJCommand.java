package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
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

public class PNJCommand extends AbstractCommand
{

	String gameName = "";
	String developer = "";

	public PNJCommand()
	{
		super("pnj", new TranslatableString("commands.pnj.usage"), GamePermission.ADMIN, GamePermission.ADMIN, GamePermission.ADMIN);
		this.allowConsole(false);
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args)
	{
		// TODO do something better
		BadblockPlayer p = (BadblockPlayer)sender;
		final ArmorStand armor = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
		final Villager ent = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
		if (args.length == 0)
		{
			sendTranslatedMessage(sender, "commands.pnj.help");
			return true;
		}
		if (args[0].equalsIgnoreCase("create"))
		{
			if (args.length == 2 && args.length == 3)
			{

				gameName = "";
				gameName = gameName + args[1];
				developer = "";
				developer = developer + args[2];

				// TODO do something better

				armor.setVisible(false);
				armor.setGravity(false);
				armor.setCustomName("§b§lMaître du jeu : §a" + gameName);
				armor.setCustomNameVisible(true);

				ent.setAdult();
				ent.getEquipment().setHelmet(setHeadofPlayer(developer));
				ent.setCustomName(null);
				ent.setCustomNameVisible(false);

				disableAI(ent);
			}
			else if (args.length == 2 && args.length != 3)
			{
				sendTranslatedMessage(sender, "commands.pnj.developer.required");
			}
			else if (args.length != 2 && args.length != 3)
			{
				sendTranslatedMessage(sender, "commands.pnj.nameanddeveloper.required");
			}
		}
		else if (args[0].equalsIgnoreCase("remove"))
		{
			for (World w : Bukkit.getWorlds())
			{
				for (org.bukkit.entity.Entity e : w.getEntities())
				{
					if (e instanceof ArmorStand)
					{
						((ArmorStand) e).damage(20);
					}
				}
			}

			sendTranslatedMessage(sender, "commands.pnj.removeall.success");
		}
		return true;
	}

	public ItemStack setHeadofPlayer(String playerName)
	{
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(developer);
		skull.setItemMeta(skullm);
		return skull;
	}

	public void disableAI(org.bukkit.entity.Entity ent)
	{
		Entity nmsEntity = ((CraftEntity) ent).getHandle();
		NBTTagCompound tag = nmsEntity.getNBTTag();
		if(tag == null)
		{
			tag = new NBTTagCompound();
		}
		nmsEntity.c(tag);
		tag.setInt("NoAI", 1);
		nmsEntity.f(tag);
	}

}
