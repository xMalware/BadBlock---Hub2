package fr.badblock.bukkit.hub.v2.cosmetics.workable.disguises;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.particles.ParticleEffect;
import fr.badblock.gameapi.particles.ParticleEffectType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomDisguiseEffect
{

	private 			ParticleEffectType	effectType;
	private 			int					amount;
	private transient	ParticleEffect		particleEffect;
	
	public CustomDisguiseEffect(ParticleEffectType effectType, int amount)
	{
		setEffectType(effectType);
		setAmount(amount);
	}
	
	public ParticleEffect build()
	{
		if (getParticleEffect() == null)
		{
			setParticleEffect(GameAPI.getAPI().createParticleEffect(getEffectType()));
			getParticleEffect().setAmount(getAmount());
		}
		return getParticleEffect();
	}
	
}
