package fr.badblock.bukkit.hub.v2.inventories.actions;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.bukkit.hub.v2.utils.tfa.AuthUtils;
import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionToggleTFA extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		String spamKey = "player_tfa_" + player.getName();
		
		if (GlobalFlags.has(spamKey))
		{
			player.sendTranslatedMessage("hub.tfa.pleasewait");
			return;
		}
		
		// 10s antispam
		GlobalFlags.set(spamKey, 5000);
		
		HubStoredPlayer hubStoredPlayer = HubStoredPlayer.get(player);
		
		if (hubStoredPlayer == null)
		{
			return;
		}
		
		String realName = player.getRealName() != null && !player.getRealName().isEmpty() ? player.getRealName() : player.getName();
		realName = realName.toLowerCase();
		
		String key = AuthUtils.getAuthKey(realName);
		player.closeInventory();
		
		if (key != null && !key.isEmpty())
		{
			player.sendTranslatedMessage("hub.tfa.removeinstructions");
			return;
		}
		
		// génération d'une nouvelle clé
		GoogleAuthenticatorKey authKey = AuthUtils.gAuth.createCredentials();
		String secretKey = authKey.getKey();
		AuthUtils.tempPlayersKeys.put(realName.toLowerCase(), secretKey);
		
		player.sendTranslatedMessage("hub.tfa.generatedkey", secretKey);
		ItemStack itemStack = GameAPI.getAPI().generateGoogleAuthQrCode(player, secretKey, "https://badblock.fr/includes/img/logosmall.png");
		
		player.getInventory().setItem(4, itemStack);
		player.getInventory().setHeldItemSlot(4);
		
		Location loc = player.getLocation().clone();
		loc.setPitch(50);
		player.teleport(loc);
	}

}