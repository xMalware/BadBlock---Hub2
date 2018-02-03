package fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts;

import org.bukkit.Location;

import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.entities.CustomCreature;

public interface IMount
{

	public void			  setPlayer(BadblockPlayer player);
	public CustomCreature spawnEntity(Location location);
	
}
