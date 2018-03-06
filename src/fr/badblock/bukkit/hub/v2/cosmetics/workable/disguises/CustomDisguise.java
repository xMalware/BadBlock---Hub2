package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.disguise.Disguise;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public abstract class CustomDisguise
{

	private BadblockPlayer	player;
	private Disguise		disguise;

	public CustomDisguise()
	{
		setDisguise(new Disguise(getEntityType(), null, true, true));
	}
	
	public CustomDisguise(BadblockPlayer player)
	{
		this();
		disguise(player);
	}
	
	public void disguise(BadblockPlayer player)
	{
		getPlayer().disguise(disguise);
	}

	public void undisguise()
	{
		getPlayer().undisguise();
	}

	public abstract EntityType			 getEntityType();
	
	public abstract CustomDisguiseEffect getEffect();

}
