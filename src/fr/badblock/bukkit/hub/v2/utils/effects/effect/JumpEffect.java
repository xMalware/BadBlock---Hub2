package fr.badblock.bukkit.hub.v2.utils.effects.effect;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectManager;
import fr.badblock.bukkit.hub.v2.utils.effects.EffectType;

public class JumpEffect extends Effect {

	/**
	 * Power of jump. (0.5f)
	 */
	public float power = .5f;

	public JumpEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.INSTANT;
		period = 20;
		iterations = 1;
		asynchronous = false;
	}

	@Override
	public void onRun() {
		Entity entity = getEntity();
		if (entity == null) {
			cancel();
			return;
		}
		Vector v = entity.getVelocity();
		v.setY(v.getY() + power);
		entity.setVelocity(v);
	}

}
