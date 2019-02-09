package fr.badblock.bukkit.hub.v2.utils.effects.math;

import org.bukkit.configuration.ConfigurationSection;

public interface Transform {
	public double get(double t);

	public void load(ConfigurationSection parameters);
}
