package fr.badblock.bukkit.hub.v2.commands.list;

import org.bukkit.command.CommandSender;

import com.google.gson.JsonObject;

import fr.badblock.bukkit.hub.v2.utils.tfa.AuthUtils;
import fr.badblock.gameapi.command.AbstractCommand;
import fr.badblock.gameapi.players.BadblockPlayer;

public class AuthKeyCommand extends AbstractCommand
{

	public AuthKeyCommand()
	{
		super("authcheck", null, BadblockPlayer.GamePermission.PLAYER);
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

		String playerName = player.getRealName() != null && !player.getRealName().isEmpty() ? player.getRealName() : player.getName();
		playerName = playerName.toLowerCase();
		
		String secretKey = AuthUtils.tempPlayersKeys.get(playerName);
		
		if (secretKey == null || secretKey.isEmpty()) 
		{
			player.sendTranslatedMessage("hub.tfa.pleasegeneratebeforecheck");
			return true;
		}
		
		int enteredTemporaryCode = secretId;
		if (AuthUtils.gAuth.authorize(secretKey, enteredTemporaryCode)) 
		{
			player.getInventory().setItem(4, null);
			player.getInventory().setHeldItemSlot(0);
			
			player.sendTranslatedMessage("hub.tfa.checked");
			
			updateAuthKey(player, secretKey);
			AuthUtils.tempPlayersKeys.remove(playerName);
			
			return true;
		}
		
		player.sendTranslatedMessage("hub.tfa.unabletocheck");
		return true;
	}

	public void updateAuthKey(BadblockPlayer player, String secretCode)
	{
		JsonObject jsonObject = new JsonObject();
		player.getObject().addProperty("authKey", secretCode);
		player.saveGameData(jsonObject);
	}

}