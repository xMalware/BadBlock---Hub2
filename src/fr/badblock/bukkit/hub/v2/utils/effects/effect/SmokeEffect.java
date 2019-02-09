package fr.badblock.bukkit.hub.v2.utils.effects.effect;

import org.bukkit.Location;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectManager;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectType;
import fr.badblock.bukkit.hub.v2.utils.effects.util.ParticleEffect;
import fr.badblock.bukkit.hub.v2.utils.effects.util.RandomUtils;

public class SmokeEffect extends Effect {

	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.SMOKE_NORMAL;

	public SmokeEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 1;
		iterations = 300;
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		for (int i = 0; i < 20; i++) {
			location.add(RandomUtils.getRandomCircleVector().multiply(RandomUtils.random.nextDouble() * 0.6d));
			location.add(0, RandomUtils.random.nextFloat() * 2, 0);
			display(particle, location);
		}
	}

}
