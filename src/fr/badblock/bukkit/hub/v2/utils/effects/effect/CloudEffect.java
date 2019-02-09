package fr.badblock.bukkit.hub.v2.utils.effects.effect;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectManager;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectType;
import fr.badblock.bukkit.hub.v2.utils.effects.util.ParticleEffect;
import fr.badblock.bukkit.hub.v2.utils.effects.util.RandomUtils;

public class CloudEffect extends Effect {

	public Color cloudColor = null;
	/*
	 * Particle of the cloud
	 */
	public ParticleEffect cloudParticle = ParticleEffect.CLOUD;

	/*
	 * Size of the cloud
	 */
	public float cloudSize = .7f;

	/*
	 * Particle of the rain/snow
	 */
	public ParticleEffect mainParticle = ParticleEffect.DRIP_WATER;

	/*
	 * Radius of the rain/snow
	 */
	public float particleRadius = cloudSize - .1f;

	/*
	 * Y-Offset from location
	 */
	public double yOffset = .8;

	public CloudEffect(EffectManager manager) {
		super(manager);
		type = EffectType.REPEATING;
		period = 5;
		iterations = 50;
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		location.add(0, yOffset, 0);
		for (int i = 0; i < 50; i++) {
			Vector v = RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * cloudSize);
			display(cloudParticle, location.add(v), cloudColor, 0, 7);
			location.subtract(v);
		}
		Location l = location.add(0, .2, 0);
		for (int i = 0; i < 15; i++) {
			int r = RandomUtils.random.nextInt(2);
			double x = RandomUtils.random.nextDouble() * particleRadius;
			double z = RandomUtils.random.nextDouble() * particleRadius;
			l.add(x, 0, z);
			if (r != 1) {
				display(mainParticle, l);
			}
			l.subtract(x, 0, z);
			l.subtract(x, 0, z);
			if (r != 1) {
				display(mainParticle, l);
			}
			l.add(x, 0, z);
		}
	}

}
