package fr.badblock.bukkit.hub.v2.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.disguise.Disguise;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public abstract class CustomDisguise
{

	private BadblockPlayer player;
	private Disguise disguise;

	public CustomDisguise(BadblockPlayer player, EntityType entityType)
	{
		setPlayer(player);
		setDisguise(new Disguise(entityType, null, true, true));
	}

	public void disguise()
	{
		getPlayer().disguise(disguise);
	}

	public void undisguise()
	{
		getPlayer().undisguise();
	}

	public abstract CustomDisguiseEffect getEffect();

}
