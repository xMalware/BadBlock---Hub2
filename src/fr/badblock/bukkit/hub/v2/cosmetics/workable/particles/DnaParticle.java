package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.effect.DnaEffect;

public class DnaParticle extends CustomParticle
{

	@Override
	public Class<? extends Effect> getEffect()
	{
		return DnaEffect.class;
	}
	
}
