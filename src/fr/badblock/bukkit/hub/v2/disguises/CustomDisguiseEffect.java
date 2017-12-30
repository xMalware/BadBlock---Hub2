package fr.badblock.bukkit.hub.v2.disguises;

import fr.badblock.gameapi.particles.ParticleEffectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Data
public class CustomDisguiseEffect
{

	private ParticleEffectType	effectType;
	private int					amount;
	
}
