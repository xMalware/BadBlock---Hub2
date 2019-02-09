package fr.badblock.bukkit.hub.v2.utils.effects.effect;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectManager;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectType;
import fr.badblock.bukkit.hub.v2.utils.effects.util.ParticleEffect;
import fr.badblock.bukkit.hub.v2.utils.effects.util.RandomUtils;

public class SphereEffect extends Effect {

	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.SPELL_MOB;

	/**
	 * Particles to display
	 */
	public int particles = 50;

	/**
	 * Radius of the sphere
	 */
	public double radius = 0.6;

	/**
	 * Y-Offset of the sphere
	 */
	public double yOffset = 0;

	public SphereEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		iterations = 500;
		period = 1;
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		location.add(0, yOffset, 0);
		for (int i = 0; i < particles; i++) {
			Vector vector = RandomUtils.getRandomVector().multiply(radius);
			location.add(vector);
			display(particle, location);
			location.subtract(vector);
		}
	}

}
