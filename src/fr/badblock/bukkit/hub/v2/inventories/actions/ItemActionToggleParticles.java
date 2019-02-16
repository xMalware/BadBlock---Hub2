package fr.badblock.bukkit.hub.v2.inventories.actions;

import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.badblock.api.common.utils.flags.GlobalFlags;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemAction;
import fr.badblock.bukkit.hub.v2.inventories.objects.CustomItemActionType;
import fr.badblock.bukkit.hub.v2.players.HubStoredPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ItemActionToggleParticles extends CustomItemAction
{

	@Override
	public void execute(BadblockPlayer player, CustomItemActionType action, String actionData)
	{
		String spamKey = "player_toggleparticles_" + player.getName();
		
		if (GlobalFlags.has(spamKey))
		{
			player.sendTranslatedMessage("hub.toggleparticles.pleasewait");
			return;
		}
		
		// 10s antispam
		GlobalFlags.set(spamKey, 10000);
		
		HubStoredPlayer hubStoredPlayer = HubStoredPlayer.get(player);
		
		if (hubStoredPlayer == null)
		{
			return;
		}
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 9));
		player.playSound(Sound.EXPLODE);
		
		if (hubStoredPlayer.isHideParticles())
		{
			hubStoredPlayer.setHideParticles(false);
			hubStoredPlayer.save(player);
			player.sendTranslatedMessage("hub.toggleparticles.show");
		}
		else
		{
			hubStoredPlayer.setHideParticles(true);
			hubStoredPlayer.save(player);
			player.sendTranslatedMessage("hub.toggleparticles.hide");
		}
	}

}
