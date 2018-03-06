package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.config.ConfigLoader;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer.GamePermission;
import fr.badblock.gameapi.utils.i18n.TranslatableString;

public class RInvCommand extends AbstractCommand
{
	
	public RInvCommand()
	{
		super("rinv", new TranslatableString("commands.rinv.usage"), GamePermission.ADMIN, GamePermission.ADMIN, GamePermission.ADMIN);
		this.allowConsole(true);
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args)
	{
		BadBlockHub instance = BadBlockHub.getInstance();
		// Reload i18n
		Bukkit.getServer().dispatchCommand(sender, "/i18n reload");
		// Reload inventories
		InventoriesLoader.reloadInventories(instance);
		sendTranslatedMessage(sender, "hub.commands.rinv.reloaded");
		// Reload locations
		ConfigLoader.load(instance);
		return true;
	}

}
