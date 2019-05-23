package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.command.CommandSender;

import fr.badblock.bukkit.hub.v2.tasks.list.RebootTask;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.utils.i18n.TranslatableString;

public class HubRebootCommand extends AbstractCommand
{

	public HubRebootCommand()
	{
		super("hubreboot", new TranslatableString("commands.spawn.usage"), "hub.reboot");
		this.allowConsole(false);
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args)
	{
		RebootTask.rebootTime = 0;
		RebootTask.reboot = 61;
		return true;
	}

}
