package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.AnimatedBallParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.CustomParticle;
import fr.badblock.bukkit.hub.v2.players.HubPlayer;
import fr.badblock.gameapi.players.BadblockPlayer;
import lombok.Getter;

@Getter
public enum ParticleFeatures implements IFeatureWorker
{

	ANIMATEDBALL				(new AnimatedBallParticle());

	private CustomParticle customParticle;

	ParticleFeatures(CustomParticle customParticle)
	{
		setCustomParticle(customParticle);
	}

	public void setCustomParticle(CustomParticle customParticle)
	{
		this.customParticle = customParticle;
	}

	@Override
	public void work(BadblockPlayer player)
	{
		HubPlayer hubPlayer = HubPlayer.get(player);
		
		if (hubPlayer == null)
		{
			return;
		}
		
		if (hubPlayer.getEffect() != null)
		{
			hubPlayer.getEffect().cancel();
			hubPlayer.setEffect(null);
		}
		
		getCustomParticle().start(player);
	}

	public static void work(BadblockPlayer player, Feature feature)
	{
		String name = feature.getName();
		ParticleFeatures finalFeature = null;
		for (ParticleFeatures mountFeature : values())
		{
			if (mountFeature.name().equalsIgnoreCase(name))
			{
				finalFeature = mountFeature;
				break;
			}
		}
		if (finalFeature == null)
		{
			return;
		}
		finalFeature.work(player);
	}

}
