package fr.badblock.bukkit.hub.v2.utils.effects.effect;

import org.bukkit.Location;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectManager;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectType;
import fr.badblock.bukkit.hub.v2.utils.effects.util.ParticleEffect;

public class IconEffect extends Effect {

	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.VILLAGER_ANGRY;

	public int yOffset = 2;

	public IconEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 4;
		iterations = 25;
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		location.add(0, yOffset, 0);
		display(particle, location);
	}
}
