package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import fr.badblock.bukkit.hub.v2.utils.effects.Effect;
import fr.badblock.bukkit.hub.v2.utils.effects.effect.DragonEffect;

public class DragonParticle extends CustomParticle
{

	@Override
	public Class<? extends Effect> getEffect()
	{
		return DragonEffect.class;
	}
	
}
