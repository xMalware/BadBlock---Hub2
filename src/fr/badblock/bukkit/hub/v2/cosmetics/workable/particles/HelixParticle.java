package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.effect.HelixEffect;

public class HelixParticle extends CustomParticle
{

	@Override
	public Class<? extends Effect> getEffect()
	{
		return HelixEffect.class;
	}
	
}
