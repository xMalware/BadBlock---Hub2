package fr.badblock.bukkit.hub.v2.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.badblock.gameapi.GameAPI;
import fr.badblock.gameapi.particles.ParticleEffect;
import fr.badblock.gameapi.particles.ParticleEffectType;
import fr.badblock.gameapi.players.BadblockPlayer;

public class ParticleSlime  {
	double i = 0;
	Player player;
	
	private void playEffect(Location location) {
        ParticleEffect effect = (ParticleEffect) GameAPI.getAPI().createParticleEffect(ParticleEffectType.SLIME);
        effect.setSpeed(0);
        effect.setLongDistance(true);
        effect.setAmount(2);
        for (Player player : Bukkit.getOnlinePlayers()) {
            BadblockPlayer bplayer = (BadblockPlayer) player;
            bplayer.sendParticle(location, effect);
        }
    }
	
	public BadblockPlayer getPlayer() {
		return (BadblockPlayer) player;
	}
	
	public void run() {
	Location location = getPlayer().getLocation();
    Location location2 = location.clone();
    double radius = 1.1d;
    double radius2 = 1.1d;
    double particles = 100;

    for (int step = 0; step < 100; step += 4) {
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
    for (int step = 0; step < 100; step += 4) {
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
