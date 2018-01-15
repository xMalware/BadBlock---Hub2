package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import fr.badblock.bukkit.hub.v2.BadBlockHub;
import fr.badblock.bukkit.hub.v2.inventories.InventoriesLoader;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.players.BadblockPlayer.GamePermission;
import fr.badblock.gameapi.utils.i18n.TranslatableString;

public class SpawnCommand extends AbstractCommand{

	public SpawnCommand() {
		super("spawn", new TranslatableString("commands.spawn.usage"), GamePermission.PLAYER, GamePermission.PLAYER, GamePermission.PLAYER);
		this.allowConsole(false);
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args) {
		BadblockPlayer bPlayer = (BadblockPlayer)sender;
		Location location = new Location(bPlayer.getWorld(), 0.403, 80, 0.528);
		if(args.length == 0) {
			InventoriesLoader.reloadInventories(BadBlockHub.getInstance());
			bPlayer.teleport(location);
		}
		return true;
	}

}
