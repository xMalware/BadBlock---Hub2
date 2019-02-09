package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.effect.CircleEffect;

public class CircleParticle extends CustomParticle
{

	@Override
	public Class<? extends Effect> getEffect()
	{
		return CircleEffect.class;
	}
	
}
