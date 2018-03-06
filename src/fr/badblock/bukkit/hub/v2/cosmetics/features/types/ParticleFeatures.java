package fr.badblock.bukkit.hub.v2.cosmetics.features.types;

import java.util.UUID;

import fr.badblock.bukkit.hub.v2.cosmetics.features.Feature;
import fr.badblock.bukkit.hub.v2.cosmetics.features.IFeatureWorker;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.CustomParticle;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleCloud;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleEnchantmentTable;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleExplosion;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleFirework;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleFlame;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleLava;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleLove;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleNote;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleSlime;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleSmoke;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleVillagerAngry;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleVillagerHappy;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleWaterBubble;
import fr.badblock.bukkit.hub.v2.cosmetics.workable.particles.ParticleWaterSplash;
import fr.badblock.gameapi.players.BadblockPlayer;
import fr.badblock.gameapi.utils.threading.TaskManager;
import lombok.Getter;

@Getter
public enum ParticleFeatures implements IFeatureWorker
{

	CLOUD				(new ParticleCloud()),
	ENCHANTMENTTABLE	(new ParticleEnchantmentTable()),
	EXPLOSION			(new ParticleExplosion()),
	FIREWORK			(new ParticleFirework()),
	FLAME				(new ParticleFlame()),
	LAVA				(new ParticleLava()),
	LOVE				(new ParticleLove()),
	NOTE				(new ParticleNote()),
	SLIME				(new ParticleSlime()),
	SMOKE				(new ParticleSmoke()),
	VILLAGERANGRY		(new ParticleVillagerAngry()),
	VILLAGERHAPPY		(new ParticleVillagerHappy()),
	WATERBUBBLE			(new ParticleWaterBubble()),
	WATERSPLASH			(new ParticleWaterSplash());

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
		String uuid = "particle-" + UUID.randomUUID().toString();
		TaskManager.scheduleSyncRepeatingTask(uuid, new Runnable()
		{

			@Override
			public void run()
			{
				if (!player.isOnline())
				{
					TaskManager.cancelTaskByName(uuid);
					return;	
				}
				getCustomParticle().playEffect(player.getLocation());
			}
		}, 5, 5);
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
