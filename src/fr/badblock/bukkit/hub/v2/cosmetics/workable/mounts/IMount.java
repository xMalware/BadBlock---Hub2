package fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts;

import org.bukkit.Location;

import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.entities.CustomCreature;

public interface IMount
{

	public abstract void			  setPlayer(BadblockPlayer player);
	public abstract CustomCreature spawnEntity(Location location);
	
}
