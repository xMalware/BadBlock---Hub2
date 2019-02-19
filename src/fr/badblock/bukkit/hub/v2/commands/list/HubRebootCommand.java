package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.command.CommandSender;

import fr.badblock.bukkit.hub.v2.tasks.list.RebootTask;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer.GamePermission;
import fr.badblock.gameapi.utils.i18n.TranslatableString;

public class HubRebootCommand extends AbstractCommand
{

	public HubRebootCommand()
	{
		super("hubreboot", new TranslatableString("commands.spawn.usage"), GamePermission.ADMIN, GamePermission.ADMIN, GamePermission.ADMIN);
		this.allowConsole(false);
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args)
	{
		RebootTask.reboot = 61;
		return true;
	}

}
