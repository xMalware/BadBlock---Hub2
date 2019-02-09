package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.effect.LineEffect;

public class LineParticle extends CustomParticle
{

	@Override
	public Class<? extends Effect> getEffect()
	{
		return LineEffect.class;
	}
	
}
