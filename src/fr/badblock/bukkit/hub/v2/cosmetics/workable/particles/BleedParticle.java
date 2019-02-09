package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.effect.BleedEffect;

public class BleedParticle extends CustomParticle
{

	@Override
	public Class<? extends Effect> getEffect()
	{
		return BleedEffect.class;
	}
	
}
