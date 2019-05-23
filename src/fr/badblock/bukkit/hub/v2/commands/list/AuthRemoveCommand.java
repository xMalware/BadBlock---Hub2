package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.command.CommandSender;

import com.google.gson.JsonObject;

import fr.badblock.bukkit.hub.v2.utils.tfa.AuthUtils;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;

public class AuthRemoveCommand extends AbstractCommand
{

	public AuthRemoveCommand()
	{
		super("authremove", null, BadblockPlayer.GamePermission.PLAYER);
		this.allowConsole(false);
	}

	@Override
	public boolean executeCommand(CommandSender sender, String[] args)
	{
		if (args.length != 1)
		{
			return false;
		}

		BadblockPlayer player = (BadblockPlayer) sender;
		int secretId = -1;

		try
		{
			secretId = Integer.parseInt(args[0]);
			
			if (secretId < 0)
			{
				throw new NullPointerException();
			}
		}
		catch(Exception error)
		{
			player.sendTranslatedMessage("hub.tfa.notanint");
			return true;
		}

		final int enteredTemporaryCode = secretId;

		String playerName = player.getRealName() != null && !player.getRealName().isEmpty() ? player.getRealName() : player.getName();

		String key = AuthUtils.getAuthKey(playerName);	

		if (key == null || key.isEmpty())
		{
			player.sendTranslatedMessage("hub.tfa.nokeyassociatedwithyouraccount");
			return true;
		}

		if (AuthUtils.gAuth.authorize(key, enteredTemporaryCode))
		{
			player.sendTranslatedMessage("hub.tfa.removed");
			
			player.getInventory().setItem(4, null);
			player.getInventory().setHeldItemSlot(0	);
			
			updateAuthKey(player, "");
			AuthUtils.tempPlayersKeys.remove(playerName);
			
			return true;
		}
		
		player.sendTranslatedMessage("hub.tfa.unabletoremove");
		return true;
	}

	public void updateAuthKey(BadblockPlayer player, String secretCode)
	{
		JsonObject jsonObject = new JsonObject();
		player.getObject().addProperty("authKey", "");
		player.saveGameData(jsonObject);
	}

}