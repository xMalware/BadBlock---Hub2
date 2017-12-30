package fr.badblock.bukkit.hub.v2.listeners.entities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.badblock.gameapi.BadListener;

public class EntityDamageListener extends BadListener{
	
	@EventHandler
	public void damage(EntityDamageEvent e) {
		e.setCancelled(true);
	}

}
