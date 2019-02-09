package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.effect.VortexEffect;

public class VortexParticle extends CustomParticle
{

	@Override
	public Class<? extends Effect> getEffect()
	{
		return VortexEffect.class;
	}
	
}
