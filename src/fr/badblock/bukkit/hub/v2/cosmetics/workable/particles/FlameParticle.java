package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.effect.FlameEffect;

public class FlameParticle extends CustomParticle
{

	@Override
	public Class<? extends Effect> getEffect()
	{
		return FlameEffect.class;
	}
	
}
