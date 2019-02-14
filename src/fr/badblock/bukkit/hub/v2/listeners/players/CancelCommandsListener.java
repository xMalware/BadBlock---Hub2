package fr.badblock.bukkit.hub.v2.listeners.players;

import java.util.Arrays;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.badblock.gameapi.BadListener;

public class CancelCommandsListener extends BadListener
{

	public List<String> disabledCommands = Arrays.asList(
			"/gadgetsmenu",
			"/gmenu",
			"/gameapi",
			"/gmysterybox",
			"/gmb",
			"/gmysteryboxes",
			"/mysterydust",
			"/dust"
			);

	@EventHandler
	public void onPlayerPreprocessCommand(PlayerCommandPreprocessEvent event)
	{
		if (event.getMessage() == null)
		{
			return;
		}

		if (!event.getMessage().startsWith("/"))
		{
			return;
		}

		String message = event.getMessage();

		for (String start : disabledCommands)
		{
			if (message.startsWith(start))
			{
				event.setCancelled(true);
				event.getPlayer().sendMessage("§cCommande inconnue.");
				break;
			}
		}
	}

}
