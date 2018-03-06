package fr.badblock.bukkit.hub.v2.cosmetics.workable.particles;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ParticleSlime extends CustomParticle
{

	double i = 0;

	@Override
	public ParticleEffectType getParticleEffectType()
	{
		return ParticleEffectType.SLIME;
	}
	
	@Override
	public void run(BadblockPlayer player)
	{
		
		Location location = player.getLocation();
		Location location2 = location.clone();
		double radius = 1.1d;
		double radius2 = 1.1d;
		double particles = 100;
		
		for (int step = 0; step < 100; step += 4)
		{
			double interval = (2 * Math.PI) / particles;
			double angle = step * interval + i;
			Vector v = new Vector();
			v.setX(Math.cos(angle) * radius);
			v.setZ(Math.sin(angle) * radius);
			playEffect(location.add(v));
			location.subtract(v);
			location.add(0, 0.12d, 0);
			radius -= 0.044f;
		}
		
		for (int step = 0; step < 100; step += 4)
		{
			double interval = (2 * Math.PI) / particles;
			double angle = step * interval + i + 3.5;
			Vector v = new Vector();
			v.setX(Math.cos(angle) * radius2);
			v.setZ(Math.sin(angle) * radius2);
			playEffect(location2.add(v));
			location2.subtract(v);
			location2.add(0, 0.12d, 0);
			radius2 -= 0.044f;
		}
		
		i += 0.05;
		
	}
	
}
