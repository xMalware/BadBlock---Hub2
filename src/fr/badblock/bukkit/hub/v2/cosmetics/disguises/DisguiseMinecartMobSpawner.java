package fr.badblock.bukkit.hub.v2.cosmetics.disguises;

import org.bukkit.entity.EntityType;

import fr.badblock.gameapi.players.BadblockPlayer;

public class DisguiseMinecartMobSpawner extends CustomDisguise
{
	
	public DisguiseMinecartMobSpawner(BadblockPlayer player)
	{
		super(player, EntityType.MINECART_MOB_SPAWNER);
	}

	@Override
	public CustomDisguiseEffect getEffect()
	{
		return null;
	}

}
