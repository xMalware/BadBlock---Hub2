package fr.badblock.bukkit.hub.v2.cosmetics.workable.mounts;

import org.bukkit.Location;

import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.entities.CustomCreature;

public interface IMount {

    void setPlayer(BadblockPlayer player);
    CustomCreature spawnEntity(Location location);

}
